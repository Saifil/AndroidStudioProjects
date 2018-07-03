package com.example.saifil.pichu;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout; // lets us add the layout (relative)
import android.widget.Button; // lets us add the button
import android.graphics.Color; // lets us change the color
import android.widget.EditText; //lets us edit user input text
import android.content.res.Resources; // resources
import android.util.TypedValue; // converting DIP to pixels (one use)


public class MainActivityPichu extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main_pichu);

        RelativeLayout relLayout = new RelativeLayout(this);
        Button btn = new Button(this);

        relLayout.setBackgroundColor(Color.CYAN); // used to change the background color
        btn.setBackgroundColor(Color.WHITE); // used to change the button color
        btn.setText("GoMyMan"); // adds text to the button

        RelativeLayout.LayoutParams btnDetails = new // initiates a rule storing container
                RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        //adding rule to the layout
        btnDetails.addRule(RelativeLayout.CENTER_HORIZONTAL);
        btnDetails.addRule(RelativeLayout.CENTER_VERTICAL);

        relLayout.addView(btn,btnDetails); // adds button to the view

        EditText username = new EditText(this);
        btn.setId(1); // sets ID to the button
        username.setId(2); // sets ID to button

        RelativeLayout.LayoutParams usernameDetails = new
                RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        usernameDetails.addRule(RelativeLayout.ABOVE,btn.getId()); // aligns the test vertically above the btn
        usernameDetails.addRule(RelativeLayout.CENTER_HORIZONTAL); // aligns it horizontally

        Resources r = getResources(); //fetches the resources associated with our device

        /*first param in applyDimension is the unit we require to convert from
        second param is the number of units
        third param is the resource, getDisplayMetrics gets the display resources*/
        int px = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,200,r.getDisplayMetrics());
        //px stores the converted value i.e., pixels
        //DIP is Display Independent Pixels

        username.setWidth(px); // sets the width of the text-box
        usernameDetails.setMargins(0,0,0,50); // sets bottom margin to the text

        relLayout.addView(username,usernameDetails);

        setContentView(relLayout); // sets our layout as the main layout

    }
}
