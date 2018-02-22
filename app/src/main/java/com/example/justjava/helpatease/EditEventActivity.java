package com.example.justjava.helpatease;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import io.realm.Realm;
import io.realm.RealmResults;

public class EditEventActivity extends AppCompatActivity {

    String Eid;
    EditText editText,editText2,editText3,editText4;
    Realm myrealm;
    RealmResults<EventModel> results;
    EventModel eventModel;

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

