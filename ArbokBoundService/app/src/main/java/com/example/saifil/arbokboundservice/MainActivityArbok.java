package com.example.saifil.arbokboundservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

//import the method 'myLocalBinder' of 'MyService' class
import com.example.saifil.arbokboundservice.MyService.MyLocalBinder;

public class MainActivityArbok extends AppCompatActivity {

    MyService myService; // reference obj. of MyService class
    boolean isBound = false; // check is the user is bound to the service or not

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_arbok);

        Intent i = new Intent(this,MyService.class); // create Intent reference obj.
        bindService(i,myConnection, Context.BIND_AUTO_CREATE); // initiates bound service
        /*
        first param is the intent
        second param is the ServiceConnection obj.
        third param is standard
         */

    }

    public void onAttackCall(View view) { // called when the button is clicked

        //gets the current time from method in MyService class
        String displayText = myService.getCurrentTime();

        TextView myTV = (TextView)findViewById(R.id.my_main_txt);
        myTV.setText(displayText);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity_arbok, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //create a service connection
    private ServiceConnection myConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            //code which is executed when the service is connected

            //create an reference obj. of MyLocalBinder which we have imported
            MyLocalBinder binder = (MyLocalBinder)iBinder; // iBinder is the passed parameter

            myService = binder.getService(); // store the service into myService obj.
            isBound = true;

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            //code which runs on service disconnection

            isBound = false;
        }
    };
}
