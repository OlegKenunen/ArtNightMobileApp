package ru.cpsmi.artnightmobileapp.data;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "museums")
public class Museum {

    @DatabaseField(generatedId = true, columnName = "museum_id")
    private int museumId;

    @DatabaseField(columnName = "title")
    private String title;
    //private LatLng position;//данные такого типа невозможно хранить в базе данных

    @DatabaseField(columnName = "latitude")
    private double latitude;

    @DatabaseField(columnName = "longitude")
    private double longitude;

    @DatabaseField(columnName = "address")
    private String address;

    @DatabaseField(columnName = "start_time")
    private Date startTime;

    @DatabaseField(columnName = "end_time")
    private Date endTime;

    @DatabaseField(columnName = "programme")
    private String programme;
    //private String url; //нужно ли?

    //private List<Event> events; //взаимоотношение классов переносим на сторону DB

    // Default constructor is needed for the SQLite
    public Museum() { //
    }

    public Museum(String title, double latitude, double longitude) {
        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
        //this.startTime = new Date(startTime);
    }

    public int getMuseumId() {
        return museumId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public String getProgramme() {
        return programme;
    }
}
