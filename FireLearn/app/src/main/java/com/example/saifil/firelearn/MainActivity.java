package com.example.saifil.firelearn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    //TextView tv;
    DatabaseReference databaseReference;
    ListView ls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // tv = findViewById(R.id.text_id);

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://firelearn-c74a1.firebaseio.com/Name");
        /*databaseReference.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                String value = dataSnapshot.getValue(String.class);
                tv.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
        ls = findViewById(R.id.my_list_id);


    }
}
