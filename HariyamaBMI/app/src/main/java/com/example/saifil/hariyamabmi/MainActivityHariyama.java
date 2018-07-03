package com.example.saifil.hariyamabmi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivityHariyama extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_hariyama);

    }

    public void takeCalculate(View view) {

        Intent intent = new Intent(this,CalculatePage.class);
        startActivity(intent);
    }
}
