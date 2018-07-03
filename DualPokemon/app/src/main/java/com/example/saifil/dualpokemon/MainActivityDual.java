package com.example.saifil.dualpokemon;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class MainActivityDual extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dual);

    }

    public void onClicked(View view) {

        TextView tv = findViewById(R.id.txtView_id);

        //check if any option is selected or not
        boolean checked = ((CheckBox) view).isChecked();

        String displayText = "";

        switch(view.getId()) { //gets the ID of selected view element
            case R.id.water_id:
                if (checked) {
                    displayText = "Blastoise";
                }
                break;
            case R.id.ice_id:
                if (checked) {
                    displayText = "Glalie";
                }
                break;
            default:
                displayText = "Normal";
                break;
        }

        tv.setText(displayText);

    }
}

