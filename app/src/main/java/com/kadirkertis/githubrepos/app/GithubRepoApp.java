package com.kadirkertis.githubrepos.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;

import timber.log.Timber;

/**
 * Created by Kadir Kertis on 3.8.2017.
 */

public class GithubRepoApp extends Application {

    private AppComponent appComponent;

    private MyBus bus;

    public static GithubRepoApp get(Activity activity){
        return (GithubRepoApp) activity.getApplication();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
        Timber.plant(new Timber.DebugTree());
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        bus = MyBus.getInstance();

    }

    public AppComponent getAppComponent(){
        return appComponent;
    }

    public MyBus getBus(){return bus;}
}
