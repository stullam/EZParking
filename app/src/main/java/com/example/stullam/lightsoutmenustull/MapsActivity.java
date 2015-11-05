package com.example.stullam.lightsoutmenustull;

import android.content.IntentSender;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity
        //implements
//        GoogleApiClient.ConnectionCallbacks,
//        GoogleApiClient.OnConnectionFailedListener,
//        LocationListener
{

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private GoogleApiClient mGoogleApiClient;
    public static final String TAG = MapsActivity.class.getSimpleName();
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private LocationRequest mLocationRequest;

    private double[] ImportantSpotArray = new double[4];

    public double targetLat = 0;
    public double targetLong = 0;
    public double currentLat = 0;
    public double currentLong = 0;

    private EZParkingDBHelper dbHelper;// = EZParkingDBHelper.getInstance(this.getApplicationContext());
    SQLiteDatabase db;// = dbHelper.getReadableDatabase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        dbHelper = EZParkingDBHelper.getInstance(this.getApplicationContext());
        db =  dbHelper.getReadableDatabase();
        setUpMapIfNeeded();

//        ImportantSpotArray = (double[]) this.getIntent().getDoubleArrayExtra(ListView.KEY_CURRENTSPOT);
//        targetLat = ImportantSpotArray[0];
//        targetLong = ImportantSpotArray[1];
//        currentLat = ImportantSpotArray[2];
//        currentLong = ImportantSpotArray[3];
//        Log.d("LOM", "list of spots 0 = " + ImportantSpotArray[0]);
//        Log.d("LOM", "list of spots 1 = " + ImportantSpotArray[1]);
//        Log.d("LOM", "list of spots 2 = " + ImportantSpotArray[1]);
//        Log.d("LOM", "list of spots 3 = " + ImportantSpotArray[2]);


//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(LocationServices.API)
//                .build();
//        mLocationRequest = LocationRequest.create()
//                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
//                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
//                .setFastestInterval(1 * 1000); // 1 second, in milliseconds
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        //mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        if (mGoogleApiClient.isConnected()) {
//            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
//            mGoogleApiClient.disconnect();
//        }
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        //Log.d("LOM", "ImportantSpotArray[0] = " + ImportantSpotArray[0]);
        //Log.d("LOM", "ImportantSpotArray[1] = " + ImportantSpotArray[1]);

        String[] projection = {EZParkingContract.EZParking.PARKING_COLUMN_LATITUDE_NAME, EZParkingContract.EZParking.PARKING_COLUMN_LONGITUDE_NAME};
        Cursor c = db.query(EZParkingContract.EZParking.PARKING_TABLE_NAME,
                            projection,
                            null,
                            null,
                            null,
                            null,
                            null);
        if(c != null && c.getCount() > 0) {
            c.moveToFirst();
            while(!c.isLast()) {
                int latitudeIndex = c.getColumnIndex(EZParkingContract.EZParking.PARKING_COLUMN_LATITUDE_NAME);
                int longitudeIndex = c.getColumnIndex(EZParkingContract.EZParking.PARKING_COLUMN_LONGITUDE_NAME);
                double latitude = c.getDouble(latitudeIndex);
                double longitude = c.getDouble(longitudeIndex);
                mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("Parking spot"));
                c.moveToNext();
            }
        }

        ImportantSpotArray = (double[]) this.getIntent().getDoubleArrayExtra(ListView.KEY_CURRENTSPOT);
        if(ImportantSpotArray != null) {
            targetLat = ImportantSpotArray[0];
            targetLong = ImportantSpotArray[1];
            currentLat = ImportantSpotArray[2];
            currentLong = ImportantSpotArray[3];
            System.out.println("0 and 1" + ImportantSpotArray[0] + ", " + ImportantSpotArray[1]);
            System.out.println("2 and 3" + ImportantSpotArray[2] + ", " + ImportantSpotArray[3]);
        }

        double[] locationData = this.getIntent().getDoubleArrayExtra(LightsOutMenu.KEY_LOCATIONARRAY);
        //ImportantSpotArray = this.getIntent().getDoubleArrayExtra(LightsOutMenu.KEY_LISTOFSPOTS);

//        Log.d("LOM", "loc0 = " + locationData[0]);
//        Log.d("LOM", "loc1 = " + locationData[1]);
//        Log.d("LOM", "loc2 = " + locationData[2]);
//        Log.d("LOM", "loc3 = " + locationData[3]);


//        if(ImportantSpotArray != null) {
//            mMap.addMarker(new MarkerOptions().position(new LatLng(targetLat, targetLong)).title("Target location"));
//            mMap.addMarker(new MarkerOptions().position(new LatLng(currentLat, currentLong)).title("You are here"));
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(targetLat, targetLong)));
//        }
//        if(locationData !=null) {
//            mMap.addMarker(new MarkerOptions().position(new LatLng(locationData[0], locationData[1])).title("Current Location"));
//            mMap.addMarker(new MarkerOptions().position(new LatLng(locationData[2], locationData[3])).title("Target location"));
//        }
        //mMap.addMarker(new MarkerOptions().position(new LatLng(currentLat, currentLong)).title("You are here"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(targetLat, targetLong)));



    }

//    @Override
//    public void onConnected(Bundle bundle) {
//        Log.i(TAG, "Location services connected.");
//        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//        if (location == null) {
//            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
//        }
//        else {
//            handleNewLocation(location);
//        }
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//        Log.i(TAG, "Location services suspended. Please reconnect.");
//    }
//
//    @Override
//    public void onConnectionFailed(ConnectionResult connectionResult) {
//        if (connectionResult.hasResolution()) {
//            try {
//                // Start an Activity that tries to resolve the error
//                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
//            } catch (IntentSender.SendIntentException e) {
//                e.printStackTrace();
//            }
//        } else {
//            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
//        }
//    }
//
//    private void handleNewLocation(Location location) {
//        Log.d(TAG, location.toString());
//        double currentLatitude = location.getLatitude();
//        double currentLongitude = location.getLongitude();
//        LatLng latLng = new LatLng(currentLatitude, currentLongitude);
//        MarkerOptions options = new MarkerOptions()
//                .position(latLng)
//                .title("I am here!");
//        mMap.addMarker(options);
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//    }
//
//    @Override
//    public void onLocationChanged(Location location) {
//        handleNewLocation(location);
//    }
    //https:maps.googleapis.com/maps/api/directions/json?origin=Toronto&destination=Montreal&key=YOUR_API_KEY

}
