package com.example.saifil.bottomnavigation;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeFragment extends Fragment {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mCurrentUser;

    private Button onAddUserbtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment,container,false);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("user");
        mFirebaseAuth = FirebaseAuth.getInstance();

        myDisplayDimension(view);
        allButtons(view);
        return view;
    }

    @Override
    public void onResume() { //called every time the home screen appears
        super.onResume();
        Log.i("meme","onResume");
    }

    @Override
    public void onStart() { //called before onResume
        super.onStart();
        Log.i("meme","onStart");
    }

    public void allButtons(View view) {
        onAddUserbtn = view.findViewById(R.id.createEntry);
        onAddUserbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentUser = mFirebaseAuth.getCurrentUser();
                if (mCurrentUser != null) {
                    DatabaseReference uniqueUser = mDatabaseReference.child(mCurrentUser.getUid());
                    uniqueUser.child("Username").setValue(mCurrentUser.getDisplayName());
                }
            }
        });
    }

    public void myDisplayDimension(View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (getContext() != null) {
            ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

            int height = displayMetrics.heightPixels;
            int width = displayMetrics.widthPixels;

            float heightDP = convertPixelsToDp(height);
            float widthDP = convertPixelsToDp(width);
        }
    }

    public static float convertPixelsToDp(int px){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float dp = (float)px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }
}
