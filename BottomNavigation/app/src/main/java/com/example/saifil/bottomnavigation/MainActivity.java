package com.example.saifil.bottomnavigation;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth; //reference the auth
    private FirebaseAuth.AuthStateListener mAuthStateListener; //listener for any auth state change

    private final static int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();

        myAuthentication(); //handles auth
        myBottomNavigation(); //handles bottom nav
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) { //attempted to signed in
            if (resultCode == RESULT_OK) { //sign in successful

                Toast.makeText(this, "Signed in", Toast.LENGTH_SHORT).show();
            }
            else if (resultCode == RESULT_CANCELED) { //sign in failed

                Toast.makeText(this, "Sign in cancelled", Toast.LENGTH_SHORT).show();
                finish(); //end the activity(before it reaches the onResume)
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        //attaches the listener when the app is resumed
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mAuthStateListener != null) {
            //detaches the listener when app is paused
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    public void myAuthentication() {
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
                                    .setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.EmailBuilder().build(),
                                            //new AuthUI.IdpConfig.PhoneBuilder().setDefaultCountryIso("in").build(),
                                            new AuthUI.IdpConfig.GoogleBuilder().build()))
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };
    }

    public void myBottomNavigation() {

        //sets home page as the default fragment on creating
        final FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.my_frame_id, new HomeFragment()).commit();


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        //disables the shift animation
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {

                            case R.id.action_home:
                                manager.beginTransaction().replace(R.id.my_frame_id, new HomeFragment()).commit();
                                break;
                            case R.id.action_explore:
                                break;
                            case R.id.action_chat:
                                break;
                            case R.id.action_account:
                                Bundle bundle = new Bundle();
                                bundle.putString("key","Value");
                                AccountFragment acc = new AccountFragment();
                                acc.setArguments(bundle);
                                manager.beginTransaction().replace(R.id.my_frame_id, acc).commit();
                                break;
                            default:
                                break;
                        }
                        return true;
                    }
                }
        );
    }
}
