package com.example.justjava.helpatease;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

public class RegisterActivity extends AppCompatActivity {

    Realm myrealm;
    TextView editText,editText2,editText3,editText4;
    EventModel eventModel;
    RealmResults<EventModel> results;
    String Eid;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myrealm = Realm.getDefaultInstance();


        Eid = getIntent().getStringExtra(Utilities.EID);
        if(Eid!=null){

            editText = (TextView) findViewById(R.id.textView19);
            editText2 = (TextView) findViewById(R.id.textView17);
            editText3 = (TextView) findViewById(R.id.textView14);
            editText4 = (TextView) findViewById(R.id.textView12);
            int a = Integer.parseInt(Eid);
            results = myrealm.where(EventModel.class).findAll();
            eventModel = results.where().equalTo("eid", a).findFirst();
            if(eventModel!=null){
                editText4.setText(eventModel.getTitle());
                editText3.setText(eventModel.getDescription());
                editText.setText(eventModel.getDatefrom());
                editText2.setText(eventModel.getDateto());

            }
        }

        final Button  btn = (Button)findViewById(R.id.button3);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Firebase rootRef = new Firebase("https://helpatease-2c540.firebaseio.com/");
                Firebase userRef = rootRef.child("users/"+FirebaseAuth.getInstance().getCurrentUser().getUid());

                myrealm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        EventModel model = myrealm.where(EventModel.class).equalTo("eid", Integer.parseInt(Eid)).findFirst();

                        Log.d("RegisterActivity","Eventid="+Integer.parseInt(Eid));

                        if(model!=null)
                            model.setUserid(FirebaseAuth.getInstance().getCurrentUser().getUid()+"$");


                    }
                });
                userRef.child("Events").child(String.valueOf(eventModel.getEid())).setValue(true);
                startActivity(new Intent(RegisterActivity.this,VolunteerActivity.class));

            }
        });
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuLogout:

                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this, MainActivity.class));

                break;
        }

        return true;
    }


}
