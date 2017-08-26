package com.kadirkertis.githubrepos.app;

import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;

import javax.annotation.Nullable;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * Created by Kadir Kertis on 24.8.2017.
 */

public class MyBus {

    private static  MyBus sBus;
    private final  Relay<Event> bus = PublishRelay.<Event>create().toSerialized();

    private MyBus(){
    }

    public static synchronized MyBus getInstance(){
        if(sBus == null){
            sBus = new MyBus();
        }
        return sBus;
    }


    public void post(String eventName,Object value){
        bus.accept(new Event(eventName,value));
    }

    public Flowable<Event> asFlowable(){
        return bus.toFlowable(BackpressureStrategy.LATEST);
    }

    public  boolean hasObservers(){
        return bus.hasObservers();
    }
}
