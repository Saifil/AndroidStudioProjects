package com.example.saifil.mrmimegestures;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.widget.TextView; // for text view
import android.view.MotionEvent; // Motion detection
import android.support.v4.view.GestureDetectorCompat; // Gesture detection

public class MainActivityMime extends AppCompatActivity implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    private TextView txtMsg;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_mime);

        txtMsg = (TextView)findViewById(R.id.myMessage); // set the txtMsg to TextView on screem
        this.gestureDetector = new GestureDetector(this,this); // gestureDetector is the initiated obj. on top
        gestureDetector.setOnDoubleTapListener(this); // set double tap listener
    }

    //press control + enter (to implement all the methods)

    //override
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    //interface
    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        txtMsg.setText("SingleTapConfirmed");
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        txtMsg.setText("onDoubleTap");
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        txtMsg.setText("onDoubleTapEvent");
        return false;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        txtMsg.setText("onDown");
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {
        txtMsg.setText("onShowPress");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        txtMsg.setText("onSingleTapUp");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        txtMsg.setText("onScroll");
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        txtMsg.setText("onLongPress");
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        txtMsg.setText("onFling");
        return false;
    }
}
