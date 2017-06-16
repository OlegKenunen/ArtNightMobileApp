package ru.cpsmi.artnightmobileapp.datalayer;

import com.google.android.gms.maps.model.LatLng;
import ru.cpsmi.artnightmobileapp.datalayer.events.Event;

import java.util.Date;
import java.util.List;

public class Museum {
    private String title;
    private LatLng position;
    private String address;

    private Date startTime;
    private Date endTime;
    private String url; //нужно ли?
    private String programme; //дублирует информацию с событиями, возможно, стоит вынести в Event
    private List<Event> events;

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }

    public LatLng getPosition() {
        return position;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }
}
