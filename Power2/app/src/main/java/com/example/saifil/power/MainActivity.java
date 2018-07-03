package com.example.saifil.power;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button button;

    private String post_url = Constants.base_url + "jsonProjectFormat.php";
    private String image_base_url = Constants.base_image_url;

    private RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    ArrayList<ProjectDetails> projectList = new ArrayList<>();
    ArrayList<JSONObject> jsonObjectArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //FAB
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //redirect to add project page
                Intent intent = new Intent(MainActivity.this,CreateProject.class);
                startActivity(intent);
            }
        });

        //Drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //nav drawer
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //for nav button (add project)
        View headerview = navigationView.getHeaderView(0);
        button = headerview.findViewById(R.id.addProj_btn_id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //redirect to add project page
                Intent intent = new Intent(MainActivity.this,CreateProject.class);
                startActivity(intent);
            }
        });

        SharedPreferences sharedpref = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        String username = sharedpref.getString("username",""); //retrieve the username

//        if (username.matches("")) {
//            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
//            startActivity(intent);
//        }


        recyclerView = findViewById(R.id.recycler_view_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //display project cards
        displayProjects();
    }

    private void displayProjects() {
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
                                String my_percent = calcPercent(jsonObject);
                                String percent = my_percent + "% Complete";

                                ProjectDetails projectDetails = new ProjectDetails(url,title,desc,percent);
                                projectList.add(projectDetails);

                                jsonObjectArrayList.add(jsonObject);

                                count++;

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.d("TAG", String.valueOf(projectList.size()));

                        adapter = new ProjectAdapter(MainActivity.this,projectList,jsonObjectArrayList);
                        recyclerView.setAdapter(adapter);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("TAG",error.getMessage() + "error");
                        error.printStackTrace();
                    }
                });
        MySingleton.getmInstance(getApplicationContext()).addToRequestQueue(jsonArrayRequest);

    }

    private String calcPercent(JSONObject jsonObject) throws JSONException {
        String project_funds = jsonObject.getString("project_funds");
        String project_people = jsonObject.getString("project_people");

        double percent;
        if (!project_funds.equals("null")) {
            double project_funds_received = Integer.parseInt(jsonObject.getString("project_funds_received"));
            percent = (project_funds_received / Integer.parseInt(project_funds)) * 100;
        } else {
            double project_people_hired = Integer.parseInt(jsonObject.getString("project_people_hired"));
            percent = (project_people_hired / Integer.parseInt(project_people)) * 100;
        }
        return String.valueOf((int)Math.ceil(percent));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_explore) {
            Intent intent = new Intent(MainActivity.this,IndividualProject.class);
            startActivity(intent);
        } else if (id == R.id.nav_profile) {

        } else if (id == R.id.nav_logout) {
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_website) {

        } else if (id == R.id.nav_ivr) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
