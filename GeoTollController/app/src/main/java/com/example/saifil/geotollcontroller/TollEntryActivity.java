package com.example.saifil.geotollcontroller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TollEntryActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabseReference;

    private DatabaseReference mTollDetails;
    private DatabaseReference mTollSingleJourney;
    private DatabaseReference mTollReturnJourney;

    private EditText tollNameET;
    private EditText truckSingleET, fourSingleET, twoSingleET;
    private EditText truckReturnET, fourReturnET, twoReturnET;
    private CheckBox rtnEnabledCB;

    double latD = 0.0, lngD = 0.0;
    String tollName = null;

    private static final int PLACE_PICKER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toll_entry);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabseReference = mFirebaseDatabase.getReference().child("MyToll");

        mTollDetails = mDatabseReference.child("details");
        mTollSingleJourney = mDatabseReference.child("single");
        mTollReturnJourney = mDatabseReference.child("return");

        //References to Edit text
        tollNameET = findViewById(R.id.tollname_id);
        truckSingleET = findViewById(R.id.truckS_id);
        truckReturnET = findViewById(R.id.truckR_id);
        fourSingleET = findViewById(R.id.fourS_id);
        fourReturnET = findViewById(R.id.fourR_id);
        twoSingleET = findViewById(R.id.twoS_id);
        twoReturnET = findViewById(R.id.twoR_id);

        //checkbox reference
        rtnEnabledCB = findViewById(R.id.rtncharge_id);

        //check the checkbox by default
        rtnEnabledCB.setChecked(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this,data);
                LatLng latLng = place.getLatLng();
                tollName = (String)place.getName(); //set the name
                latD = latLng.latitude; //set the lat
                lngD = latLng.longitude; //set the lng

                //set the tollName to ET
                tollNameET.setText(tollName);
            }
        }
    }

    public void pickLocation(View view) {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(TollEntryActivity.this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    public void addToll(View view) {
        double truckSingle, fourSingle, twoSingle;
        double truckReturn, fourReturn, twoReturn;

        tollName = tollNameET.getText().toString().trim();

        String truckSingleString = truckSingleET.getText().toString();
        String fourSingleString = fourSingleET.getText().toString();
        String twoSingleString = twoSingleET.getText().toString();

        String truckReturnString = truckReturnET.getText().toString();
        String fourReturnString = fourReturnET.getText().toString();
        String twoReturnString = twoReturnET.getText().toString();

        if (tollName.matches("") || latD == 0.0 || lngD == 0.0
                || truckSingleString.matches("") || fourSingleString.matches("") || twoSingleString.matches("")) {
            Toast.makeText(this, "Enter the details.", Toast.LENGTH_SHORT).show();
        }
        else {

            truckSingle = Double.parseDouble(truckSingleString);
            fourSingle = Double.parseDouble(fourSingleString);
            twoSingle = Double.parseDouble(twoSingleString);

            TollDetail tollDetail = new TollDetail(tollName,latD,lngD); //create obj for details
            TollCharge tollChargeSingle = new TollCharge(truckSingle,fourSingle,twoSingle); //create obj. for single trip charge

            if (rtnEnabledCB.isChecked()) {
                if (truckReturnString.matches("") || fourReturnString.matches("") || twoReturnString.matches("")) {
                    Toast.makeText(this, "Enter return journey charges", Toast.LENGTH_SHORT).show();
                }
                else {
                    truckReturn = Double.parseDouble(truckReturnET.getText().toString());
                    fourReturn = Double.parseDouble(fourReturnET.getText().toString());
                    twoReturn = Double.parseDouble(twoReturnET.getText().toString());

                    DatabaseReference tollChildDetails = mTollDetails.push(); //create a new child of details

                    //get the key of newly generated child
                    String key = tollChildDetails.getKey();

                    //make return trip obj. too
                    TollCharge tollChargeReturn = new TollCharge(truckReturn,fourReturn,twoReturn);

                    //push to the newly added MyToll child
                    tollChildDetails.setValue(tollDetail);
                    mTollSingleJourney.child(key).setValue(tollChargeSingle);
                    mTollReturnJourney.child(key).setValue(tollChargeReturn);

                    Toast.makeText(this, "Added successfully", Toast.LENGTH_SHORT).show();

                    //redirect to main activity
                    Intent intent = new Intent(this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
            else { //no return trip cost included
                DatabaseReference tollChildDetails = mTollDetails.push(); //create a new child of details

                //get the key of newly generated child
                String key = tollChildDetails.getKey();

                //push to the newly added MyToll child
                tollChildDetails.setValue(tollDetail);
                mTollSingleJourney.child(key).setValue(tollChargeSingle);

                Toast.makeText(this, "Added successfully", Toast.LENGTH_SHORT).show();

                //redirect to main activity
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

}
