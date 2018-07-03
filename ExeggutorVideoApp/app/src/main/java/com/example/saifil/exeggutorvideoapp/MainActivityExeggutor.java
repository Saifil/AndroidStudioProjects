package com.example.saifil.exeggutorvideoapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.VideoView;

public class MainActivityExeggutor extends AppCompatActivity {

    VideoView vv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_exeggutor);

        vv = findViewById(R.id.my_video_view);
        String url = "http://sv67.onlinevideoconverter.com/download?file=g6a0b1a0j9f5c2e4";
        //add a video source to VideoView
        vv.setVideoPath(url);

        //vv.start(); //start the video
    }

    public void onClickEvolve(View view) {

        vv.start(); //start the video
    }

}
