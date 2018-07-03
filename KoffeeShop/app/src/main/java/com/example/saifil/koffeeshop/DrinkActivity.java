package com.example.saifil.koffeeshop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class DrinkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        Bundle myData = getIntent().getExtras(); // get the passed data into myData
        if (myData == null) { // checks if myData is Null (return if null)
            return;
        }

        int value = myData.getInt("index_no");

        Drinks drinks = new Drinks();

        String name = drinks.getName(value);
        String desc = drinks.getDesc(value);
        int imgID = drinks.getImgID(value);

        TextView title = findViewById(R.id.txt_title_id);
        TextView body = findViewById(R.id.txt_body_id);
        ImageView img = findViewById(R.id.my_img_id);

        title.setText(name);
        body.setText(desc);
        img.setImageResource(imgID);

    }

}
