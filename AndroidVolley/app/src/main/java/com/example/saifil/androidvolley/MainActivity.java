package com.example.saifil.androidvolley;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private TextView tv, username, tv_email;
    private Button btn, button, json_btn;
    private Button img_btn;

    //url for server
    String server_url = "http://7f230a53.ngrok.io/Android/Volley/projectInfo.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.txt_usr_name_id);
        tv_email = findViewById(R.id.txt_email_id);
        button = findViewById(R.id.btn_user_id);
        json_btn = findViewById(R.id.json_array_btn_id);

        tv = findViewById(R.id.server_res_id);
        btn = findViewById(R.id.btn_server_id);
        img_btn = findViewById(R.id.img_nav_btn_id);

        json_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,JsonArray.class);
                startActivity(intent);
            }
        });

        img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ImageActivity.class);
                startActivity(intent);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Volley's default method (uses default values for cache)
                //create a request queue
//                final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
//
//                //request type (here string)
//                //param1: method, param2: server url
//                StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
//                        new Response.Listener<String>() { //response listener
//                            @Override
//                            public void onResponse(String response) {
//                                tv.setText(response); //print response
//                                requestQueue.stop();
//                            }
//                        },
//                        new Response.ErrorListener() { //error listener
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                tv.setText("error in connection"); //print error
//                                error.printStackTrace();
//                                requestQueue.stop();
//                            }
//                        });
//                //add the string req to teh request queue
//                requestQueue.add(stringRequest);

                //Custom request queue
//                Cache cache = new DiskBasedCache(getCacheDir(),1024*1024); //1mb size here
//                Network network = new BasicNetwork(new HurlStack());
//                final RequestQueue requestQueue = new RequestQueue(cache,network);
//                requestQueue.start();
//
//                StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
//                        new Response.Listener<String>() { //response listener
//                            @Override
//                            public void onResponse(String response) {
//                                tv.setText(response); //print response
//                                requestQueue.stop();
//                            }
//                        },
//                        new Response.ErrorListener() { //error listener
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                tv.setText("error in connection"); //print error
//                                error.printStackTrace();
//                                requestQueue.stop();
//                            }
//                        });
//                //add the string req to teh request queue
//                requestQueue.add(stringRequest);

                //using singleton

                //request type (here string)
                //param1: method, param2: server url
                StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                        new Response.Listener<String>() { //response listener
                            @Override
                            public void onResponse(String response) {
                                tv.setText(response); //print responserequestQueue.stop();
                            }
                        },
                        new Response.ErrorListener() { //error listener
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                tv.setText("error in connection"); //print error
                                error.printStackTrace();
                            }
                        });
                //add the string req to teh request queue using singleton
                MySingleton.getmInstance(getApplicationContext()).addToRequestQueue(stringRequest);

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //get info from db
                //using json
                String json_url = "http://7f230a53.ngrok.io/Android/Volley/projectInfo.php";

                //third param is string
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, json_url, (String)null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                String user_name = null, email = null;
                                try { //get data from server
                                    user_name = response.getString("user_name");
                                    email = response.getString("email");

                                    username.setText(user_name);
                                    tv_email.setText(email);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MainActivity.this, "error in json part", Toast.LENGTH_SHORT).show();
                            }
                        });
                //add to request queue
                MySingleton.getmInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);

            }
        });
    }
}
