package com.example.saifil.starterradiobutton;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivityStarter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_starter);

    }

    public void onClicked(View view) {

        TextView tv = findViewById(R.id.textview_id);

        //get reference to the RadioGroup
        RadioGroup rg = findViewById(R.id.radio_group);

        int id = rg.getCheckedRadioButtonId(); //returns the id of the checked buttom

        String displayText;
        switch (id) {
            case R.id.fire_id: displayText = "Charmander";
            break;
            case R.id.water_id: displayText = "Squirtle";
            break;
            case R.id.grass_id: displayText = "Balbasaur";
            break;
            default: displayText = "its-a-me-a-mario";
            break;
        }

        tv.setText(displayText);

    }


}
