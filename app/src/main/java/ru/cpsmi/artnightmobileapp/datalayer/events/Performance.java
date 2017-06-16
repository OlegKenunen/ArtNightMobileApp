package ru.cpsmi.artnightmobileapp.datalayer.events;

import java.util.Date;

/**
 * Created by Oleg on 16.06.2017.
 * oleg.kenunen@gmail.com
 */
public class Performance extends Event {
    private Date beginTime;
    private Date endTime;

    public Date getBeginTime() {
        return beginTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
