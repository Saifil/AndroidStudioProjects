package com.example.saifil.eeveeintents;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ActivityWater extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water);

        //Create a bundle reference onj.
        Bundle myData = getIntent().getExtras(); // get the passed data into myData

        if (myData == null) { // checks if myData is Null (return if null)
            return;
        }
        String myName = myData.getString("pokemonName"); // store the passes value into string

        final  TextView tv = (TextView)findViewById(R.id.top_txt_water);
        tv.setText(myName);
    }

}
