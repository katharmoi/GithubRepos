package com.kadirkertis.githubrepos.app;

import android.content.Context;

import com.kadirkertis.githubrepos.githubService.GithubApi;
import com.kadirkertis.githubrepos.githubService.model.GithubRepo;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Kadir Kertis on 3.8.2017.
 */

@Module
public class AppModule {

    private final Context context;

    public AppModule(Context context){
        this.context = context;
    }

    @AppScope
    @Provides
    public Context provideAppContext(){
        return context;
    }
}
