package com.example.justjava.helpatease;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

import io.realm.Realm;

public class ViewVolunteerActivity extends AppCompatActivity {


    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_volunteer);

        realm = Realm.getDefaultInstance();

        EventModel model = realm.where(EventModel.class).equalTo("uid", FirebaseAuth.getInstance().getCurrentUser().getUid()).findFirst();

        String users = model.getUserid();

        int l = users.length();
        for(int i=0;i<l;i++){
            String ids = users.substring(i,i+28);
            Log.d("ViewVolunteerActivity", "ids="+ids+"lastchar="+users.substring(28,29));
            i=i+29;
        }

    }
}
