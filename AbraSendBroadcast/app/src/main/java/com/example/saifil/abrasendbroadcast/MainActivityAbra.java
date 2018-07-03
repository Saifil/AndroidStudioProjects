package com.example.saifil.abrasendbroadcast;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;


public class MainActivityAbra extends AppCompatActivity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_abra);

    }

    public void sendOutBroadcast(View view) {
        Intent i = new Intent(); // create Intent reference obj

        btn = (Button)findViewById(R.id.btn_Send);
        btn.setText("WAS CLICKED");
        //standard broadcast setup code
        i.setAction("com.example.saifil.abrasendbroadcast"); // pass the package name
        i.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES); //set a flag

        //send the broadcast
        sendBroadcast(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity_abra, menu);
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
