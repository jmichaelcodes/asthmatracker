package com.jmichaelcodes.asthmatrackerkids.Models;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by docto_000 on 6/29/2016.
 */
public class Entries {
    ArrayList<Entry> entries;
    String entriesDate;

    public ArrayList<Entry> getEntries() {
        return entries;
    }

    public void setEntries(ArrayList<Entry> entries) {
        this.entries = entries;
    }

    public String getEntriesDate() {
        return entriesDate;
    }

    public void setEntriesDate(String entriesDate) {
        this.entriesDate = entriesDate;
    }
}
