package com.example.saifil.metapodfragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment; // import fragment suppport
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BottomSectionFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //we create a view using inflater
        View view = inflater.inflate(R.layout.bottom_picture_fragment,container,false);
        /*
        first param is the int reference to the layout of the fragment
        second param is the container
        third param is the boolean value (false)
         */
        return view;
    }
}
