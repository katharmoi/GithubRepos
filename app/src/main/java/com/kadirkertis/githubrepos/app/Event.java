package com.kadirkertis.githubrepos.app;

/**
 * Created by Kadir Kertis on 26.8.2017.
 */

public class Event {
    private String eventName;
    private Object event;

    public Event(String eventName, Object event) {
        this.eventName = eventName;
        this.event = event;
    }

    public String getEventName() {
        return eventName;
    }

    public Object getEvent() {
        return event;
    }
}
