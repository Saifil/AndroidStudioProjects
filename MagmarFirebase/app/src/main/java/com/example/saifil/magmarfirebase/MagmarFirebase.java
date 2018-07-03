package com.example.saifil.magmarfirebase;

import android.app.Application;

import com.firebase.client.Firebase;

//enables firebase on every activity
public class MagmarFirebase extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);
    }
}
