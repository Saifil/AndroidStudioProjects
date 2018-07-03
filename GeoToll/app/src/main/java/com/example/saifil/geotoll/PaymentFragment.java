package com.example.saifil.geotoll;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PaymentFragment extends Fragment {


    private Button  pay;
    private Spinner journeyType, vehicleType;
    private TextView dispAmount, displayTollName;
    private ProgressBar mProgressBar;
    private Spinner mSpinner;

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mTollChargeReference;
    DatabaseReference mTollDetails;

    public static double singleJ[] = new double[3];
    public static double returnJ[] = new double[3];

    public double cashOutAmount = 0.0;

    public static int journeyPos = 0;
    public static int vehiclePos = 0;
    public static String tollName;
    public static String tollKey;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.payment_fragment,container,false);

        displayTollName = view.findViewById(R.id.display_my_toll_id);
        pay = view.findViewById(R.id.payTollbtn_id);
        dispAmount = view.findViewById(R.id.cost_disp_id);
        journeyType = view.findViewById(R.id.mySpinner_id);
        vehicleType = view.findViewById(R.id.myVehicle_id);
        mProgressBar = view.findViewById(R.id.indeterminateBar);
        mSpinner = view.findViewById(R.id.myToll_spinner_id);

        mFirebaseDatabase = FirebaseDatabase.getInstance();

        //get the key from bundle
        Bundle bundle = this.getArguments();
        if (bundle != null) { //called from notification
            tollKey = bundle.getString("toll_key_main");
            tollName = bundle.getString("toll_name_main");

            bundle.remove("toll_key_main");
            bundle.remove("toll_name_main");

            displayTollName.setText(tollName); //set the name of the toll

            Log.d("PAY",tollKey);

            //disable spinner
            mSpinner.setEnabled(false);

            //find the cost for journey
            findCost(true,tollKey);
            findCost(false,tollKey);
            getSpecificToll();
        }
        else { //called manually
            getCustomToll(view);
        }
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cashOutAmount == 0.0) {
                    Toast.makeText(getActivity(), "Select a toll", Toast.LENGTH_SHORT).show();
                }
                else {
                    startPayment();
                }
            }
        });
        return view;
    }

    private void findCost(boolean b, String key) {
        final String myKey = key;
        final boolean myCheck = b;
        //set progress bar
        if (myCheck) {
            try {
                mProgressBar.setVisibility(View.VISIBLE);
            }
            catch (Exception e) {
                Log.e("PAY", e.getMessage());
            }
        }

        //set the journey type
        if (b) { //single
            mTollChargeReference = mFirebaseDatabase.getReference().child("MyToll/single");
        }
        else {
            mTollChargeReference = mFirebaseDatabase.getReference().child("MyToll/return");
        }

        //get the cost
        mTollChargeReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String dbKey = snapshot.getKey();
                    TollCharge tollCharge = snapshot.getValue(TollCharge.class);

                    if (dbKey.equals(myKey)) { //matches the user toll
                        Log.d("PAY","key matched");
                        if (myCheck && tollCharge != null) { //single
                            singleJ[0] = tollCharge.truck;
                            singleJ[1] = tollCharge.fourWheeler;
                            singleJ[2] = tollCharge.twoWheeler;
                            Log.d("PAY",singleJ[0] + " " + singleJ[1] + " " + singleJ[2]);
                        }
                        else if (!myCheck && tollCharge != null) {
                            returnJ[0] = tollCharge.truck;
                            returnJ[1] = tollCharge.fourWheeler;
                            returnJ[2] = tollCharge.twoWheeler;
                            Log.d("PAY",returnJ[0] + " " + returnJ[1] + " " + returnJ[2]);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        if (!myCheck) {
            try {
                mProgressBar.setVisibility(View.GONE);
            }
            catch (Exception e) {
                Log.e("PAY", e.getMessage());
            }
        }
        String dispMytext = "Amount: Rs. " + cashOutAmount;
        dispAmount.setText(dispMytext);
    }

    private void getSpecificToll() { //User was send by notification
        //set a listener to the Spinner
        journeyType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                journeyPos = i;
                setAmountText();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        vehicleType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                vehiclePos = i;
                setAmountText();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void setAmountText() { //set the amount text
        if (journeyPos == 0) {
            String displayText;

            switch (vehiclePos) {
                case 0: cashOutAmount = singleJ[0];
                    break;
                case 1: cashOutAmount = singleJ[1];
                    break;
                case 2: cashOutAmount = singleJ[2];
                    break;
                default: cashOutAmount = 100.0;
                    break;
            }
            displayText = "Amount: Rs. " + cashOutAmount;
            dispAmount.setText(displayText);
        }
        else if (journeyPos == 1) {
            String displayText;

            switch (vehiclePos) {
                case 0: cashOutAmount = returnJ[0];
                    break;
                case 1: cashOutAmount = returnJ[1];
                    break;
                case 2: cashOutAmount = returnJ[2];
                    break;
                default: cashOutAmount = 100.0;
                    break;
            }
            displayText = "Amount: Rs. " + cashOutAmount;
            dispAmount.setText(displayText);
        }

    }

    private void getCustomToll(View view) {
        final View myview = view;
        mProgressBar.setVisibility(View.GONE);

        final List<String> myTolls = new ArrayList<>();
        final List<String> myTollKeys = new ArrayList<>();

        //String[] myListString = {"Saifil","Momin","Saif"};

        mTollDetails = mFirebaseDatabase.getReference().child("MyToll/details");
        mTollDetails.addListenerForSingleValueEvent(new ValueEventListener() {
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
                ArrayAdapter<String> adapter = new ArrayAdapter<>(myview.getContext(), android.R.layout.simple_spinner_item, myTolls);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner.setAdapter(adapter);

                mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        tollName = adapterView.getSelectedItem().toString(); //set the toll name
                        displayTollName.setText(tollName); //set text view

                        //find the cost for journey
                        findCost(true,myTollKeys.get(i));
                        findCost(false,myTollKeys.get(i));
                        getSpecificToll();
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

    private void startPayment() {
        int amount = (int)cashOutAmount; //amount to deduce

        Checkout checkout = new Checkout();

        //set app logo here
        checkout.setImage(R.mipmap.ic_launcher);

        //reference to current activity
        final Activity activity = getActivity();

        //Pass your payment options to the Razorpay Checkout as a JSONObject
        try {
            JSONObject options = new JSONObject();

            //Use the same name fields
            options.put("name", "Merchant Name");
            options.put("description", "Order #123456");
            options.put("currency", "INR");
            options.put("amount", amount * 100);

            //set the paid toll parameters
            TollConstants.TOLL_NAME = tollName;
            TollConstants.TOLL_KEY = tollKey;
            TollConstants.TOLL_JOURNEY = journeyPos;
            TollConstants.TOLL_VEHICLE = vehiclePos;
            TollConstants.TOLL_COST = amount;

            checkout.open(activity, options);
        } catch(Exception e) {
            Log.e("PAY", "Error in starting Razorpay Checkout", e);
        }
    }

    //Success and failures are done in main activity
}
