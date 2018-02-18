package com.example.justjava.helpatease;

/**
 * Created by hp on 09-02-2018.
 */

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {


    private static final int CHOOSE_IMAGE = 101;
    private String uid;
    private FirebaseUser user;
    private Firebase mroot;
    private Button btn;
    private EditText editText;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null)
            Log.d("ProfileActivity", "User id:"+user.getUid());
        Firebase.setAndroidContext(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Button btn = (Button)findViewById(R.id.buttonsave);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Firebase rootRef = new Firebase("https://helpatease-2c540.firebaseio.com/");
                EditText editText = (EditText)findViewById(R.id.editText);
                EditText editText2 = (EditText)findViewById(R.id.editText2);
                EditText editText3 = (EditText)findViewById(R.id.editText3);
                String name = editText2.getText().toString().trim();
                String phone = editText.getText().toString().trim();
                String city = editText3.getText().toString().trim();
                RadioButton volunteer = (RadioButton) findViewById(R.id.radioButton);
                RadioButton organisation = (RadioButton)findViewById(R.id.radioButton2);
                editText.setText(phone);
                editText2.setText(name);
                editText3.setText(city);
                boolean checkbox;
                String type;
// Assuming the user is already logged in.
                Firebase userRef = rootRef.child("users/"+FirebaseAuth.getInstance().getCurrentUser().getUid());
                userRef.child("Name").setValue(name);
                userRef.child("Phone").setValue(phone);
                userRef.child("City").setValue(city);

                if(volunteer.isChecked()) {
                    type = "Volunteer";
                    userRef.child("Type").setValue(type);

                }
                if(organisation.isChecked()) {
                    type = "Organisation";
                    userRef.child("Type").setValue(type);
                }

                Toast.makeText(getApplicationContext(), "Details Saved..!", Toast.LENGTH_SHORT).show();
            }
        });
    }




    @Override
    protected void onStart() {
        super.onStart();
        if (user == null) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
        
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
