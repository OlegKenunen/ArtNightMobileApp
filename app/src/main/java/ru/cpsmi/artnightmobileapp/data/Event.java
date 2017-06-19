package ru.cpsmi.artnightmobileapp.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by T on 6/14/2017.
 */
@DatabaseTable(tableName="events")
public class Event {

    @DatabaseField(generatedId = true, columnName = "event_id")
    private int eventId;

    @DatabaseField(columnName = "event_type")
    private String eventType;

    @DatabaseField(columnName = "title")
    private String title;

    private String description;
    private Date beginTime;
    private Date endTime;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Museum museum; //Внешний ключ для связывания музей-событие


    // Default constructor is needed for the SQLite
    public Event() {
    }

    public String getEventType() {
        return eventType;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }
}
