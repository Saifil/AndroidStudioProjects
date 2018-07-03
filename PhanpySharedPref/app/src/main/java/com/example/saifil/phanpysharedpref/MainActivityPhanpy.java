package com.example.saifil.phanpysharedpref;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivityPhanpy extends AppCompatActivity {

    EditText username;
    EditText password;
    TextView displayText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_phanpy);

        username = findViewById(R.id.usrname_id);
        password = findViewById(R.id.pass_id);
        displayText = findViewById(R.id.display_txt_id);

    }

    public void onAddUser(View view) { //onClick ADD button

        //create a shared preference reference obj.
        SharedPreferences sharedpref = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        /*
        first param is the name of the preference
        second param is the mode
         */
        SharedPreferences.Editor editor = sharedpref.edit(); //edit the preference

        //add the name value pair to the editor
        editor.putString("username",username.getText().toString()); //add username
        editor.putString("password",password.getText().toString()); //add password

        editor.apply(); //apply the changes

        //pop up message for user when data is added
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();

    }

    public void onDisplayUser(View view) { //onClick DISPLAY button

        SharedPreferences sharedpref = getSharedPreferences("userinfo", Context.MODE_PRIVATE);

        String name = sharedpref.getString("username",""); //retrieve the username
        String pass = sharedpref.getString("password",""); //retrieve the password

        displayText.setText(name + "\n" + pass); //display the user info

    }

}
