package com.example.saifil.onixlistview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivityOnix extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_onix);

        //create a array of strings to display as a list
        String[] pokemonType = {"Fire","Water","Grass"};

        //create a ListAdapter to display the content of array in LinearView
        ListAdapter myAdapter = new CustomAdapter(this,pokemonType);
        /*
        first param is this
        second param is the type of list we want to display (here simple_list_item_1)
        third param is our string array (content of the list)
         */

        ListView lv = findViewById(R.id.my_list); // create a reference to listview obj.
        lv.setAdapter(myAdapter); // set the adapter to out ListView

        //add an action listener to ListView
        lv.setOnItemClickListener(
                new AdapterView.OnItemClickListener() { // triggered when an item is selected

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        //store the selected value in a string
                        //adapterView and i are the passed value, i is the pos of selected value
                        String type = String.valueOf(adapterView.getItemAtPosition(i));

                        //pop up a text on selection
                        Toast.makeText(MainActivityOnix.this,type,Toast.LENGTH_SHORT).show();
                        /*
                        first param is the activity in which message is to be popped
                        second param is the string to display
                        third param is the time for which the message is to be displayed
                         */

                    }
                }
        );
    }

}
