package com.example.saifil.loginmysql;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

//use this class to insert the data
public class BackgroundTask extends AsyncTask<String,Void,String> {

    Context mCtx;
    AlertDialog alertDialog;

    public BackgroundTask(Context mCtx) {
        this.mCtx = mCtx;
    }

    @Override
    protected String doInBackground(String... strings) { //passes info into MySQL db

        String reg_url = "http://192.168.0.103/Android/v1/login.php";
        String login_url = "http://192.168.0.103/Android/v1/user_login.php";

        //create a variable that identifies the task to be performed
        String method = strings[0]; //holds the first value that was passed

        if (method.equals("register")) { //if user calls for registration


            //get the passed values into the variables
            String fname, lname, username, dob, password;
            fname = strings[1];
            lname = strings[2];
            username = strings[3];
            dob = strings[4];
            password = strings[5];

            try {

                URL url = new URL(reg_url); //ref obj to URL

                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST"); //set up a request method
                httpURLConnection.setDoOutput(true); //to pass information

                OutputStream OS = httpURLConnection.getOutputStream(); //get output stream

                BufferedWriter bufferedWriter = new BufferedWriter( //set the buffered writer
                        new OutputStreamWriter(OS,"UTF-8")
                );


                String data = URLEncoder.encode("fname","UTF-8") + "=" + URLEncoder.encode(fname,"UTF-8") + "&"
                        + URLEncoder.encode("lname","UTF-8") + "=" + URLEncoder.encode(lname,"UTF-8") + "&"
                        + URLEncoder.encode("username","UTF-8") + "=" + URLEncoder.encode(username,"UTF-8") + "&"
                        + URLEncoder.encode("dob","UTF-8") + "=" + URLEncoder.encode(dob,"UTF-8") + "&"
                        + URLEncoder.encode("password","UTF-8") + "=" + URLEncoder.encode(password,"UTF-8");


                //add the information into bufferedWriter
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();

                //close the OutputStream
                OS.close();

                //get the response from the server
                InputStream IS = httpURLConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader( //store the server response into a buffered reader
                        new InputStreamReader(IS,"iso-8859-1")
                );

                String response = "", line;
                while ((line = bufferedReader.readLine()) != null) { //while response exist

                    response += line;
                }

                bufferedReader.close();
                IS.close(); //no operation performed as there is no response set in the server

                httpURLConnection.disconnect(); //disconnect the httpURLConnection

                if (response.matches("")) { //if there is no response
                    return "Registration successful";
                }
                else if (response.equals("username taken")) {
                    return response;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                Log.e("myMssg",e.getMessage());
                e.printStackTrace();

            }
        }
        else if (method.equals("login")) {

            String username = strings[1];
            String password = strings[2];

            try {

                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream OS = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(
                        new OutputStreamWriter(OS,"UTF-8")
                );

                String data = URLEncoder.encode("login_name","UTF-8") + "=" + URLEncoder.encode(username,"UTF-8") + "&"
                        + URLEncoder.encode("login_pass","UTF-8") + "=" + URLEncoder.encode(password,"UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();

                OS.close();

                InputStream IS = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader( //store the server response into a buffered reader
                        new InputStreamReader(IS,"iso-8859-1")
                );

                String response = "", line;

                while ((line = bufferedReader.readLine()) != null) { //while response exist

                    response += line;
                }

                bufferedReader.close();
                IS.close();

                httpURLConnection.disconnect();

                return response;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return "failed";
    }

    @Override
    protected void onPreExecute() {

        alertDialog = new AlertDialog.Builder(mCtx).create(); //creates an alert dialog
        alertDialog.setTitle("Login Information");

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {


        if (result.equals("Registration successful")) { //if the response is from registration

            //send the message to the user
            Toast.makeText(mCtx,result, Toast.LENGTH_LONG).show();

            Intent intent = new Intent(mCtx,MainActivity.class);
            mCtx.startActivity(intent); //redirects to MainActivity
            ((Activity)mCtx).finish(); //ends the activity
        }
        else if (result.equals("username taken")) {

            Toast.makeText(mCtx, result, Toast.LENGTH_SHORT).show();

        }
        else { //if the response is from login

            alertDialog.setMessage(result);
            alertDialog.show();
        }

    }
    
}
