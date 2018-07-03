package com.example.saifil.spinarakmultithreading;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.os.Message;

public class MainActivitySpinarak extends AppCompatActivity {

    //we create a Handler
    Handler hand = new Handler() { // lies in the main Thread (by default)

        @Override
        public void handleMessage(Message msg) {

            //write the main task to be performed
            TextView tv = (TextView)findViewById(R.id.my_txt);
            tv.setText("ARIADOS");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_spinarak);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity_spinarak, menu);
        return true;
    }

    public void changeText(View view) {

        Runnable r = new Runnable() { // implements Multithreading into java code

            @Override
            public void run() {
                //causes a delay of 10 seconds
                synchronized (this) { // is used to execute the threads in sequence
                    try {
                        wait(10000); // won't cause delay w/o the synchronize block
                    }
                    catch(Exception e) {}
                }

                //call the handler which has the main task
                hand.sendEmptyMessage(0);

            }
        };

        //lets us run the delay in the background, so the main thread is unaffected
        Thread th = new Thread(r);
        th.start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
