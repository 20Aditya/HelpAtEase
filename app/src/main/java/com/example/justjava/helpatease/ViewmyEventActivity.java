package com.example.justjava.helpatease;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;


public class ViewmyEventActivity extends AppCompatActivity {

    Realm realm;
    RealmResults<EventModel> realmResults;
    ArrayList<Event> eventModels;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewmy_event);

         eventModels = new ArrayList<Event>();

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        realm = Realm.getDefaultInstance();
        listView = (ListView)findViewById(R.id.list);

        DatabaseReference rootref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference demoref = rootref.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Events");

        demoref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot child: dataSnapshot.getChildren()){
                    String key = child.getKey();
                    Log.d("ViewmyEventActivity","key="+key);
                    EventModel model = realm.where(EventModel.class).equalTo("eid",Integer.parseInt(key)).findFirst();
                    eventModels.add(new Event(model.getTitle(),model.getDatefrom(),model.getDateto()));

                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        EventAdapter adapter = new EventAdapter(this,eventModels);

        listView.setAdapter(adapter);

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
