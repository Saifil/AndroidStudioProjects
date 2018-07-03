package com.example.saifil.counter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static int count = 0;
    private TextView tv;
    private Button plusBT, minusBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.disp_cnt);
        plusBT = findViewById(R.id.btn_plus);
        minusBT = findViewById(R.id.btn_minus);

        setCountText(); // set the initial count

        plusBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++; //inc count
                setCountText(); //set the count
            }
        });

        minusBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count != 0) {
                    count--; //dec count
                    setCountText(); //set the count
                }
            }
        });
    }

    public void setCountText() {
        tv.setText(String.valueOf(count));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_reset:
                count = 0;
                setCountText();
                break;
            case R.id.btn_edit:
                Toast.makeText(this, "Edited", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_setting:
                Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();
                break;
            default: break;
        }

        return super.onOptionsItemSelected(item);
    }
}
