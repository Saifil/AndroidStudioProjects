package com.example.saifil.tollplaza;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mFireBaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    public static final int RC_SIGN_IN = 1;
    public static int INITIAL_BALANCE = 1000;

    private String mUsername;
    private static final String ANONYMOUS = "";
    public String userID;

    private FirebaseDatabase mFirebaseDatabase; //entry point to db
    private DatabaseReference mWalletReference; //references specific part of the db
    private DatabaseReference mUserWallet; //ref to usrr wallet
    private DatabaseReference mActive;
    private DatabaseReference mUsercarInfo;


    private ImageView myImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFireBaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        mActive = mFirebaseDatabase.getReference().child("active");
        mUsercarInfo = mFirebaseDatabase.getReference().child("profile");

        mWalletReference = mFirebaseDatabase.getReference().child("wallet");
        mWalletReference.child(mFireBaseAuth.getUid()).setValue(INITIAL_BALANCE);
        if (mFireBaseAuth.getCurrentUser() != null) {
            /*Intent intent = getIntent();
            if (intent != null) {
                mWalletReference.child(mFireBaseAuth.getUid()).setValue(intent.getParcelableArrayExtra("Balance"));

            }else{
                mWalletReference.child(mFireBaseAuth.getUid()).setValue(INITIAL_BALANCE);

            }
            userID = mFireBaseAuth.getUid().toString();
            */
            //mUserWallet = mFirebaseDatabase.getReference().child("wallet/" + userID);
            Intent intent = getIntent();
            if (intent != null) {
                INITIAL_BALANCE = 1000;
            }
        }
        mWalletReference.child(mFireBaseAuth.getUid()).setValue(INITIAL_BALANCE);

        myImage = findViewById(R.id.myQRimage);

        //check for bitmap
        Intent intent = getIntent();
        if (intent != null) {
            Bitmap bitmap = (Bitmap) intent.getParcelableExtra("BitmapImage");
            myImage.setImageBitmap(bitmap);
        }

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //user is signed in
                    onSignedInInitialize(user.getDisplayName()); //pass username here
                }
                else {
                    //user is signed out
                    onSignedOutCleanUp();
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

    public void onSignOut(View view) {
        AuthUI.getInstance().signOut(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) { //attempted to signed in
            if (resultCode == RESULT_OK) { //sign in successful

                //Toast.makeText(this, "Signed in", Toast.LENGTH_SHORT).show();
            }
            else if (resultCode == RESULT_CANCELED) { //sign in failed

                Toast.makeText(this, "Sign in cancelled", Toast.LENGTH_SHORT).show();
                finish(); //end the activity(before it reaches the onResume)
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAuthStateListener != null) {
            mFireBaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFireBaseAuth.addAuthStateListener(mAuthStateListener);
    }

    private void onSignedInInitialize(String username) {
        mUsername = username;
    }

    private void onSignedOutCleanUp() {
        //detach stuff here
        mUsername = ANONYMOUS;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_profile) {
            //start profile activity
            Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickPayments(View view) {

        Intent intent = new Intent(MainActivity.this,PaymentActivity.class);
        startActivity(intent);
    }
}
