package com.example.saifil.power;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText et_email;
    private EditText et_password;
    private Button btn;

    String login_url = Constants.base_url + "login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_email = findViewById(R.id.email_id);
        et_password = findViewById(R.id.pass_id);

    }

    public void login_verify(View view) {
        final String email = et_email.getText().toString();
        final String password = et_password.getText().toString();

        if (email.matches("") || password.matches("")) {
            Toast.makeText(this, "Enter Email / Password", Toast.LENGTH_SHORT).show();
        } else { //server interaction
            StringRequest stringRequest = new StringRequest(Request.Method.POST, login_url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //get query status
                            try {
                                Log.i("TAG","inside try");
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);

                                String status = jsonObject.getString("status");
                                if (status.equals("false")) { //print error message
                                    Log.i("TAG","status=false");

                                    String message = jsonObject.getString("message");
                                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();

                                } else { //valid user
                                    Log.i("TAG","valid user");

                                    //store user details
                                    SharedPreferences sharedpref = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedpref.edit(); //edit the preference

                                    editor.putString("username",jsonObject.getString("user_name")); //add username
                                    editor.putString("type",jsonObject.getString("type")); //add type
                                    editor.putString("user_id",jsonObject.getString("user_id")); //add user_id

                                    editor.apply(); //apply the changes

                                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                    startActivity(intent);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("TAG","onErrorResponse");

                            Toast.makeText(LoginActivity.this, "db error", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError { //send the data to db
                    Log.i("TAG","getParams");

                    Map<String,String> params = new HashMap<String,String>();
                    params.put("email",email);
                    params.put("password",password);

                    return params;
                }
            };
            MySingleton.getmInstance(LoginActivity.this).addToRequestQueue(stringRequest);

        }
    }

    public void to_registration(View view) {
        //redirect to register
        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }
}
