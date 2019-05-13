package my.ddx.ddphb.managers;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.orhanobut.logger.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;
import my.ddx.ddphb.R;
import my.ddx.ddphb.models.ClassModel;
import my.ddx.ddphb.models.SpellModel;
import my.ddx.ddphb.models.common.SpellFilterModel;

/**
 * DataManager
 * Created by deler on 19.03.17.
 */

public class DataManager {

    private final static int REALM_SCHEMA_VERSION = 1;

    private Context mContext;
    private SettingsManager mSettingsManager;
    private SpellsManager mSpellsManager;
    private ClassesManager mClassesManager;

    public DataManager(Context context, SettingsManager settingsManager) {
        mContext = context;
        mSettingsManager = settingsManager;
        mSpellsManager = new SpellsManager();
        mClassesManager = new ClassesManager();
        Realm.init(context);

        RealmConfiguration config0 = new RealmConfiguration.Builder()
                .name("default3.realm")
                .schemaVersion(REALM_SCHEMA_VERSION)
                .build();

        Realm.setDefaultConfiguration(config0);

        Realm.getDefaultInstance();

        if (mSettingsManager.getRealmSchemaVersion() < 1) {
            initFirstData();
        }
    }

    private void initFirstData() {

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(realm1 -> {
                    InputStream is = mContext.getResources().openRawResource(R.raw.data);
                    Writer writer = new StringWriter();
                    char[] buffer = new char[1024];
                    try {
                        Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                        int n;
                        while ((n = reader.read(buffer)) != -1) {
                            writer.write(buffer, 0, n);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    String jsonString = writer.toString();

                    Gson gson = new GsonBuilder().create();
                    JsonObject firstData = gson.fromJson(jsonString, JsonObject.class);
                    JsonArray classesJsonArray = firstData.getAsJsonArray("classes");
                    List<ClassModel> classes = new ArrayList<>();
                    for (JsonElement jsonElement : classesJsonArray) {
                        ClassModel classModel = gson.fromJson(jsonElement, ClassModel.class);
                        classes.add(classModel);
                    }
                    realm1.copyToRealm(classes);

                    JsonArray spellsJsonArray = firstData.getAsJsonArray("spells");
                    List<SpellModel> spells = new ArrayList<>();
                    for (JsonElement jsonElement : spellsJsonArray) {
                        SpellModel spellModel = gson.fromJson(jsonElement, SpellModel.class);
                        spells.add(spellModel);
                    }
                    realm1.copyToRealm(spells);

                    RealmResults<ClassModel> classModels = realm1.where(ClassModel.class).findAll();
                    RealmResults<SpellModel> spellModels = realm1.where(SpellModel.class).findAll();
                    for (SpellModel spellModel : spellModels) {
                        for (ClassModel classModel : classModels) {
                            if (getClassesManager().isCanCastClass(spellModel, classModel)) {
                                spellModel.getClasses().add(classModel);
                            }
                        }
                    }
                },
                () -> mSettingsManager.setRealmSchemaVersion(REALM_SCHEMA_VERSION),
                error -> Logger.e(error, "Error"));
    }


    public SpellsManager getSpellsManager() {
        return mSpellsManager;
    }

    public ClassesManager getClassesManager() {
        return mClassesManager;
    }

    public class ClassesManager {
        public RealmResults<ClassModel> getClasses() {
            Realm realm = Realm.getDefaultInstance();
            return realm.where(ClassModel.class).sort(ClassModel.NAME).findAllAsync();

        }

        private boolean isCanCastClass(SpellModel spellModel, ClassModel classModel) {
            String classIds = spellModel.getClassIds();
            if (!TextUtils.isEmpty(classIds)) {
                String[] strings = classIds.split(",");
                String id = classModel.getId();
                for (String string : strings) {
                    if (string.trim().equalsIgnoreCase(id)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public class SpellsManager {
        public SpellModel getSpell(String spellId) {
            Realm realm = Realm.getDefaultInstance();
            return realm.where(SpellModel.class).equalTo(SpellModel.ID, spellId, Case.INSENSITIVE).findFirstAsync();
        }

        public RealmResults<SpellModel> getSpells() {
            return getSpells(null);
        }

        public RealmResults<SpellModel> getSpells(SpellFilterModel filter) {
            Realm realm = Realm.getDefaultInstance();
            RealmQuery<SpellModel> query = realm.where(SpellModel.class);
            if (filter != null) {
                if (!TextUtils.isEmpty(filter.getName())) {
                    query.like(SpellModel.NAME, String.format("*%s*", filter.getName()), Case.INSENSITIVE);
                }

                if (!filter.getClassIds().isEmpty()) {
                    String[] ids = new String[filter.getClassIds().size()];
                    ids = filter.getClassIds().toArray(ids);
                    query.in(SpellModel.CLASSES + "." + ClassModel.ID, ids, Case.INSENSITIVE);
                }

                if (!filter.getSchools().isEmpty()) {
                    String[] ids = new String[filter.getSchools().size()];
                    ids = filter.getSchools().toArray(ids);
                    query.in(SpellModel.SCHOOL, ids, Case.INSENSITIVE);
                }

                query.between(SpellModel.LEVEL, filter.getMinLevel(), filter.getMaxLevel());

            }
            return query.sort(SpellModel.LEVEL, Sort.ASCENDING, SpellModel.NAME, Sort.ASCENDING).findAllAsync();
        }

        public RealmResults<SpellModel> getSpellSchools() {
            Realm realm = Realm.getDefaultInstance();
            return realm.where(SpellModel.class).distinct(SpellModel.SCHOOL).findAllAsync();
        }
    }
}
