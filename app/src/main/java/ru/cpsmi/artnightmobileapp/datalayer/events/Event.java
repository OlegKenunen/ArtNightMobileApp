package ru.cpsmi.artnightmobileapp.datalayer.events;

/**
 * Created by T on 6/14/2017.
 */
public   abstract class Event {
    private String title;
    private String description;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
