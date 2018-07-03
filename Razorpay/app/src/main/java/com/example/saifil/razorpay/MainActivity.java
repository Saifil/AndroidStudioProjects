package com.example.saifil.razorpay;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements PaymentResultListener {

    private static final String TAG = "MEME";

    private EditText amtET;
    private Button btn;

    int amount; //amount to be paid

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amtET = findViewById(R.id.amnt_id);
        btn = findViewById(R.id.myPaybtn_id);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (amtET.getText().toString().matches("")) {
                    Toast.makeText(MainActivity.this, "Enter amount", Toast.LENGTH_SHORT).show();
                }
                else {
                    startPayment();
                }
            }
        });
    }

    private void startPayment() {
        amount = Integer.parseInt(amtET.getText().toString());

        Checkout checkout = new Checkout();

        //set app logo here
        checkout.setImage(R.mipmap.ic_launcher);

        //reference to current activity
        final Activity activity = this;

        //Pass your payment options to the Razorpay Checkout as a JSONObject
        try {
            JSONObject options = new JSONObject();

            //Use the same name fields
            options.put("name", "Merchant Name");
            options.put("description", "Order #123456");
            options.put("currency", "INR");
            options.put("amount", amount * 100);

            checkout.open(activity, options);
        } catch(Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }

    }

    @Override
    public void onPaymentSuccess(String s) { //called when payment is successful
        Toast.makeText(this, "Payment was successful", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPaymentError(int i, String s) { //called when payment fails
        Toast.makeText(this, "Payment failed", Toast.LENGTH_SHORT).show();
    }
}
