package com.kadirkertis.githubrepos.utility;

import android.os.Build;
import android.support.annotation.RequiresApi;

import org.reactivestreams.Publisher;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import timber.log.Timber;

/**
 * Created by Kadir Kertis on 7.8.2017.
 */

@RequiresApi(api = Build.VERSION_CODES.N)
public class RetryWithDelay implements Function<Flowable<? extends Throwable>, Publisher<?>> {

    private static final int ATTEMPTS = 5;
    private final int maxNumOfTry;
    private final int retryDelayMs;
    private int retryCount;

    public RetryWithDelay(int maxNumOfTry, int retryDelayMs) {
        this.maxNumOfTry = maxNumOfTry;
        this.retryDelayMs = retryDelayMs;
        retryCount = 0;
    }

    @Override
    public Publisher<?> apply(Flowable<? extends Throwable> flowable) {
        return flowable.flatMap(new io.reactivex.functions.Function<Throwable, Publisher<?>>() {
            @Override
            public Publisher<?> apply(@NonNull Throwable throwable) throws Exception {
                if (maxNumOfTry > ++retryCount) {
                    Timber.d("Retrying in %d ms", retryCount * retryDelayMs);

                    return Flowable.timer(retryCount * retryDelayMs, TimeUnit.MILLISECONDS);
                }

                Timber.d("Reached Max number of retry");

                return Flowable.error(throwable);
            }
        });
    }


}
