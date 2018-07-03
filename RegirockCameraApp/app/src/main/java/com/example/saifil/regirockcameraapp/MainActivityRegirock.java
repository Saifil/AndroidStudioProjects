package com.example.saifil.regirockcameraapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivityRegirock extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView myImgView; //reference to ImageView obj.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_regirock);

        myImgView = findViewById(R.id.img_id);
        Button myBtn = findViewById(R.id.my_btn);

        //check if the user has a camera
        if (!hasCamera()) {
            myBtn.setEnabled(false); // disable the button if no camera
        }

    }

    public boolean hasCamera() {

        //returns if the device has camera feature
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    public void onLocking(View view) { //called when button is clicked

        //initialize intent to launch the camera
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //start the intent (request the intent to capture the image)
        startActivityForResult(intent,REQUEST_IMAGE_CAPTURE);

    }

    //method to display the image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //check if the image is captured successfully
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //proceed if image was captured successfully

            //get the image
            Bundle extras = data.getExtras();
            Bitmap photo = (Bitmap)extras.get("data"); //get the image into bitmap format

            myImgView.setImageBitmap(photo); //set the imageView

        }
    }
}
