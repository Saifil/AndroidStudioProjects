package com.example.saifil.volleyrecyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String post_url = "http://192.168.0.105/Android/Volley/jsonProjectFormat.php";
    private String image_base_url = "http://192.168.0.105/Android/Volley/UploadedImages/";

    private RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    ArrayList<ProjectDetails> projectList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view_id);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        String[] data = {"gen1", "gen2", "gen3", "gen4"};
//        recyclerView.setAdapter(new DemoAdapter(data));

        //JSon
//        StringRequest stringRequest = new StringRequest(url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.d("TAG",response);
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d("TAG", "error");
//                    }
//                });
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
//
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST,post_url,(String)null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int count = 0;
                        while (count < response.length()) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(count);

                                String url = image_base_url + jsonObject.getString("project_image");
                                String title = jsonObject.getString("project_title");
                                String desc = jsonObject.getString("project_short_description");
                                String percent = "25" + "% Complete";

                                Log.d("TAG",title);

                                ProjectDetails projectDetails = new ProjectDetails(url,title,desc,percent);
                                projectList.add(projectDetails);

                                count++;

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.d("TAG", String.valueOf(projectList.size()));

                        adapter = new ProjectAdapter(MainActivity.this,projectList);
                        recyclerView.setAdapter(adapter);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("TAG",error.getMessage());
                        error.printStackTrace();
                    }
                });
        MySingleton.getmInstance(getApplicationContext()).addToRequestQueue(jsonArrayRequest);

    }
}
