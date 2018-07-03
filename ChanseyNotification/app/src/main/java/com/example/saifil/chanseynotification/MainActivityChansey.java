package com.example.saifil.chanseynotification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivityChansey extends AppCompatActivity {

    //build a notification
    NotificationCompat.Builder notification;
    private static final int uniqueID = 2002; //assign unique ID to the notification
    private static final String channelId = "my_channel_id"; //set a channel ID
    private static final String NOTIFICATION_CHANNEL_NAME = "myChannel"; //channel name

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chansey);

        //supported in newer versions
        notification = new NotificationCompat.Builder(this,channelId);
        /*
        first param is the context (this)
        second param is the channel ID
         */

        //on checking the notification it should disappear
        notification.setAutoCancel(true);

    }

    public void notifyMe(View view) { //called when button is clicked

        notification.setSmallIcon(R.drawable.chanseyimg); //set an icon to the notification
        notification.setTicker("Chansey Used Refresh"); // set a ticker
        //ticker is the small notification that appears on the top while using come other app
        notification.setWhen(System.currentTimeMillis()); //set the time when notification occurred
        notification.setContentTitle("POKEMON CENTER"); //set the title to notification
        notification.setContentText("Your Pokemon Are Completely Healed"); //set text to notification

        //direct the user back to the activity on clicking the notification
        Intent intent = new Intent(this,MainActivityChansey.class);

        //create a pending intent
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent); //set the notification content

        //manages the notifications, get notification service into it
        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        //if the version is greater than or equal to Oreo then we set the notification Channel
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) { //check SDK version

            //set up the notification attributes
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel notificationChannel = new NotificationChannel(channelId, NOTIFICATION_CHANNEL_NAME, importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            nm.createNotificationChannel(notificationChannel); //create a notification channel
        }

        nm.notify(uniqueID,notification.build()); //notify the user
    }

}
