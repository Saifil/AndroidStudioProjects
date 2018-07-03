package com.example.saifil.koffeeshop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        ListView listDrinks = findViewById(R.id.see_my_list);

        ArrayAdapter<Drinks> listAdapter= new ArrayAdapter<Drinks>(
                this,
                android.R.layout.simple_list_item_1,
                Drinks.drinks
        );

        listDrinks.setAdapter(listAdapter); //displays the list items from Drinks array

        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(SecondActivity.this,DrinkActivity.class);

                intent.putExtra("index_no",i);
                startActivity(intent);
            }
        };

        listDrinks.setOnItemClickListener(itemListener); //set the listener
    }



}
