package my.ddx.ddphb.managers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SettingsManager
 * Created by deler on 20.03.17.
 */

public class SettingsManager {

    private static final String APP_SETTINGS_PREFS = "APP_SETTINGS_PREFS";
    private static final String KEY_REALM_SCHEMA_VERSION = "KEY_REALM_SCHEMA_VERSION";

    private SharedPreferences mPreferences;

    public SettingsManager(Context context) {
        mPreferences = context.getSharedPreferences(APP_SETTINGS_PREFS, 0);
    }

    public void setRealmSchemaVersion(int version){
        mPreferences.edit().putInt(KEY_REALM_SCHEMA_VERSION, version).apply();
    }

    public int getRealmSchemaVersion(){
        return mPreferences.getInt(KEY_REALM_SCHEMA_VERSION, -1);
    }

}
