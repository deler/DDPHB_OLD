package my.ddx.ddphb;

import android.app.Application;

import com.orhanobut.logger.Logger;

import my.ddx.ddphb.managers.components.AppComponent;
import my.ddx.ddphb.managers.components.DaggerAppComponent;
import my.ddx.ddphb.managers.modules.AppModule;

/**
 * App
 * Created by deler on 18.03.17.
 */

public class App extends Application {

    private static AppComponent sAppComponent;

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//
//        Logger.init(getString(R.string.app_name))
//                .methodCount(10)
//                .logLevel(BuildConfig.DEBUG ?
//                        LogLevel.FULL :
//                        LogLevel.NONE);

        sAppComponent = buildAppComponent();
    }

    private AppComponent buildAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(getApplicationContext()))
                .build();
    }
}
