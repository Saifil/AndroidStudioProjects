package com.example.saifil.dittoteleportbutton;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.content.res.Resources;

public class MainActivityDittoTele extends AppCompatActivity{

    public static int check = 0;
    Button mybtn;
    ViewGroup vg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ditto_tele);

        vg = (ViewGroup)findViewById(R.id.my_main_view);
        mybtn = (Button)findViewById(R.id.my_btn);

        mybtn.setTextColor(Color.RED);

        //set the dimensions of the buttons
        Resources r = getResources();
        int px = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,150,r.getDisplayMetrics());
        mybtn.setWidth(px);
        mybtn.setHeight(px / 2);

        mybtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                moveMyButton();
            }
        });

    }
    public void moveMyButton() {

        check = (check + 1) % 4;

        //add delay to the transition
        TransitionManager.beginDelayedTransition(vg);

        RelativeLayout.LayoutParams posRule = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        if (check == 0) {
            mybtn.setText("GOTTA");
            posRule.addRule(RelativeLayout.ALIGN_PARENT_TOP,RelativeLayout.TRUE);
        }
        else if (check == 1) {
            mybtn.setText("CATCH'EM");
            posRule.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,RelativeLayout.TRUE);
        }
        else if (check == 2) {
            mybtn.setText("ALL");
            posRule.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,RelativeLayout.TRUE);
            posRule.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,RelativeLayout.TRUE);
        }
        else if (check == 3) {
            mybtn.setText("POKEMON");
            posRule.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,RelativeLayout.TRUE);
        }
        mybtn.setLayoutParams(posRule);
    }
}
