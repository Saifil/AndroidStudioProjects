package com.example.saifil.eeveeintents;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

    private static final String TAG = "com.example.saifil.eeveeintents";

    public MyService() { // constructor
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //called on the start of service
        Log.i(TAG,"onStart called"); // second param is the message to display

        //create a thread (needed in service class as opposed to IntentService,
            //where it is automatically created
        Runnable rn = new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 5; i++) { // execute the delay 5 times
                    synchronized (this) { // run in syn with other thread (makes sure delay takes place)
                        try {
                            //displays the log message every 5 seconds for 5 time
                            wait(5000);
                            Log.i(TAG,"Service is running"); // write to the Log
                        }
                        catch(Exception e) {}
                    }
                }
            }
        };

        Thread th = new Thread(rn); // create thread reference obj
        th.start(); // start the thread

        //return int value
        return Service.START_STICKY; //restart the service if it crashes in some case
    }

    @Override
    public void onDestroy() {
        //execute when service is destroyed
        Log.i(TAG,"The service was destroyed");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
