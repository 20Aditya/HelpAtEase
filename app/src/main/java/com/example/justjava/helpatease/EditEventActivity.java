package com.example.justjava.helpatease;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class EditEventActivity extends AppCompatActivity {

    String Eid;
    EditText editText,editText2,editText3,editText4;
    Realm myrealm;
    RealmResults<EventModel> results;
    EventModel eventModel;
    ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myrealm = Realm.getDefaultInstance();
        Eid = getIntent().getStringExtra(Utilities.EID);
        if(Eid!=null){

            editText = (EditText)findViewById(R.id.editText3);
            editText2 = (EditText)findViewById(R.id.editText4);
            editText3 = (EditText)findViewById(R.id.editText2);
            editText4 = (EditText)findViewById(R.id.editText);
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

        DatabaseReference rootref = FirebaseDatabase.getInstance().getReference();

        ListView listView = (ListView)findViewById(R.id.list);
        list = new ArrayList<String>();

        String users = eventModel.getUserid();

        int l = users.length();
        for(int i=0;i<l;i++){
            String ids = users.substring(i,i+28);
            Log.d("ViewVolunteerActivity", "ids="+ids+"lastchar="+users.substring(28,29));
            DatabaseReference demoref = rootref.child("users").child(ids).child("Name");

            demoref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String name = dataSnapshot.getValue(String.class);
                    list.add(name);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            i=i+29;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //load menu based on the state we are in (new, view/update/delete)
        //user is viewing or updating a note
        getMenuInflater().inflate(R.menu.editevent, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_update: //save the note
                update();
                startActivity(new Intent(EditEventActivity.this,OrganisationActivity.class));
                break;

            case R.id.action_delete:
                delete();
                startActivity(new Intent(EditEventActivity.this,OrganisationActivity.class));
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    public void update() {
        myrealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                EventModel toedit = myrealm.where(EventModel.class).equalTo("eid", Integer.parseInt(Eid)).findFirst();

                toedit.setTitle(editText4.getText().toString().trim());
                toedit.setDescription(editText3.getText().toString().trim());
                toedit.setDatefrom(editText.getText().toString().trim());
                toedit.setDateto(editText2.getText().toString().trim());
                myrealm.copyToRealmOrUpdate(toedit);
            }
        });
    }

    public void delete(){

        myrealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                EventModel todelete = myrealm.where(EventModel.class).equalTo("eid", Integer.parseInt(Eid)).findFirst();
                todelete.deleteFromRealm();
            }
        });
    }

}

