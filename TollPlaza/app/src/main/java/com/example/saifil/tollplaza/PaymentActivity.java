package com.example.saifil.tollplaza;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class PaymentActivity extends AppCompatActivity {

    private FirebaseAuth mFireBaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private FirebaseDatabase mFirebaseDatabase; //entry point to db
    private DatabaseReference mWalletReference; //references specific part of the db
    private DatabaseReference mUserWallet; //ref to usrr wallet
    private DatabaseReference mActive;

    public ImageView qrCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        qrCode = findViewById(R.id.myImage);

        mFireBaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        mActive = mFirebaseDatabase.getReference().child("active");
        mWalletReference = mFirebaseDatabase.getReference().child("wallet");
        //mWalletReference.child(mFireBaseAuth.getUid()).setValue(INITIAL_BALANCE);

        String userID = mFireBaseAuth.getUid().toString();
        mUserWallet = mFirebaseDatabase.getReference().child("wallet/" + userID);
    }

    public void generateBarcode(View view) {
        updateAmount();

        String userID = mFireBaseAuth.getUid().toString();

        //generate QR code
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(userID, BarcodeFormat.QR_CODE,250,250);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);

            //send the bitmap to main activity
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("BitmapImage", bitmap);
            intent.putExtra("Balance",951);

            startActivity(intent);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    public void updateAmount() {
        mWalletReference.child(mFireBaseAuth.getUid()).setValue(951);
        //set active transaction as true

        int myValue = 1;
        mActive.child(mFireBaseAuth.getUid()).setValue(myValue);

    }
}
