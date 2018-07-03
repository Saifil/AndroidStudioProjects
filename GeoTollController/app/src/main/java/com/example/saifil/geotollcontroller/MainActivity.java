package com.example.saifil.geotollcontroller;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabasereference;

    //qr code scanner object
    private IntentIntegrator qrScan;

    private Spinner mSpinner;
    private String tollKeyMain;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,TollEntryActivity.class);
                startActivity(intent);
            }
        });

        mSpinner = findViewById(R.id.mySpinner_id);
        tv = findViewById(R.id.text_verify_id);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabasereference = mFirebaseDatabase.getReference().child("MyToll/details");

        initializeSpinner();

        qrScan = new IntentIntegrator(this);
    }

    private void initializeSpinner() {
        final List<String> myTolls = new ArrayList<>();
        final List<String> myTollKeys = new ArrayList<>();

        //add single time event listener
        mDatabasereference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    TollDetail details = snapshot.getValue(TollDetail.class);
                    if (details != null) {
                        String key = snapshot.getKey();
                        String name = details.name;
                        myTolls.add(name);
                        myTollKeys.add(key);
                    }
                }
                Log.d("MEME", String.valueOf(myTolls.size()));

                ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, myTolls);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner.setAdapter(adapter);

                mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        //Toast.makeText(MainActivity.this, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                        tollKeyMain = myTollKeys.get(i);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        Log.d("PAY","onNothingSelected");
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
            }
            else {
                //if qr contains data
                try {
                    String myKey = result.getContents();
                    verifyTollUser(myKey); //check for key


                }
                catch (Exception e) {}
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    private void verifyTollUser(String key) {
        final String userKey = key;
        DatabaseReference mRefQrActive = mFirebaseDatabase.getReference().child("MyQRCode/active/" + tollKeyMain);
        mRefQrActive.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean isPresent = false;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String dbKey = snapshot.getKey();
                    if (dbKey.equals(userKey)) {
                        isPresent = true;
                        QrDetails qrDetails = snapshot.getValue(QrDetails.class);
                        tv.setText("Verified");
                        break;
                    }
                }
                if (!isPresent) {
                    tv.setText("Not Found");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
//
//    public void onDisplayToll(View view) {
//        //add single time event listener
//        mDatabasereference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                String myText = "";
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    String key = snapshot.getKey();
//                    TollDetail details = snapshot.getValue(TollDetail.class);
//                    if (details != null) {
//                        myText += key + "\n";
//                    }
//                }
//                tv.setText(myText);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }

    public void scanQrCode(View view) {
        //initiating the qr code scan
        qrScan.initiateScan();
    }
}
