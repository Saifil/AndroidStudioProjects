package com.example.saifil.magmarfirebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class AccountActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        firebaseAuth = FirebaseAuth.getInstance();

    }

    public void onSignout(View view) {

        firebaseAuth.signOut();

        Intent intent = new Intent(AccountActivity.this,MainActivityMagmar.class);
        startActivity(intent);
    }


}
