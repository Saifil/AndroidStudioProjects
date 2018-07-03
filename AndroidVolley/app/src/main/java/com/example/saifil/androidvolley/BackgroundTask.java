package com.example.saifil.androidvolley;

import android.content.Context;
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

public class BackgroundTask {

    Context context;
    ArrayList<Project> arrayList = new ArrayList<>();
    String json_url = "http://9316651a.ngrok.io/Android/Volley/projectInfo.php";

    public BackgroundTask(Context context) {
        this.context = context;
    }

    public ArrayList<Project> getList() { //retrieve list from db

        Log.e("TAG","BackgroundTask getList");

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, json_url, (String)null,
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

                                Log.i("TAG",project_id + " " + user_id + " " + project_title);

                                Project project = new Project(project_id,user_id,project_title);

                                arrayList.add(project);
                                Log.e("TAG","Count: " + response.length());

                                count++;

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Error in BackgroundTask", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                });
        Log.i("TAG", String.valueOf(arrayList.size()));
        MySingleton.getmInstance(context).addToRequestQueue(jsonArrayRequest);

        return arrayList;
    }
}
