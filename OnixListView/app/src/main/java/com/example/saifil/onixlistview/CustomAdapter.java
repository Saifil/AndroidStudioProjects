package com.example.saifil.onixlistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

//Adapter class for our custom ListView
class CustomAdapter extends ArrayAdapter<String> { // remove the public keyword

    public CustomAdapter(Context context, String [] type) {
        // string that contains the list Items to be printed (type) here

        super(context,R.layout.custom_row,type);
        //custom row is the layout that has the custom list with image
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        //in this method we set a template which is applied to every list item
        //initialize the layout

        //initialize a LayoutInflater
        LayoutInflater myInflater = LayoutInflater.from(getContext());

        //create a reference to our layout
        View customView = myInflater.inflate(R.layout.custom_row,parent,false);
        /*
        first param is the layout with custom LinearView
        other two params are default
         */

        String singleType = getItem(position); //get the string selected
        //reference to the TextView in custom_row layout
        TextView tv = customView.findViewById(R.id.my_img_txtview);
        //reference to the image
        ImageView myImg = customView.findViewById(R.id.img_id);

        tv.setText(singleType); // sets the text to selected text

        //dynamically set the image
        if (singleType == "Water") {
            myImg.setImageResource(R.drawable.squirtle);
        }
        else if (singleType == "Grass") {
            myImg.setImageResource(R.drawable.bulbasaur);
        }
        else {
            myImg.setImageResource(R.drawable.charimage); // set the image to selected image in drawable
        }

        //myImg.setImageResource(R.drawable.charimage); // set the image to selected image in drawable

        return customView; // return the custom view
    }
}
