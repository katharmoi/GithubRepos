package com.kadirkertis.githubrepos.screens.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kadirkertis.githubrepos.app.GithubRepoApp;
import com.kadirkertis.githubrepos.screens.home.di.DaggerMainScreenComponent;
import com.kadirkertis.githubrepos.screens.home.di.MainScreenModule;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity  {

    @Inject
    MainViewImpl view;

    @Inject
    MainPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerMainScreenComponent.builder()
                .mainScreenModule(new MainScreenModule(this))
                .appComponent(GithubRepoApp.get(this).getAppComponent())
                .build()
                .inject(this);
        setContentView(view);
        presenter.onCreate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
