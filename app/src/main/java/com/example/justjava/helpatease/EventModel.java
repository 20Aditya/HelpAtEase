package com.example.justjava.helpatease;

/**
 * Created by hp on 21-02-2018.
 */

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by hp on 20-02-2018.
 */

public class EventModel extends RealmObject {

    @Required
    private String title;

    @PrimaryKey
    public int eventid;

    private int eid;

    private String userid;
    @Required
    private String description;

    @Required
    private String datefrom,dateto;

    @Required
    private String uid;

    public String getTitle()
    {
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }

    public String getDatefrom() {
        return datefrom;
    }

    public void setDatefrom(String datefrom) {
        this.datefrom = datefrom;
    }

    public String getDateto() {
        return dateto;
    }

    public void setDateto(String dateto) {
        this.dateto = dateto;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getEid() {
        return eventid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}

