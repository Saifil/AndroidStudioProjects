package com.example.saifil.rapdishxyzsensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivityRapdish extends AppCompatActivity implements SensorEventListener {

    Sensor accelerometer; //define a sensor
    SensorManager sm;
    TextView displayText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_rapdish);

        //stores the access to the system service(sensor)
        sm = (SensorManager)getSystemService(SENSOR_SERVICE);

        //reference to the accelerometer
        accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //register the sensor manager
        sm.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);

        displayText = findViewById(R.id.txt_id);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) { //set the TextView with the co-ordinates

        //sendorEvent.values array has the x, y, z co-ordinates
        displayText.setText("x: "+ sensorEvent.values[0]
                            + "\nY: "+ sensorEvent.values[1]
                            + "\nZ: "+ sensorEvent.values[2]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
