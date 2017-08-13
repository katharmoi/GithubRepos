package com.kadirkertis.githubrepos.screens.home.di;


import android.app.Activity;

import com.kadirkertis.githubrepos.githubService.GithubApi;
import com.kadirkertis.githubrepos.repository.GithubRepositoryImpl;
import com.kadirkertis.githubrepos.repository.GithubRepository;
import com.kadirkertis.githubrepos.screens.home.MainPresenter;
import com.kadirkertis.githubrepos.screens.home.MainPresenterImpl;
import com.kadirkertis.githubrepos.screens.home.MainViewImpl;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Kadir Kertis on 3.8.2017.
 */

@Module
public class MainScreenModule {

    private final Activity activity;

    public MainScreenModule(Activity activity) {
        this.activity = activity;
    }

    @MainScreenScope
    @Provides
    public MainViewImpl provideMainView(Picasso picasso){
        return new MainViewImpl(activity,picasso);
    }

    @MainScreenScope
    @Provides
    public GithubRepository provideGithubRepository(GithubApi githubApi){
        return new GithubRepositoryImpl(githubApi);
    }
    @MainScreenScope
    @Provides
    public MainPresenter provideMainPresenter(MainViewImpl view,GithubRepository repository){
        return new MainPresenterImpl(view,repository);
    }
}
