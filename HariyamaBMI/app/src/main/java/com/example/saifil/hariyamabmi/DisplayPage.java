package com.example.saifil.hariyamabmi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import java.text.DecimalFormat;

public class DisplayPage extends AppCompatActivity {

    TextView value;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_page);

        value = findViewById(R.id.bmi_disp);
        result = findViewById(R.id.result_id);

        Bundle myData = getIntent().getExtras();
        if (myData == null) {
            return;
        }
        double bmi = myData.getDouble("value");

        DecimalFormat df = new DecimalFormat(".##");
        bmi = Double.parseDouble(df.format(bmi));

        value.setText(String.valueOf(bmi));

        String displayResult;
        if (bmi < 18.5) {
            displayResult = "EAT SKINNY LEGS";
        }
        else if (bmi > 25) {
            displayResult = "STOP FATASS";
        }
        else {
            displayResult = "GG MY MAN";
        }
        result.setText(displayResult);

    }

}
