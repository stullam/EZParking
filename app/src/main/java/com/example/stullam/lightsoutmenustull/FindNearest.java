package com.example.stullam.lightsoutmenustull;

import android.Manifest;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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

import java.util.ArrayList;
import java.util.Arrays;

public class FindNearest extends FragmentActivity
{

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private GoogleApiClient mGoogleApiClient;
    public static final String TAG = MapsActivity.class.getSimpleName();
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private LocationRequest mLocationRequest;

    private EZParkingDBHelper dbHelper;
    private SQLiteDatabase db;

//    private ParkingSpot Parking1 = new ParkingSpot("Parking1", 39.4698, -87.3898, 1);
//    private ParkingSpot Parking2 = new ParkingSpot("Parking2", 39.4700, -87.3898, 2);
//    private ParkingSpot Parking3 = new ParkingSpot("Parking3", 39.4702, -87.3898, 3);
//    private ParkingSpot Parking4 = new ParkingSpot("Parking4", 39.4704, -87.3898, 4);
//    private ParkingSpot Parking5 = new ParkingSpot("Parking5", 39.4706, -87.3898, 5);
//    private ParkingSpot Parking6 = new ParkingSpot("Parking6", 39.4708, -87.3898, 6);
//    private ParkingSpot Parking7 = new ParkingSpot("Parking7", 39.4710, -87.3898, 7);
//    private ParkingSpot Parking8 = new ParkingSpot("Parking8", 39.4712, -87.3898, 8);
//    private ParkingSpot Parking9 = new ParkingSpot("Parking9", 39.4714, -87.3898, 9);
//    private ParkingSpot Parking10 = new ParkingSpot("Parking10", 39.4716, -87.3898, 10);

    private double[] ImportantSpotArray = new double[4];
    // 0 and 1 are the target lats and long
    // 2 and 3 are the current lats and longs
    private double[] ImportantSpotInfo = new double[4];
    private double[][] SortedListByDistances = new double[10][2];
    private double[] CurrentSpot = new double[2];
    private double[] NearestSpot = new double[2];

    public ArrayList<ParkingSpot> parkSpots = new ArrayList<ParkingSpot>();
    public ArrayList<ParkingSpot> distanceSortedSpots = new ArrayList<ParkingSpot>();

    public double targetLat = 0;
    public double targetLong = 0;
    public double currentLat = 0;
    public double currentLong = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();

//        parkSpots.add(Parking1);
//        parkSpots.add(Parking2);
//        parkSpots.add(Parking3);
//        parkSpots.add(Parking4);
//        parkSpots.add(Parking5);
//        parkSpots.add(Parking6);
//        parkSpots.add(Parking7);
//        parkSpots.add(Parking8);
//        parkSpots.add(Parking9);
//        parkSpots.add(Parking10);

        //ImportantSpotInfo = (double[]) this.getIntent().getDoubleArrayExtra(LightsOutMenu.KEY_TARGETCURRENT);
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(permission == PackageManager.PERMISSION_GRANTED) {
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new android.location.LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
            Location currentLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (currentLocation != null) {
                CurrentSpot[0] = currentLocation.getLatitude();
                CurrentSpot[1] = currentLocation.getLongitude();
            }
        }
        dbHelper = EZParkingDBHelper.getInstance(this.getApplicationContext());
        db = dbHelper.getReadableDatabase();
//        double crowDist = Math.sqrt(((targetLat-tempLat) * (targetLat-tempLat)) + ((targetLong - tempLong) * (targetLong-tempLong)));
        String[] projection = {EZParkingContract.EZParking.PARKING_COLUMN_LATITUDE_NAME, EZParkingContract.EZParking.PARKING_COLUMN_LONGITUDE_NAME};
        Cursor c = db.query(EZParkingContract.EZParking.PARKING_TABLE_NAME,
                            projection,
                            null,
                            null,
                             null,
                            null,
                            null);
        ArrayList<LatLng> positions = new ArrayList<LatLng>();

        if(c != null && c.getCount() > 0) {
            c.moveToFirst();
            while(!c.isLast()) {
                int latIndex = c.getColumnIndex(EZParkingContract.EZParking.PARKING_COLUMN_LATITUDE_NAME);
                int longIndex = c.getColumnIndex(EZParkingContract.EZParking.PARKING_COLUMN_LONGITUDE_NAME);
                double latitude = c.getDouble(latIndex);
                double longitude = c.getDouble(longIndex);
                positions.add(new LatLng(latitude, longitude));
                c.moveToNext();
            }
        }
        LatLng nearestSpot = sortSpotByDistance(positions, CurrentSpot);
        System.out.println(nearestSpot == null);
        System.out.println("Latitude: " + Double.toString(nearestSpot.latitude) + " Longitude: " + Double.toString(nearestSpot.longitude));
        mMap.addMarker(new MarkerOptions().position(nearestSpot).title("Nearest Spot"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(nearestSpot));
//        sortSpotByDistance(parkSpots, ImportantSpotInfo);
//        if(ImportantSpotInfo.length != 0) {
//            System.out.println("distance sorted: " + distanceSortedSpots.get(0).getLattitude());
//            mMap.addMarker(new MarkerOptions().position(new LatLng(distanceSortedSpots.get(0).getLattitude(), distanceSortedSpots.get(0).getLongitude())).title("The Nearest Spot Is Here"));
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(distanceSortedSpots.get(0).getLattitude(), distanceSortedSpots.get(0).getLongitude())));
//        }



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
        //double[] locationData = this.getIntent().getDoubleArrayExtra(LightsOutMenu.KEY_TARGETCURRENT);
        //mMap.addMarker(new MarkerOptions().position(new LatLng(locationData[0], locationData[1])).title("Current Location"));
        //mMap.addMarker(new MarkerOptions().position(new LatLng(locationData[2], locationData[3])).title("Target location"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(locationData[2], locationData[3])));
        //System.out.println("0 and 1 " + locationData[0] + ", " + locationData[1]);
        //System.out.println("2 and 3 " + locationData[2] + ", " + locationData[3]);

    }

    private LatLng sortSpotByDistance(ArrayList<LatLng> possibleSpots, double[] targetSpot) {
        double targetLat = targetSpot[0];
        double targetLong = targetSpot[1];
        LatLng currentMin = null;
        double minDistance = 99999999;
        double[] locDistHolder = new double[possibleSpots.size()];
        for(int i = 0; i< possibleSpots.size(); i++) {
            double tempLat = possibleSpots.get(i).latitude;
            double tempLong = possibleSpots.get(i).longitude;
            System.out.println((targetLat - tempLat) * (targetLat - tempLat) + ((targetLong - tempLong) * (targetLong-tempLong)));
            double crowDist = Math.sqrt(((targetLat-tempLat) * (targetLat-tempLat)) + ((targetLong - tempLong) * (targetLong-tempLong)));
            System.out.println("crowDist = " + Double.toString(crowDist) + " minDistance = " + Double.toString(minDistance));
            if(crowDist < minDistance) {
                minDistance = crowDist;
                currentMin = possibleSpots.get(i);
            }
        }
        return currentMin;
    }
}