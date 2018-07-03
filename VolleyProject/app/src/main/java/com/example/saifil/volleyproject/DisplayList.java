package com.example.saifil.volleyproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DisplayList extends AppCompatActivity {

    RecyclerView recyclerView;
    ProjectAdapter projectAdapter;
    List<ProjectDetails> projectList;

    String json_url = "http://7f230a53.ngrok.io/Android/Volley/projectInfo.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_list);

        projectList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true); //set the size as fixed
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, json_url, (String) null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int count = 0;
                        while (count < response.length()) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(count);

                                String project_id = jsonObject.getString("project_id");
                                String user_id = jsonObject.getString("user_id");
                                String project_title = jsonObject.getString("project_title");

                                Log.i("TAG",project_id + " " + user_id + " " +project_title);

                                projectList.add(new ProjectDetails(project_id,user_id,project_title));

                                count++;

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.i("TAG", String.valueOf(projectList.size()));

                        //inflate the adapter
                        projectAdapter = new ProjectAdapter(DisplayList.this,projectList);
                        recyclerView.setAdapter(projectAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DisplayList.this, "Error in JsonArrayRequest", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                });
        MySingleton.getmInstance(DisplayList.this).addToRequestQueue(jsonArrayRequest);

        //add items to be displayed
//        projectList.add(new ProjectDetails("1","2","My first project"));
//        projectList.add(new ProjectDetails("2","2","My second project"));
//        projectList.add(new ProjectDetails("3","3","My third project"));
//        projectList.add(new ProjectDetails("4","2","My fourth project"));
//        projectList.add(new ProjectDetails("5","2","My fifth project"));

//        Log.i("TAG", String.valueOf(projectList.size()));

//        //inflate the adapter
//        projectAdapter = new ProjectAdapter(this,projectList);
//        recyclerView.setAdapter(projectAdapter);

    }
}
