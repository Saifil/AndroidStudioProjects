package com.example.saifil.tollplaza;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {

    Spinner myAuto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        myAuto = findViewById(R.id.mySpinner);
    }

    public void onSaveAuto(View view) {
        String mySelectedAuto = String.valueOf(myAuto.getSelectedItem());

        Toast.makeText(this, mySelectedAuto, Toast.LENGTH_SHORT).show();
    }
}
