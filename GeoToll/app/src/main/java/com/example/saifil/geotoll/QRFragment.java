package com.example.saifil.geotoll;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class QRFragment extends Fragment {

    private ImageView imageView;
    private ProgressBar mProgressBar;

    private FirebaseStorage mFirebaseStorage;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mMostRecentQrcode;

    private String uID;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.qr_fragment,container,false);

        imageView = view.findViewById(R.id.qrImage_id);
        mProgressBar = view.findViewById(R.id.indeterminateBar_qr);

//        Bundle bundle = this.getArguments();
//        if (bundle != null) {
//            byte[] byteArray = bundle.getByteArray("myQrImage");
//            if (byteArray != null) {
//                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
//                imageView.setImageBitmap(bitmap); //set the image
//            }
//            else {
//                Toast.makeText(getActivity(), "Image is not present", Toast.LENGTH_SHORT).show();
//            }
//        }
//        else {
//            Log.d("PAY","bundle is null");
//        }

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseStorage = FirebaseStorage.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();

        if (mFirebaseAuth != null) {
            if (mFirebaseAuth.getCurrentUser() != null) {
                uID = mFirebaseAuth.getCurrentUser().getUid();
                mMostRecentQrcode = mFirebaseDatabase.getReference().child("MyQRCode/mostRecent/" + uID);
                //listen to a particular value in database
                mMostRecentQrcode.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        RecentQrCode recentQrCode = dataSnapshot.getValue(RecentQrCode.class);
                        if (recentQrCode != null) {
                            String url = recentQrCode.recentPhotoUrl;
                            //Toast.makeText(getActivity(), url, Toast.LENGTH_LONG).show();
                            //setImageView(url); //set the image view
                            try {
                                mProgressBar.setVisibility(View.VISIBLE); //set progressbar

                                //use glide to set the image
                                if (url != null) {
                                    Glide.with(getActivity()).load(url).into(imageView);
                                }
                                else {
                                    Log.d("meme","url is null");
                                }

                                mProgressBar.setVisibility(View.GONE); //unset progressbar
                            } catch (Exception e) {
                                Log.d("meme","failed to load the qr code");
                                Toast.makeText(getActivity(), "failed to load the qr code", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }
        else {
            Log.d("meme", "mFirebaseAuth is null");
        }
        return view;
    }

    private void setImageView(String url) {
//        try {
//            mProgressBar.setVisibility(View.VISIBLE); //set progressbar
//
//            //use glide to set the image
//            if (url != null) {
//                Glide.with(getActivity()).load(url).into(imageView);
//            }
//            else {
//                Log.d("meme","url is null");
//            }
//
//            mProgressBar.setVisibility(View.GONE); //unset progressbar
//        } catch (Exception e) {
//            Log.d("meme","failed to load the qr code");
//            Toast.makeText(getActivity(), "failed to load the qr code", Toast.LENGTH_SHORT).show();
//        }
    }
}
