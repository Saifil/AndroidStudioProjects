package com.example.saifil.koffeeshop;

import android.app.Activity;
import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class DrinksDisc extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drinks_disc);

        /*ListView listDrinks = findViewById(R.id.my_sec_list);

        ArrayAdapter<Drinks> listAdapter= new ArrayAdapter<Drinks>(
                this,
                android.R.layout.simple_list_item_1,
                Drinks.drinks
        );

        listDrinks.setAdapter(listAdapter);
*/
        //Toast.makeText(this, "HOLLA", Toast.LENGTH_LONG).show();


    }
}
