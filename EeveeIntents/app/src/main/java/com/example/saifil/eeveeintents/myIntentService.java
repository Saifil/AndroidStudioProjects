package com.example.saifil.eeveeintents;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class myIntentService  extends IntentService {

    private static final String TAG = "com.example.saifil.eeveeintents"; // LOG message

    public myIntentService() { //constructor
        super("myIntentService"); // name of the class as parameter
    }

    @Override
    protected void onHandleIntent(Intent intent) { //Handles the Intent

        Log.i(TAG,"The service has started"); // Print to Log to check the service call(Intent call)
    }
}
