package com.example.justjava.helpatease;

import android.app.Application;

import com.firebase.client.Firebase;

import io.realm.Realm;

/**
 * Created by hp on 22-02-2018.
 */

public class HelpAtEase extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);

    }

}
