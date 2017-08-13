package com.kadirkertis.githubrepos.screens.repos.di;

import com.kadirkertis.githubrepos.app.AppComponent;

import dagger.Component;

/**
 * Created by Kadir Kertis on 3.8.2017.
 */

@ReposScreenScope
@Component(modules = ReposScreenModule.class , dependencies = AppComponent.class)
public interface ReposScreenComponent {
}
