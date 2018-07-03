package com.example.saifil.androidvolley;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

public class ImageActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button button;

    //url for server
    String server_url = "http://74f4a3d0.ngrok.io/Android/Volley/space.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        imageView = findViewById(R.id.db_img_id);
        button = findViewById(R.id.server_img_btn_id);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //retrieve image stored in db

                //max width, max height: 0,0 give og image size
                //param 4 scale type
                //param 5 decoding format (here null)
                ImageRequest imageRequest = new ImageRequest(server_url,
                        new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap response) { //receive the response
                                //set imageview
                                imageView.setImageBitmap(response); //response in bitmap
                            }
                        }, 0, 0, ImageView.ScaleType.CENTER_CROP, null,
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) { //handle error
                                Toast.makeText(ImageActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                error.printStackTrace();
                            }
                        });
                //add request to request queue
                MySingleton.getmInstance(getApplicationContext()).addToRequestQueue(imageRequest);
            }
        });
    }
}
