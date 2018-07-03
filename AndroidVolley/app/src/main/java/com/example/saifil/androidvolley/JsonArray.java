package com.example.saifil.androidvolley;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class JsonArray extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<Project> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_array);

        recyclerView = findViewById(R.id.recycler_id);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true); //improve performance

        BackgroundTask backgroundTask = new BackgroundTask(JsonArray.this);
        arrayList = backgroundTask.getList();

        adapter = new RecyclerAdapter(arrayList);

        Log.e("TAG","JsonArray onCreate");
        recyclerView.setAdapter(adapter);

    }
}
