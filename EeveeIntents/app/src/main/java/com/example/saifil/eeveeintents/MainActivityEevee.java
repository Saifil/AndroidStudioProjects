package com.example.saifil.eeveeintents;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class MainActivityEevee extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_eevee);

        //create an Intent to the service class
        /*Intent intent = new Intent(this,myIntentService.class);
        startService(intent); // starts the service*/

        Intent myInt = new Intent(this,MyService.class);
        startService(myInt);

    }

    public void onClickWater(View view) { // called when the button is clicked

        //create an intent obj. (connect two activities)
        //second param is the main class of the second activity
        Intent i = new Intent(this,ActivityWater.class);

        //create a ref to the EditText
        final EditText firstip = (EditText)findViewById(R.id.firstinput);
        String name = firstip.getText().toString(); // get user i/p into string (name)

        i.putExtra("pokemonName",name); // send a message through intent

        startActivity(i); // starts(switches to the second activity)
    }

    public void onClickFire(View view) { // called when the button is clicked

        //create an intent obj. (connect two activities)
        //second param is the main class of the second activity
        Intent i = new Intent(this,ActivityFire.class);

        startActivity(i); // starts(switches to the second activity)
    }

    public void onClickElectric(View view) { // called when the button is clicked

        //create an intent obj. (connect two activities)
        //second param is the main class of the second activity
        Intent i = new Intent(this,ActivityElectric.class);

        startActivity(i); // starts(switches to the second activity)
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity_eevee, menu);
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
