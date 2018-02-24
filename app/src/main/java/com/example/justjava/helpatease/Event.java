package com.example.justjava.helpatease;

/**
 * Created by hp on 25-02-2018.
 */

public class Event {

    private String title,datefrom,dateto;


    public Event(String mtitle, String mdatefrom, String mdateto) {
        title = mtitle;
        datefrom = mdatefrom;
        dateto = mdateto;
    }


    public String getTitle() {
        return title;
    }

    public String getDatefrom() {
        return datefrom;
    }

    public String getDateto() {
        return dateto;
    }
}
