package com.example.saifil.arbokboundservice;

import android.app.Service;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Binder;
import android.os.IBinder;
import java.util.Date;
import java.util.Locale;

public class MyService extends Service {
    //current code is default code for connecting to bound serices

    //create a IBinder reference obj
    private final IBinder myBinder = new MyLocalBinder(); // calls the myLocalBinder method

    public MyService() {
    }

    public class MyLocalBinder extends Binder { // create a class for all service execution
        // A class within a class in java is called nested classes

        MyService getService() { // to connect to the service
            return MyService.this; // return a MyService type
        }
    }

    public String getCurrentTime() { // returns the current time

        //to return the current time use following code:
        /*SimpleDateFormat st = new SimpleDateFormat("HH:mm:ss",Locale.US);

        //first param is the format
        //second param is the time zone

        return (st.format(new Date()));
        */

        String s = "ATTACK MISSED!";
        return s;
    }

    @Override
    public IBinder onBind(Intent intent) { // used for bounded services
        return myBinder;
    }
}
