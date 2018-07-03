package com.example.saifil.imageglide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.img_glide_id);

        String img_url = "https://www.w3schools.com/howto/img_fjords.jpg";

        //Load image using glide
        /*
        with : context
        load: img url
        into : imageview
         */
        Glide.with(this).load(img_url).into(imageView);
    }
}
