package com.example.saifil.pidgeotactionbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivityPidgeot extends AppCompatActivity {

    public static int level = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pidgeot);

        //needed for action bar (to be displayed)
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity_pidgeot, menu);
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
        else if (id == R.id.order_action) { //action on clicking the button on action bar
            level++;
            Toast.makeText(this,"Level: " + Integer.toString(level), Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.order_home) {
            Toast.makeText(this,"Current level is: " + Integer.toString(level),Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
