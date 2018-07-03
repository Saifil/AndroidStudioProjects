package com.example.saifil.arcaninedataapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivityArcanine extends Activity {

    EditText myInput;
    TextView tv;
    MyDBHandler dbHandler; //java class created by us to setup database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_arcanine);

        myInput = findViewById(R.id.ip_txt);
        tv = findViewById(R.id.text_display);
        dbHandler = new MyDBHandler(this,null,null,1);

        //print the values of the database
        printDB();

    }

    public void printDB() {

        //retrieve the values of database into the string
        String dbString =  dbHandler.dbToString();


        tv.setText(dbString); //display the data

        //make the imput filed empty once it's submitted
        myInput.setText("");

    }

    public void onDataAdd(View view) { //add data on button click (ADD)

        //call the Moveset constructor and set the moveName variable to user i/p
        Movesets ms = new Movesets(myInput.getText().toString());

        dbHandler.addMoveset(ms); // add the value to the database

        printDB(); //print the DB

    }

    public void onDataDelete(View view) { //delete data on button click (DELETE)

        //get the item name entered by the user
        String delName = myInput.getText().toString();

        dbHandler.deleteMoveset(delName); //delete the data

        printDB(); //print the DB

    }


}
