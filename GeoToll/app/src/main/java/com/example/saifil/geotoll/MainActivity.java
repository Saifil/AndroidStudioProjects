package com.example.saifil.geotoll;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.razorpay.PaymentResultListener;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements PaymentResultListener {

    private static final String TAG = "MEME";

    private FirebaseAuth mFirebaseAuth; //reference the auth
    private FirebaseAuth.AuthStateListener mAuthStateListener; //listener for any auth state change
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mActiveQrReference;
    private DatabaseReference mMostRecentQR;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mQrBitmapImage;

    private final static int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseStorage = FirebaseStorage.getInstance();

        myAuthentication(); //handles auth
        if (mFirebaseAuth.getCurrentUser() != null) {
            myBottomNavigation();
       }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) { //attempted to signed in
            if (resultCode == RESULT_OK) { //sign in successful

                Toast.makeText(this, "Signed in", Toast.LENGTH_SHORT).show();
                Log.d(TAG,"onActivityResult for Signin");
                myBottomNavigation(); //start the app activity by calling Map fragment
            }
            else if (resultCode == RESULT_CANCELED) { //sign in failed

                Toast.makeText(this, "Sign in cancelled", Toast.LENGTH_SHORT).show();
                finish(); //end the activity(before it reaches the onResume)
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_logout: FirebaseAuth.getInstance().signOut();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mAuthStateListener != null) {
            //detaches the listener when app is paused
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        //attaches the listener when the app is resumed
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    private void myAuthentication() {
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                //create a user which points to the current user
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) { //user is signed in
                }
                else { //user is not signed in
                    //used to get the user's country iso code

                    //start a new activity with firebaseui-auth default sign in methods
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(
                                            Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                                    new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                                    .build(),
                            RC_SIGN_IN);

                }
            }
        };

    }

    public void myBottomNavigation() {
        final FragmentManager manager = getSupportFragmentManager();

        if (isNotified()) {
            String tollKey = getIntent().getStringExtra("TollKey");
            String tollName = getIntent().getStringExtra("TollName");
            getIntent().removeExtra("TollKey"); //remove the key
            getIntent().removeExtra("TollName"); //remove the name

            //set the key in the bundle
            Bundle bundle = new Bundle();
            bundle.putString("toll_key_main",tollKey);
            bundle.putString("toll_name_main",tollName);
            PaymentFragment paymentFragment = new PaymentFragment();
            paymentFragment.setArguments(bundle);
            manager.beginTransaction().replace(R.id.my_frame_id, paymentFragment).commit();
        }
        else {
            //sets home page as the default fragment on creating
            manager.beginTransaction().replace(R.id.my_frame_id, new MapFragment()).commit();
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        //disables the shift animation
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {

                            case R.id.action_map:
                                manager.beginTransaction().replace(R.id.my_frame_id, new MapFragment()).commit();
                                break;
                            case R.id.action_qr:
                                manager.beginTransaction().replace(R.id.my_frame_id, new QRFragment()).commit();
                                break;
                            case R.id.action_payments:
                                manager.beginTransaction().replace(R.id.my_frame_id, new PaymentFragment()).commit();
                                break;
                            case R.id.action_account:
                                manager.beginTransaction().replace(R.id.my_frame_id, new AccountFragment()).commit();
                                break;
                            default:
                                break;
                        }
                        return true;
                    }
                }
        );
    }

    private boolean isNotified() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.getStringExtra("TollKey") != null) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onPaymentSuccess(String s) {
        Log.d("PAY","success main activity");

        //set the TollDetails for payment
        String tollName = TollConstants.TOLL_NAME;
        final String tollKey = TollConstants.TOLL_KEY;
        final int journeyPos = TollConstants.TOLL_JOURNEY;
        int vehiclePos = TollConstants.TOLL_VEHICLE;
        final int amount = TollConstants.TOLL_COST;

        final String userID = mFirebaseAuth.getCurrentUser().getUid();
        Log.d("PAY","onPaymentSuccess: " + tollName);

        //make the entry to db here

        //get the ref to given toll location under active
        mActiveQrReference = mFirebaseDatabase.getReference().child("MyQRCode/active/" + tollKey);
        //ref to store the users most recent qrcode
        mMostRecentQR = mFirebaseDatabase.getReference().child("MyQRCode/mostRecent/" + userID);
        mQrBitmapImage = mFirebaseStorage.getReference().child("MyQRImage");

        final DatabaseReference qrChild = mActiveQrReference.push(); //push a new child

        String imageName = GenerateString.randomAlphaNumeric(6); //generate random image name
        StorageReference myQrImage = mQrBitmapImage.child(imageName + ".jpg"); //ref to child image to be pushed

        String qrKey = qrChild.getKey(); //get the key of newly added child
        Bitmap bitmap = generateQRCode(qrKey,200,200); //generate barcode

        //push the bitmap image into the firebase storage
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = myQrImage.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //failed
                Log.d("PAY","onFailure: uploadTask");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                int status = (journeyPos == 0)? 1:2; // 1 for single, 2 for return

                QrDetails qrDetails = new QrDetails(userID,tollKey,downloadUrl.toString(),status,amount);
                qrChild.setValue(qrDetails); //push the new qrcode to database

                //most recent qr code is always one value and no multiple values
                RecentQrCode recentQrCode = new RecentQrCode(downloadUrl.toString());
                mMostRecentQR.setValue(recentQrCode); //set the most recent qrcode for current user
            }
        });

        //unset the TollDetails for payments
        clearTollConstants();
        try {
            FragmentManager manager = getSupportFragmentManager();

//            Bundle bundle = new Bundle();
//            bundle.putByteArray("myQrImage",data);
//
//            QRFragment qrFragment = new QRFragment();
//            qrFragment.setArguments(bundle);

            manager.beginTransaction().replace(R.id.my_frame_id, new QRFragment()).commitAllowingStateLoss();
        }
        catch (Exception e) {
            Log.e("PAY",e.getMessage());
            Toast.makeText(this, "Redirection failed", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onPaymentError(int i, String s) {
        Log.d("PAY","Failure main activity");
        clearTollConstants();
    }

    public void clearTollConstants() {
        TollConstants.TOLL_NAME = null;
        TollConstants.TOLL_KEY = null;
        TollConstants.TOLL_VEHICLE = -1;
        TollConstants.TOLL_JOURNEY = -1;
        TollConstants.TOLL_COST = -1;
    }

    public Bitmap generateQRCode(String text, int width, int height) {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE,width,height);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }
}
