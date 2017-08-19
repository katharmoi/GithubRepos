package com.kadirkertis.githubrepos.utility;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

/**
 * Created by Kadir Kertis on 19.8.2017.
 */

public class Utilities {
    private static final int ATTEMPTS = 5;

    public static boolean notNullOrEmpty(String s) {
        return s != null && !s.isEmpty();
    }

    public static Observable<Long> exponentialBackOff(Throwable error, int attempt) {
        switch (attempt) {
            case 1:
                return Observable.just(1L);
            case ATTEMPTS:
                return Observable.error(error);
            default:
                long expDelay = (long) Math.pow(2, attempt - 2);
                return Observable.timer(expDelay, TimeUnit.SECONDS);
        }
    }
}
