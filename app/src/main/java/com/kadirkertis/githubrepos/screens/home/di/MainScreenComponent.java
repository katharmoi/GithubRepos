package com.kadirkertis.githubrepos.screens.home.di;

import com.kadirkertis.githubrepos.app.AppComponent;
import com.kadirkertis.githubrepos.screens.home.MainActivity;

import dagger.Component;

/**
 * Created by Kadir Kertis on 3.8.2017.
 */
@MainScreenScope
@Component(modules = {MainScreenModule.class},dependencies = AppComponent.class)
public interface MainScreenComponent {
    void inject(MainActivity target);
}
