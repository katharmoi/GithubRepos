package com.kadirkertis.githubrepos;

import android.support.test.espresso.IdlingRegistry;

import com.jakewharton.espresso.OkHttp3IdlingResource;

import okhttp3.OkHttpClient;

/**
 * Created by Kadir Kertis on 14.8.2017.
 */

public abstract class IdlingResources {
    public static void registerOkHttp(OkHttpClient client){
    }
}
