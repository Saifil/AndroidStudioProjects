package com.example.saifil.hariyamabmi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CalculatePage extends AppCompatActivity {

    EditText myHeight;
    EditText myWeight;
    Button myBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_page);

    }

    public void calculateMyBMI(View view) {

        double height = 0, weight = 0, bmi = 0;
        Intent intent = new Intent(this,DisplayPage.class);

        myHeight = findViewById(R.id.height_id);
        myWeight = findViewById(R.id.weigth_id);
        myBtn = findViewById(R.id.btn_submit);

        String stringHeight = myHeight.getText().toString();
        String stringWeight = myWeight.getText().toString();

        if (stringHeight.matches("") || stringWeight.matches("")) {
            Toast.makeText(this, "Enter the Fields", Toast.LENGTH_SHORT).show();
        }
        else {
            height = Double.parseDouble(stringHeight);
            weight = Double.parseDouble(stringWeight);

            height *= height;
            bmi = weight / height;

            intent.putExtra("value",bmi);
            startActivity(intent);
        }

    }

}
