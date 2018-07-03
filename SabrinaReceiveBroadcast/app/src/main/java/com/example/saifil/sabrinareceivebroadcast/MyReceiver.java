package com.example.saifil.sabrinareceivebroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //method to receive the broaddcast

        //send a pop-up to display message
        Toast.makeText(context, "Broadcast Received", Toast.LENGTH_LONG).show();
        /*
        first param is the context
        second param is message to display
        third param is the duration to display the message
         */
    }
}
