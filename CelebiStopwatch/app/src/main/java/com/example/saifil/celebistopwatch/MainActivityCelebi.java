package com.example.saifil.celebistopwatch;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class MainActivityCelebi extends Activity {

    private int seconds = 0; //keeps track of seconds elapsed
    private boolean isRunning = false; //check if timer is running

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_celebi);

        if (savedInstanceState != null) { //to avoid on screen rotation
            seconds = savedInstanceState.getInt("seconds");
            isRunning = savedInstanceState.getBoolean("isRunning");
        }

        runTimer(); //start the timer

    }

    /*
    when the screen is rotated a new activity is started on every rotation
    thus, the values of seconds and isRunning are reset, thus, we make a
    method to save the values and pass them every time the screen is rotated
     */
    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putInt("seconds",seconds);
        savedInstanceState.putBoolean("isRunning",isRunning);
    }

    public void onStartTimer(View view) {

        isRunning = true;
    }

    public void onStopTimer(View view) {

        isRunning = false;
        if (seconds !=  0) { //to cancel out an extra second caused to pause the timer
            seconds--;
        }
    }

    public void onResetTimer(View view) {

        isRunning = false;
        seconds = 0;
    }

    private void runTimer() {

        final TextView timeView  = findViewById(R.id.disp_txt);

        final Handler handler = new Handler(); //helps schedule the execution of a code
        handler.post(new Runnable() { //code to be ran right now (current time execution)
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                String time = String.format(Locale.US,"%d:%02d:%02d",hours,minutes,secs);
                timeView.setText(time);

                if (isRunning) { // while it is running increment the seconds
                    seconds++;
                }

                int delay = 1000;
                if (seconds == 1) { //to stop the very initial start timer delay and time delay after reset
                    delay = 0;
                }
                handler.postDelayed(this,delay); //case delay of 1 second

            }
        });

    }

}
