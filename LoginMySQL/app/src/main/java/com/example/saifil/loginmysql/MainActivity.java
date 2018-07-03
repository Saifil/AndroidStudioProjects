package com.example.saifil.loginmysql;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void onLogin(View view) {

        EditText username_et, password_et;

        username_et = findViewById(R.id.get_user_id);
        password_et = findViewById(R.id.get_pass_id);

        String username = username_et.getText().toString();
        String password = password_et.getText().toString();

        if (username.matches("") || password.matches("")) {
            Toast.makeText(this, "Enter username and password", Toast.LENGTH_SHORT).show();
        }
        else {

            String method = "login";
            BackgroundTask bt = new BackgroundTask(this);
            bt.execute(method,username,password);

        }

    }

    public void redirectME(View view) {

        Intent intent = new Intent(MainActivity.this,RegistrationActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
