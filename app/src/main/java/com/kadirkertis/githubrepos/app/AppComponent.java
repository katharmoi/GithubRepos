package com.kadirkertis.githubrepos.app;

import com.kadirkertis.githubrepos.githubService.GithubApi;
import com.squareup.picasso.Picasso;

import dagger.Component;

/**
 * Created by Kadir Kertis on 3.8.2017.
 */

@AppScope
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {
    GithubApi githubApi();

    Picasso picasso();
}
