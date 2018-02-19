package com.example.justjava.helpatease;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth mAuth;
    EditText editTextEmail, editTextPassword;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        findViewById(R.id.textViewSignup).setOnClickListener(this);
        findViewById(R.id.buttonLogin).setOnClickListener(this);

    }

    private void userLogin(){

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(password.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Enter a valid Email");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if(password.length()<8){

            editTextPassword.setError("Minimum length is 8");
            editTextPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);


        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    final String[] typo = new String[1];
                    DatabaseReference rootref = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference demoref = rootref.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Type");

                    demoref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            typo[0] = dataSnapshot.getValue(String.class);
                            if(typo[0]!=null && typo[0].equals("Volunteer"))
                            {
                                Log.d("ProfileActivity","Type=1");
                                finish();
                                startActivity(new Intent(MainActivity.this,VolunteerActivity.class));

                            }
                            else if(typo[0]!=null && typo[0].equals("Organisation")) {
                                Log.d("ProfileActivity","Type=3");
                                finish();
                                startActivity(new Intent(MainActivity.this, OrganisationActivity.class));
                            }
                            else {
                                Log.d("ProfileActivity","Type=2");
                                finish();
                                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            final String[] typo = new String[1];
            DatabaseReference rootref = FirebaseDatabase.getInstance().getReference();
            DatabaseReference demoref = rootref.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Type");

            demoref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    typo[0] = dataSnapshot.getValue(String.class);
                    if(typo[0]!=null && typo[0].equals("Volunteer"))
                    {
                        Log.d("ProfileActivity","Type=1");
                        finish();
                        startActivity(new Intent(MainActivity.this,VolunteerActivity.class));

                    }
                    else if(typo[0]!=null && typo[0].equals("Organisation")) {
                        Log.d("ProfileActivity","Type=3");
                        finish();
                        startActivity(new Intent(MainActivity.this, OrganisationActivity.class));
                    }
                    else {
                        Log.d("ProfileActivity","Type=2");
                        finish();
                        startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.textViewSignup:
                finish();
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
                break;

            case R.id.buttonLogin:
                userLogin();
                break;
        }
    }
}

