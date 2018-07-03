package com.example.saifil.loginmysql;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void makeEntry(View view) {

        EditText fname_et, lname_et, usrname_et, pass_et, conPass_et, date_et;

        fname_et = findViewById(R.id.first_name);
        lname_et = findViewById(R.id.last_name);
        usrname_et = findViewById(R.id.user_name);
        pass_et = findViewById(R.id.pass_id);
        conPass_et = findViewById(R.id.con_pass_id);
        date_et = findViewById(R.id.date_id);

        String fname, lname, username, pass, conPass, date;

        fname = fname_et.getText().toString();
        lname = lname_et.getText().toString();
        username = usrname_et.getText().toString();
        pass = pass_et.getText().toString();
        conPass = conPass_et.getText().toString();
        date = date_et.getText().toString();

        if (pass.equals(conPass)) {

            //passing the user info into the background task
            String method = "register";
            BackgroundTask bt = new BackgroundTask(this);
            bt.execute(method,fname,lname,username,date,pass); //calls the doInBackground method

            /*
            when using in real app, redirect the user from here instead of finish
            */
        }
        else {
            Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show();
        }

    }

    public void redirectLogin(View view) {

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }


}
