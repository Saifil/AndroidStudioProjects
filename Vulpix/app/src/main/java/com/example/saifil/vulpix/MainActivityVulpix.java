package com.example.saifil.vulpix;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivityVulpix extends AppCompatActivity {

    public static int alternate = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_vulpix);

        //creates a reference to the button in view
        Button mybtn = (Button)findViewById(R.id.mypokebtn);

        //sets the event Listener
        mybtn.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) { // function called on click
                        //do stuff
                        String s;
                        if (alternate % 2 == 0) {
                            s = "VULPIX, I CHOOSE YOU!";
                        }
                        else {
                            s = "RIVAL GARRY WANTS TO FIGHT!";
                        }
                        TextView txt = (TextView)findViewById(R.id.mypoketext);
                        txt.setText(s);
                        alternate++;
                    }
                }
        );

        mybtn.setOnLongClickListener(
                new Button.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        String s;
                        s = "BLASTOISE FAINTED!";
                        TextView longText = (TextView)findViewById(R.id.mypoketext);
                        longText.setText(s);
                        return true;
                    }
                }
        );
    }
}
