package com.example.saifil.magmarfirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivityMagmar extends AppCompatActivity {

    private FirebaseAuth firebaseAuth; //used for authenticating the user
    private FirebaseAuth.AuthStateListener authStateListener; //creates a listener to firebaseAuth

    private EditText pass_et, email_et;
    private TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_magmar);

        //set a reference to the firebaseauth
        firebaseAuth = FirebaseAuth.getInstance();

        email_et = findViewById(R.id.email_id);
        pass_et = findViewById(R.id.pass_id);

        //if the user is logged in then we redirect him to a new activity
        //also called everytime the user tries to login
        authStateListener = new FirebaseAuth.AuthStateListener() { //gives the current login state
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() != null) { //user is signed in

                    //redirect the user
                    Intent intent = new Intent(MainActivityMagmar.this,AccountActivity.class);
                    startActivity(intent);
                }
            }
        };

        //redirect to registration page
        tv =findViewById(R.id.myText_id);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityMagmar.this,RegistrationActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onStart() {

        super.onStart();

        //call the authstatelistener in the onCreate method when the activity starts
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    public void onLogin(View view) {

        //call the method when user tries to login
        startSignin();
    }

    private void startSignin() { //authenticates the user

        String email = email_et.getText().toString();
        String password = pass_et.getText().toString();

        //signing in with email and password
        if (email.matches("") || password.matches("")) {

            Toast.makeText(this, "Enter email and password", Toast.LENGTH_SHORT).show();
        }
        else {
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(
                    new OnCompleteListener<AuthResult>() { //check the status of the signin task (response)
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            String message = "Sign in failed";
                            if (!task.isSuccessful()) { //if login was fail
                                Toast.makeText(MainActivityMagmar.this, message, Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
            );
        }


    }

}
