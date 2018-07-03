package com.example.saifil.gastlyoverflow;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivityGastly extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_gastly);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity_gastly, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //function is called when a menu item is selected

        //create a RelativeLayout obj referenced to the main activity
        RelativeLayout rl = (RelativeLayout)findViewById(R.id.my_view_main);
        TextView tv = (TextView)findViewById(R.id.mytextOver);

        switch(item.getItemId()) { //check the selected item id
            case R.id.menu_fire:
                if (item.isChecked() == true) { // checks if the current item is checked
                    item.setChecked(false); // sets it to false
                }
                else {
                    item.setChecked(true); // sets it to true
                }
                tv.setText("CHARMDANDER");
                rl.setBackgroundColor(Color.RED); // changes the background color of main Activity
                break;
            case R.id.menu_water:
                if (item.isChecked() == true) {
                    item.setChecked(false);
                }
                else {
                    item.setChecked(true);
                }
                tv.setText("SQUIRTLE");
                rl.setBackgroundColor(Color.BLUE);
                break;
            case R.id.menu_grass:
                if (item.isChecked() == true) {
                    item.setChecked(false);
                }
                else {
                    item.setChecked(true);
                }
                tv.setText("BALBASAUR");
                rl.setBackgroundColor(Color.GREEN);
                break;
            default:
                return super.onOptionsItemSelected(item); // return the selected item in default case
        }
        return true;
    }
}
