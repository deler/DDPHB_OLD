package my.ddx.ddphb.managers.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import my.ddx.ddphb.managers.DataManager;
import my.ddx.ddphb.managers.SettingsManager;

/**
 * DataModule
 * Created by deler on 19.03.17.
 */

@Module
public class DataModule {

    @Provides
    @Singleton
    public DataManager provideDataManager(Context context, SettingsManager settingsManager) {
        return new DataManager(context, settingsManager);
    }
}
