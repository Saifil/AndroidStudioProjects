package com.example.saifil.kecleonimageeffects;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MainActivityKecleon extends AppCompatActivity {

    //create references to various obj.s
    public static ImageView myImgView;
    Drawable myPokemon;
    public static Bitmap bitmapImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_kecleon);

        myImgView = findViewById(R.id.img_id); // reference to img in MainActivity

        //store the req. image into myPokemon
        myPokemon = ResourcesCompat.getDrawable(getResources(),R.drawable.kecleonimg,null);
        /*
        first param is the resources
        second param is the img path
        third param is null
         */

        bitmapImg = ((BitmapDrawable)myPokemon).getBitmap(); //reference to the bitmap img (to be created)

        myImgView.setImageBitmap(bitmapImg); //set ImageView to the original image

    }

    public static void invertImage(View view) { //inverts the image on button click

        Bitmap original = bitmapImg;
        //set the image for finalImg by passing original img's width, height and configurations
        Bitmap finalImg = Bitmap.createBitmap(original.getWidth(),original.getHeight(),original.getConfig());

        int a, r, g, b;
        int pixelColor;
        int height = original.getHeight();
        int width = original.getWidth();

        //traverse through all the pixels in the image
        for (int y = 0; y <= height - 1; y++) {
            for (int x = 0; x <= width - 1; x++) {

                pixelColor = original.getPixel(x,y); //get pixel's color
                a = Color.alpha(pixelColor); //stores the alpha value of pixel
                //store the rgb values in the variables and invert them
                r = 255 - Color.red(pixelColor);
                g = 255 - Color.green(pixelColor);
                b = 255 - Color.blue(pixelColor);

                finalImg.setPixel(x,y,Color.argb(a,r,g,b)); //set pixel to the finalImage

            }
        }

        myImgView.setImageBitmap(finalImg); //set image view to the inverted image

    }

    public void applyFilter(View view) { //code for image filter

        //create layers
        Drawable [] layers = new Drawable[2]; //2 layers are to be used
        //set content of the first layer
        layers[0] = ResourcesCompat.getDrawable(getResources(),R.drawable.kecleonimg,null); //store image 1
        layers[1] = ResourcesCompat.getDrawable(getResources(),R.drawable.articunoteam,null); //store image 2

        //merge and store the two image
        LayerDrawable layerDrawable = new LayerDrawable(layers);

        myImgView.setImageDrawable(layerDrawable); //set the filter image
    }

}
