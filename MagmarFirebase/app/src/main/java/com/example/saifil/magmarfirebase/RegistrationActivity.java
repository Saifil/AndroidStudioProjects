package com.example.saifil.magmarfirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    private EditText pass_et, email_et;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        pass_et = findViewById(R.id.mepass);
        email_et = findViewById(R.id.memail);

        //set a reference to the firebaseauth
        firebaseAuth = FirebaseAuth.getInstance();

    }

    public void redirecttoLogin(View view) {

        Intent intent = new Intent(RegistrationActivity.this,MainActivityMagmar.class);
        startActivity(intent);
    }

    public void onSignup(View view) {

        String email = email_et.getText().toString();
        String password = pass_et.getText().toString();

        if (email.matches("") || password.matches("")) {

            Toast.makeText(this, "Enter email and password", Toast.LENGTH_SHORT).show();
        }
        else {

            //create a new user
            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(
                    new OnCompleteListener<AuthResult>() { //task to perform on completion
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) { //if registration was successful

                                //redirect to the main page
                                Intent intent = new Intent(RegistrationActivity.this,AccountActivity.class);
                                startActivity(intent);
                            }
                            else { //if reg. failed, raise a toast

                                String message = "registration failed, try again";
                                Toast.makeText(RegistrationActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
            );
        }

    }
}
