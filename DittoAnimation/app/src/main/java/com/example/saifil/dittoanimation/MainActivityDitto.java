package com.example.saifil.dittoanimation;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.transition.TransitionManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class MainActivityDitto extends AppCompatActivity {

    ViewGroup vg; // declare a ViewGroup type object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ditto);

        //create a ref obj of ViewGroup type to the main activity
        vg = (ViewGroup)findViewById(R.id.my_main_view);

        //add an onTouch option on the button
        vg.setOnTouchListener(
                new RelativeLayout.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) { // function called on touch
                        moveButton(); // User defined function to cause button animation
                        return false;
                    }
                }
        );
    }

    public void moveButton() {

        //add delay to the transition
        TransitionManager.beginDelayedTransition(vg);

        //Create a view reference to the button
        View mybtn = findViewById(R.id.my_btn_first);

        //set a rule container for the button
        RelativeLayout.LayoutParams posRule = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        //add rules to the Rule container

        //move the button to the bottom
        posRule.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,RelativeLayout.TRUE);

        //add the rule to the button
        mybtn.setLayoutParams(posRule);

        //grow button in size
        ViewGroup.LayoutParams szRule = mybtn.getLayoutParams(); // sets a rule container
        szRule.width = 450; // height of the button
        szRule.height = 300; // width of the button
        mybtn.setLayoutParams(szRule); // add the rules to the button
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity_ditto, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
