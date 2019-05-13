package my.ddx.ddphb.screens.spelldetail.spelldetail;

import android.content.Context;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import io.realm.RealmResults;
import my.ddx.ddphb.App;
import my.ddx.ddphb.R;
import my.ddx.ddphb.managers.DataManager;
import my.ddx.ddphb.models.ClassModel;
import my.ddx.ddphb.models.SpellModel;

/**
 * SpellDetailPresenter implemetation {@link ISpellDetailPresenter}
 * Created by deler on 20.03.17.
 */
public class SpellDetailPresenter implements ISpellDetailPresenter {
    @Inject
    DataManager mDataManager;
    @Inject
    Context mContext;

    private ISpellDetailView mView;

    private String mSpellId;
    private SpellModel mSpellModel;

    SpellDetailPresenter(String spellId) {
        mSpellId = spellId;

        App.getAppComponent().inject(this);

        mSpellModel = mDataManager.getSpellsManager().getSpell(mSpellId);
        mSpellModel.addChangeListener(element -> publishSpell());
    }

    private void publishSpell() {
        if (mView != null) {

            CharSequence levelAndSchool;
            if (mSpellModel.getLevel() == 0) {
                levelAndSchool = getContext().getString(R.string.spell_details_text_cantrips_and_school, mSpellModel.getSchool());
            } else {
                levelAndSchool = getContext().getString(R.string.spell_details_text_level_and_school, mSpellModel.getLevel(), mSpellModel.getSchool());
            }

            StringBuilder classesBuilder = new StringBuilder();
            RealmResults<ClassModel> classes = mSpellModel.getClasses().sort(ClassModel.NAME);
            if (classes.size() > 1) {
                for (int i = 0; i < classes.size() - 1; i++) {
                    ClassModel classModel = classes.get(i);
                    classesBuilder.append(classModel.getName());
                    classesBuilder.append(", ");
                }
            }

            if (classes.size() > 0) {
                ClassModel classModel = classes.last();
                classesBuilder.append(classModel.getName());
            }

            mView.setupView(new ISpellDetailView
                    .SpellViewModel(mSpellModel.getId())
                    .setName(mSpellModel.getName())
                    .setSchoolAndLevel(levelAndSchool)
                    .setRitual(mSpellModel.isRitual())
                    .setCastingTime(mSpellModel.getCastingTime())
                    .setRange(mSpellModel.getRange())
                    .setDuration(mSpellModel.getDuration())
                    .setConcentration(mSpellModel.isConcentration())
                    .setComponents(mSpellModel.getComponents())
                    .setDescription(mSpellModel.getDescription())
                    .setUpLevel(mSpellModel.getUpLevel())
                    .setClasses(classesBuilder.toString())
            );
        }
    }

    @Override
    public void onAttachView(@NonNull ISpellDetailView view) {
        mView = view;

        if (mSpellModel.isLoaded()){
            publishSpell();
        }
    }

    @Override
    public void onDetachView() {
        mView = null;
    }

    @Override
    public void onDestroy() {
        mSpellModel.removeAllChangeListeners();
    }

    public Context getContext() {
        return mContext;
    }
}
