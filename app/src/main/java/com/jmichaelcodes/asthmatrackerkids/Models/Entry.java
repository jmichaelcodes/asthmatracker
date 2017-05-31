package com.jmichaelcodes.asthmatrackerkids.Models;

/**
 * Created by docto_000 on 6/29/2016.
 */
public class Entry {
    Integer type;
    String entryTime;
    String zone;
    String date;
    Child child;

    public Entry(String zone, String entryTime, Integer type, String date, Child child) {
        this.zone = zone;
        this.entryTime = entryTime;
        this.type = type;
        this.date = date;
        this.child = child;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }
}
