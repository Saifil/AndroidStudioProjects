package com.example.saifil.volleyproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btn_main_id);
        tv = findViewById(R.id.textView);

//        if (Constants.is_login) {
//            tv.setText("Welcome");
//        } else {
//            tv.setText("Please login");
//        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,DisplayList.class);
                startActivity(intent);
            }
        });
    }
}
