package my.ddx.ddphb.managers.modules;

import android.content.Context;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import dagger.Module;
import dagger.Provides;
import my.ddx.ddphb.managers.SettingsManager;

/**
 * AppModule
 * Created by deler on 18.03.17.
 */

@Module
public class AppModule {
    private Context mContext;

    public AppModule(@NonNull Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return mContext;
    }

    @Provides
    @Singleton
    public SettingsManager provideSettingsManager(Context context) {
        return new SettingsManager(context);
    }
}
