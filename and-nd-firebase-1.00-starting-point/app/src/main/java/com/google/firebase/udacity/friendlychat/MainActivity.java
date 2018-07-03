/**
 * Copyright Google Inc. All Rights Reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.firebase.udacity.friendlychat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    public static final String ANONYMOUS = "anonymous";
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 1000;
    private  static final int RC_SIGN_IN = 1;
    private static final int RC_PHOTO_PICKER = 2;
    private static final String FRIENDLY_MSG_LENGTH_KEY = "friendly_msg_length";


    private ListView mMessageListView;
    private MessageAdapter mMessageAdapter;
    private ProgressBar mProgressBar;
    private ImageButton mPhotoPickerButton;
    private EditText mMessageEditText;
    private Button mSendButton;

    private String mUsername;

    private FirebaseDatabase mFirebaseDatabase; //entry point to db
    private DatabaseReference mMessagesDatabaseReference; //references specific part of the db
    private ChildEventListener mChildEventListener; //Lisener for any changes on child
    private FirebaseAuth mFirebaseAuth; //reference the auth
    private FirebaseAuth.AuthStateListener mAuthStateListener; //listener for any auth state change
    private FirebaseStorage mFirebaseStorage; //reference to firebase storage
    private StorageReference mChatPhotosStorageReference; //ref. to storage ref obj
    private FirebaseRemoteConfig mFirebaseRemoteConfig; //ref. to remote config.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get reference to the db
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        //get reference to auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        //get reference to firebase storage
        mFirebaseStorage = FirebaseStorage.getInstance();

        //get reference to the messages part of the db
        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("messages");
        //get reference to the remote config.
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        //get reference to the chat_photos file in storage
        mChatPhotosStorageReference = mFirebaseStorage.getReference().child("chat_photos");

        mUsername = ANONYMOUS;

        // Initialize references to views
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mMessageListView = (ListView) findViewById(R.id.messageListView);
        mPhotoPickerButton = (ImageButton) findViewById(R.id.photoPickerButton);
        mMessageEditText = (EditText) findViewById(R.id.messageEditText);
        mSendButton = (Button) findViewById(R.id.sendButton);

        // Initialize message ListView and its adapter
        List<FriendlyMessage> friendlyMessages = new ArrayList<>();
        mMessageAdapter = new MessageAdapter(this, R.layout.item_message, friendlyMessages);
        mMessageListView.setAdapter(mMessageAdapter);

        // Initialize progress bar
        mProgressBar.setVisibility(ProgressBar.INVISIBLE);

        // ImagePickerButton shows an image picker to upload a image for a message
        mPhotoPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Fire an intent to show an image picker
            }
        });

        // Enable Send button when there's text to send
        mMessageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    mSendButton.setEnabled(true);
                } else {
                    mSendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        mMessageEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});

        // Send button sends a message and clears the EditText
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FriendlyMessage friendlyMessage = new FriendlyMessage(mMessageEditText.getText().toString(), mUsername, null);
                mMessagesDatabaseReference.push().setValue(friendlyMessage);

                // Clear input box
                mMessageEditText.setText("");
            }
        });

        mAuthStateListener = new FirebaseAuth.AuthStateListener() { //listener for any changes in auth state
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                //create a user which points to the current user
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) { //user is signed in

                    onSignedInInitialize(firebaseUser.getDisplayName()); //pass username as param
                }
                else { //user is not signed in

                    onSignedOutCleanUp();

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

        // ImagePickerButton shows an image picker to upload a image for a message
        mPhotoPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
            }
        });

        //Firebase remote config
        FirebaseRemoteConfigSettings remoteConfigSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        mFirebaseRemoteConfig.setConfigSettings(remoteConfigSettings); //set the config. settings

        //store the key-value pair in the map (config. variable set in the console)
        Map<String,Object> defaultConfigMap = new HashMap<>();
        defaultConfigMap.put(FRIENDLY_MSG_LENGTH_KEY,DEFAULT_MSG_LENGTH_LIMIT);
        mFirebaseRemoteConfig.setDefaults(defaultConfigMap);
        fetchConfig(); //sets the changed param from the remote config.

    }

    //called before onResume(i.e. where the user is redirected to sign in
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //handles the back button press to exit the app
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
        else if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK) { //if photo was picked

            Uri selectedImageUri = data.getData();
            //create a child of the chat_photos folder with the name having lastPath of the selected Uri
            StorageReference photoRef =
                    mChatPhotosStorageReference.child(selectedImageUri.getLastPathSegment());

            //store the file into db storage
            photoRef.putFile(selectedImageUri).addOnSuccessListener(
                    this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            //get the download Uri for the chat display listAdapter
                            Uri downloadUri = taskSnapshot.getDownloadUrl();

                            //send the uri to the adapter to be displayed
                            FriendlyMessage friendlyMessage =
                                    new FriendlyMessage(null,mUsername,downloadUri.toString());
                            mMessagesDatabaseReference.push().setValue(friendlyMessage);
                        }
                    }
            );
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                //sign out
                AuthUI.getInstance().signOut(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
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
        detachDatabaseReadListener();
        mMessageAdapter.clear();
    }

    private void onSignedInInitialize(String username) {

        mUsername = username;

        //display the messages by calling the method
        attactDatabaseReadListener();
    }

    private void onSignedOutCleanUp() {

        mUsername = ANONYMOUS; //unset the username
        mMessageAdapter.clear(); //clear the message list
        detachDatabaseReadListener(); //detach the messages db
    }

    private void attactDatabaseReadListener() {

        if (mChildEventListener == null) {

            //Listen to any changes made to the db (keeping it realtime)
            mChildEventListener = new ChildEventListener() { //triggered once when the data is added
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    //add the data to the adapter (to display it) whenever new message is sent
                    FriendlyMessage friendlyMessage = dataSnapshot.getValue(FriendlyMessage.class); //get value from db
                    mMessageAdapter.add(friendlyMessage); //sent to adapter

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {}

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

                @Override
                public void onCancelled(DatabaseError databaseError) {}
            };
            //add the listener to the messages child of the db
            mMessagesDatabaseReference.addChildEventListener(mChildEventListener);
        }
    }

    private void detachDatabaseReadListener() {

        if (mChildEventListener != null) { //ensures that only attach/detach works at one point
            mMessagesDatabaseReference.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }

    }

    public void fetchConfig() {

        long cashExpiration = 3600; //set expiration time to an hour

        if (mFirebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) { //if dev mode is enabled
            cashExpiration = 0;
        }
        mFirebaseRemoteConfig.fetch(cashExpiration)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) { //if succeeded in getting the value from remote config.

                        mFirebaseRemoteConfig.activateFetched();
                        applyRetrievedLengthLimit(); //make the changes (call function)
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) { //happens when the user is offline

                        Log.w(TAG,"Error fetching config",e);
                        applyRetrievedLengthLimit();
                    }
                });

    }

    private void applyRetrievedLengthLimit() {

        //get the value from the config. settings
        long friendly_msg_length = mFirebaseRemoteConfig.getLong(FRIENDLY_MSG_LENGTH_KEY);
        //apply the message length filter to input message
        mMessageEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(((int) friendly_msg_length))});
        //raise a log giving the current message length value
        Log.d(TAG, FRIENDLY_MSG_LENGTH_KEY + " = " + friendly_msg_length);
    }
}
