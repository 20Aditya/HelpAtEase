package com.example.justjava.helpatease;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;

public class AddEventActivity extends AppCompatActivity {

    Realm realm;
    private EditText editText, editText2, editText3, editText4;
    private Calendar c2;
    private int mYear, mMonth, mDay;
    private Button button;
    private String Eid,string;
    Boolean isBoolean=false;
    EventModel eventModel;
    RealmResults<EventModel> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Realm.init(this);
        realm = Realm.getDefaultInstance();
        editText = (EditText)findViewById(R.id.editText3);
        editText2 = (EditText)findViewById(R.id.editText4);
        editText3 = (EditText)findViewById(R.id.editText2);
        editText4 = (EditText)findViewById(R.id.editText);
        button = (Button)findViewById(R.id.button2);



        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                c2 = Calendar.getInstance();
                mYear = c2.get(Calendar.YEAR);
                mMonth = c2.get(Calendar.MONTH);
                mDay = c2.get(Calendar.DAY_OF_MONTH);

                // Launch Date Picker Dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddEventActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                editText.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.show();

            }
        });

        editText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                c2 = Calendar.getInstance();
                mYear = c2.get(Calendar.YEAR);
                mMonth = c2.get(Calendar.MONTH);
                mDay = c2.get(Calendar.DAY_OF_MONTH);

                // Launch Date Picker Dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddEventActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                editText2.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.show();

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Number currentnumid = realm.where(EventModel.class).max("eventid");
                        int nextid;
                        if(currentnumid == null){
                            nextid =1;
                        }else{
                            nextid = currentnumid.intValue()+1;
                        }

                        Log.d("AddEventActivity","nextid="+nextid);

                        EventModel model = realm.createObject(EventModel.class, nextid);
                        model.setTitle(editText4.getText().toString().trim());
                        model.setDescription(editText3.getText().toString().trim());
                        model.setDatefrom(editText.getText().toString().trim());
                        model.setDateto(editText2.getText().toString().trim());
                        model.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        model.setEid(nextid);

                    }
                });

                startActivity(new Intent(AddEventActivity.this,OrganisationActivity.class));
            }
        });


    }


}
