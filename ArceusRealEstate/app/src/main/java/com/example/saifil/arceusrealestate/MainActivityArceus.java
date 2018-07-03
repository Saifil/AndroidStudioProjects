package com.example.saifil.arceusrealestate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivityArceus extends AppCompatActivity {

    EditText usr;
    EditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_arceus);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void onLogin(View view) {

        usr = findViewById(R.id.usr_id);
        pass = findViewById(R.id.pass_id);

        String username = usr.getText().toString();
        String password = pass.getText().toString();

        //modify when using backend technology
        String ogUsername = "admin";
        String ogPassword = "admin";

        if (username.matches("") || password.matches("")) { //check if any field is empty
            Toast.makeText(this, "Enter username and password.", Toast.LENGTH_SHORT).show();
        }
        else { //if both the fields are entered

            if (username.equals(ogUsername) && password.equals(ogPassword)) { //if matches

                Intent intent = new Intent(MainActivityArceus.this,NavigationActivity.class);
                startActivity(intent);
            }
            else {//if invalid
                Toast.makeText(this, "Invalid username or password.", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity_arceus, menu);
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
}
