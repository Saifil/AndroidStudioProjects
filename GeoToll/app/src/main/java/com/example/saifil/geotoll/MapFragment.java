package com.example.saifil.geotoll;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    MapView mMapView;
    private GoogleMap googleMap;

    private static final int REQUEST_LOCATION_PERMISSIONS = 1;
    private static final String TAG = "meme";

    private static int UPDATE_INTERVAL = 5000; //5 secs
    private static int FASTEST_INTERVAL = 3000;
    private static int DISPLACEMENT = 10;

    private static String userID = null;

    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private FusedLocationProviderClient mFusedLocationClient;

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;
    DatabaseReference mTollDetails;
    FirebaseUser mCurrentUser;
    FirebaseAuth mFirebaseAuth;
    GeoFire mGeoFire;
    Marker myCurrentLocation;

    //build a notification
    NotificationCompat.Builder notification;
    private static final int uniqueID = 2002; //assign unique ID to the notification
    private static final String channelId = "my_channel_id"; //set a channel ID
    private static final String NOTIFICATION_CHANNEL_NAME = "myChannel"; //channel name

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_fragment, container, false);

        mMapView = view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        Log.d(TAG, "onCreateView");

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("MyLocation");
        mTollDetails = mFirebaseDatabase.getReference().child("MyToll/details");
        mGeoFire = new GeoFire(mDatabaseReference);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        setUpLocation(); //set up the user location

        //notification manager
        notification = new NotificationCompat.Builder(getActivity(), channelId);
        notification.setAutoCancel(true);

        //Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        if (mFirebaseAuth != null) {
            mCurrentUser = mFirebaseAuth.getCurrentUser();
            if (mCurrentUser != null) {
                userID = mCurrentUser.getUid();
            }
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                Log.d(TAG, "onMapReady");

                if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                googleMap.setMyLocationEnabled(true);

                //For showing a move to my location button
                if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    //if user doesn't grant the permission
                    checkLocationPermission();
                } else {
                    Log.d("HEHE","onMapReady");
                    //add a circle (geo area)
//                    LatLng myToll = new LatLng(19.144964,72.841667);
//                    googleMap.addCircle(new CircleOptions()
//                            .center(myToll)
//                            .radius(500) //in meters
//                            .strokeColor(Color.BLUE)
//                            .fillColor(0x220000FF)
//                            .strokeWidth(5.0f)
//                    );
                    //LatLng myToll = tollLocation(19.110764,72.8726742);
                    //LatLng myToll1 = tollLocation(19.118851,72.847243);

                    //Add GeoQuery
                    //0.5f = 0.5 km = 500 m
//                    GeoQuery geoQuery = tollGeoQuery(myToll.latitude,myToll.longitude,0.5f);
//                    GeoQuery geoQuery1 = tollGeoQuery(myToll1.latitude,myToll1.longitude,0.5f);

                    //GeoQuery geoQuery = mGeoFire.queryAtLocation(new GeoLocation(myToll.latitude,myToll.longitude),0.5f);
//                    geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
//                        @Override
//                        public void onKeyEntered(String key, GeoLocation location) {
//                            sendNotification("GeoToll",String.format("%s entered the toll area",key),"");
//                        }
//
//                        @Override
//                        public void onKeyExited(String key) {
//                            sendNotification("GeoToll",String.format("%s exited the toll area",key),"");
//                        }
//
//                        @Override
//                        public void onKeyMoved(String key, GeoLocation location) {
//                            Log.d(TAG,String.format("%s moved within the toll area %f / %f",key,location.latitude,location.longitude));
//                        }
//
//                        @Override
//                        public void onGeoQueryReady() {
//
//                        }
//
//                        @Override
//                        public void onGeoQueryError(DatabaseError error) {
//                            Log.e(TAG,"" + error);
//                        }
//                    });
//

                    //try multiple geoQuery here
                    final List<LatLng> myLatLng = new ArrayList<>();
                    final List<GeoQuery> myGeoQuery = new ArrayList<>();

                    mTollDetails.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            int i = 0;
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                //final String myKey = snapshot.getKey();
                                TollDetail details = snapshot.getValue(TollDetail.class);
                                if (details != null) {
                                    double lat = details.lat;
                                    double lng = details.lng;
                                    final String myKey = snapshot.getKey();
                                    String latlng = lat + " " + lng;
                                    Log.d("HEHE",latlng);
                                    final String name = details.name;

                                    myLatLng.add(tollLocation(lat,lng));
                                    myGeoQuery.add(tollGeoQuery(myLatLng.get(i).latitude,myLatLng.get(i).longitude,0.5f));

                                    myGeoQuery.get(i).addGeoQueryEventListener(new GeoQueryEventListener() {
                                        @Override
                                        public void onKeyEntered(String key, GeoLocation location) {
                                            sendNotification("GeoToll Entry",String.format("%s",name),myKey);
                                            Log.d("HEHE","Was called for: " + name);
                                        }

                                        @Override
                                        public void onKeyExited(String key) {
                                            //sendNotification("GeoToll Exit",String.format("%s",name),myKey);
                                        }

                                        @Override
                                        public void onKeyMoved(String key, GeoLocation location) {
                                            Log.d(TAG,String.format("%s moved within the toll area %f / %f",key,location.latitude,location.longitude));
                                        }

                                        @Override
                                        public void onGeoQueryReady() {

                                        }

                                        @Override
                                        public void onGeoQueryError(DatabaseError error) {
                                            Log.e(TAG,"" + error);
                                        }
                                    });
                                    i++;
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        return view;
    }

    private GeoQuery tollGeoQuery(double latitude, double longitude, float v) {
        return mGeoFire.queryAtLocation(new GeoLocation(latitude,longitude),v);
    }

    private LatLng tollLocation(double latitude, double longitude) {
        LatLng myToll = new LatLng(latitude,longitude);
        googleMap.addCircle(new CircleOptions()
                .center(myToll)
                .radius(500) //in meters
                .strokeColor(Color.BLUE)
                .fillColor(0x220000FF)
                .strokeWidth(5.0f)
        );
        return myToll;
    }

    private void sendNotification(String geoToll, String format, String myKey) {
        //Toast.makeText(getActivity(), "Notification will be sent. ", Toast.LENGTH_SHORT).show();
        Log.d(TAG,"sendNotification :" + format);

        notification.setSmallIcon(R.drawable.carone);
        notification.setTicker("Check myToll");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle(geoToll);
        notification.setContentText(format);

        //direct the user back to the activity on clicking the notification
        Intent intent = new Intent(getActivity(),MainActivity.class);
        intent.putExtra("TollKey",myKey);
        intent.putExtra("TollName",format);

        //create a pending intent
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent); //set the notification content

        //manages the notifications, get notification service into it
        NotificationManager nm = (NotificationManager)getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        //if the version is greater than or equal to Oreo then we set the notification Channel
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) { //check SDK version
            Log.d(TAG,"was inside if cond");
            //set up the notification attributes
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel notificationChannel = new NotificationChannel(channelId, NOTIFICATION_CHANNEL_NAME, importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            if (nm != null) {
                Log.d(TAG,"was here in 'nm != null'");
                nm.createNotificationChannel(notificationChannel); //create a notification channel
            }
        }

        if (nm != null) {
            nm.notify(uniqueID, notification.build()); //notify the user
        }
    }


    private void setUpLocation() {
        Log.d(TAG,"setUpLocation");
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //if user doesn't grant the permission
            checkLocationPermission();
        } else {
            buildGoogleApiClient();
        }
    }

    public void checkLocationPermission() {
        Log.d(TAG,"checkLocationPermission");
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //if user doesn't grant the permission
            //ask for permission which is handled in onRequestPermissionsResult
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_LOCATION_PERMISSIONS);
        }
    }

    LocationCallback mLocationCallback = new LocationCallback(){
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Log.d(TAG,"onLocationResult");
            for (Location location : locationResult.getLocations()) {
                //do stuff when the location is updated
                final double lat = location.getLatitude();
                final double lng = location.getLongitude();

                Log.d("HEHE","myLocation is: " + lat + " " + lng);

                //Update to Firebase
                mGeoFire.setLocation(userID, new GeoLocation(lat, lng),
                        new GeoFire.CompletionListener() {
                            @Override
                            public void onComplete(String key, DatabaseError error) {
                                //add marker
                                if (myCurrentLocation != null) { //remove old marker
                                    myCurrentLocation.remove();
                                }
                                myCurrentLocation = googleMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(lat,lng))
                                        .title("You"));

                                //focus camera
                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lng),12.0f));
                            }
                        });

                //add a marker

                Log.d(TAG, "Location: " + lat + " " + lng);
                mLastLocation = location;
            }
        };

    };


    private void createLocationRequest() {
        Log.d(TAG,"createLocationRequest");
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //if user doesn't grant the permission
            checkLocationPermission();
        } else {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
        }
    }

    private void buildGoogleApiClient() {
        Log.d(TAG,"buildGoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.d(TAG,"onRequestPermissionsResult");
        if (requestCode == REQUEST_LOCATION_PERMISSIONS) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (mGoogleApiClient == null) {
                    buildGoogleApiClient();
                }
                if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                googleMap.setMyLocationEnabled(true);
            } else {
                // Permission was denied or request was cancelled
                Toast.makeText(getActivity(), "permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG,"onResume");
        mMapView.onResume();
        createLocationRequest();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG,"onPause");
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.d(TAG,"onLowMemory");
        mMapView.onLowMemory();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG,"onConnected");
        createLocationRequest();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG,"onConnectionSuspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG,"onConnectionFailed");
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG,"onLocationChanged");
    }
}
