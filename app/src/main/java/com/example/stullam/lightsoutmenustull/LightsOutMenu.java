package com.example.stullam.lightsoutmenustull;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.util.Log;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class LightsOutMenu extends AppCompatActivity implements View.OnClickListener {

    private Button mSettingsButton;
    private Button mSearchButton;
    private Button mFindNearestButton;
    private Button mMapViewButton;
    private Button mMatchPrefButton;
    private Button mParkedHereButton;
    private Button mListViewButton;
    private Button mDemoButton;

    private ParkingSpot Parking1 = new ParkingSpot("Parking1", 39.4698, -87.3898);
    private ParkingSpot Parking2 = new ParkingSpot("Parking2", 39.4700, -87.3898);
    private ParkingSpot Parking3 = new ParkingSpot("Parking3", 39.4702, -87.3898);
    private ParkingSpot Parking4 = new ParkingSpot("Parking4", 39.4704, -87.3898);
    private ParkingSpot Parking5 = new ParkingSpot("Parking5", 39.4706, -87.3898);
    private ParkingSpot Parking6 = new ParkingSpot("Parking6", 39.4708, -87.3898);
    private ParkingSpot Parking7 = new ParkingSpot("Parking7", 39.4710, -87.3898);
    private ParkingSpot Parking8 = new ParkingSpot("Parking8", 39.4712, -87.3898);
    private ParkingSpot Parking9 = new ParkingSpot("Parking9", 39.4714, -87.3898);
    private ParkingSpot Parking10 = new ParkingSpot("Parking10", 39.4716, -87.3898);

    public ArrayList<ParkingSpot> parkSpots = new ArrayList<ParkingSpot>();
    public double[] ImportantSpotArray = new double[4];
    public double[] LocationArray = new double[4];

    static final String KEY_SEARCH_RADIUS = "KEY_SEARCH_RADIUS";
    static final String KEY_TARGETSPOT = "KEY_TARGETSPOT";
    static final String KEY_LISTOFSPOTS = "KEY_LISTOFSPOTS";
    static final String KEY_LOCATIONARRAY = "KEY_LOCATIONARRAY";
    private String mSearchRadius = "";
    private static final int REQUEST_CODE_CHANGE_BUTTON = 1;
    private static final int REQUEST_CODE_TARGETSPOT = 1;
    private static final int REQUEST_CODE_LISTOFSPOTS = 1;
    public double currentSpotLat = 0;
    public double currentSpotLong = 0;

    public EZParkingDBHelper dbHelper;
    private SQLiteDatabase db;

    private AutoCompleteTextView autoCompleteTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lights_out_menu);

        mSettingsButton = (Button)findViewById(R.id.Settings);
        mSettingsButton.setOnClickListener(this);

        mSearchButton = (Button)findViewById(R.id.Search);
        mSearchButton.setOnClickListener(this);

        mFindNearestButton = (Button)findViewById(R.id.FindNearest);
        mFindNearestButton.setOnClickListener(this);

        mMapViewButton = (Button)findViewById(R.id.MapView);
        mMapViewButton.setOnClickListener(this);

        mMatchPrefButton = (Button)findViewById(R.id.MatchPreferences);
        mMatchPrefButton.setOnClickListener(this);

        mParkedHereButton = (Button)findViewById(R.id.IParked);
        mParkedHereButton.setOnClickListener(this);

        mListViewButton = (Button)findViewById(R.id.ListView);
        mListViewButton.setOnClickListener(this);

        String[] cities = getResources().getStringArray(R.array.list_of_cities);
        ArrayAdapter adapter =new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,cities);

        autoCompleteTv = (AutoCompleteTextView)findViewById(R.id.auto_complete_tv);
        //autoCompleteTv.getText().toString();
        autoCompleteTv.setAdapter(adapter);
        dbHelper = EZParkingDBHelper.getInstance(this.getApplicationContext());
        db = dbHelper.getWritableDatabase();
    }

    @Override
    public void onClick(View v) {
        //if(v.getId() == R.id.button)
        parkSpots.add(Parking1);
        parkSpots.add(Parking2);
        parkSpots.add(Parking3);
        parkSpots.add(Parking4);
        parkSpots.add(Parking5);
        parkSpots.add(Parking6);
        parkSpots.add(Parking7);
        parkSpots.add(Parking8);
        parkSpots.add(Parking9);
        parkSpots.add(Parking10);

        // This is where you enter the code to detemrine where the target spot is.
        // Store the 0 as the lattitude, and the 1 index as the longitude
        // 0 and 1 can be target spot lattitude and longitude
        // and 2 and 3 can be the current spot lattitude and longitude
        // or the other way around
        ImportantSpotArray[0] = 39.4696;
        ImportantSpotArray[1] = -87.3898;
        //ImportantSpotArray[2] = currentSpotLat;
       // ImportantSpotArray[3] = currentSpotLong;

        switch (v.getId()) {
            case R.id.Settings:
                Log.d("LOM", "Settings Button Clicked");
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                settingsIntent.putExtra(KEY_SEARCH_RADIUS, mSearchRadius); // Possible source of error
                startActivityForResult(settingsIntent, REQUEST_CODE_CHANGE_BUTTON);
                //startActivity(settingsIntent);
                break;
            case R.id.Search:
                Log.d("LOM", "Search Button Clicked");
                //Intent searchIntent = new Intent(this, Search.class);
                Intent SearchIntent = new Intent(this, ListView.class);
                //EditText address = (EditText) findViewById(R.id.address_enter);
                //EditText address2 = (EditText) findViewById(R.id.address_enter2);
                //searchIntent.putExtra("address", address.getText().toString());
                //searchIntent.putExtra("address2", address2.getText());

                Geocoder gc = new Geocoder(this);
                try {
                    List<Address> list = gc.getFromLocationName(autoCompleteTv.getText().toString(),1);
                    Address add = list.get(0);
                    double SearchLattitude = add.getLatitude();
                    double SearchLongitude = add.getLongitude();
                    ImportantSpotArray[0] = SearchLattitude;
                    ImportantSpotArray[1] = SearchLongitude;
                    String locality = add.getLocality();
                    System.out.println("Stuff" + ImportantSpotArray[0] + ", " + ImportantSpotArray[1]);
                    SearchIntent.putExtra(KEY_TARGETSPOT, ImportantSpotArray);
                    //Intent MapIntent = new Intent(this, MapsActivity.class);
                    //MapIntent.putExtra("Lat",add.getLatitude());
                    //MapIntent.putExtra("Lon",add.getLongitude());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //this.startActivity(searchIntent);
                this.startActivity(SearchIntent);
                break;
            case R.id.FindNearest:
                Log.d("LOM", "Search Button Clicked");
                Intent findIntent = new Intent(this, FindNearest.class);
                this.startActivity(findIntent);
                break;
            case R.id.MapView:
                Log.d("LOM", "Search Button Clicked");
                Intent mapIntent = new Intent(this, MapsActivity.class);
                //Intent mapIntent = new Intent(this, MapView.class);
                mapIntent.putExtra(KEY_LOCATIONARRAY, LocationArray);
                this.startActivity(mapIntent);
                break;
            case R.id.MatchPreferences:
                Log.d("LOM", "Search Button Clicked");
                Intent matchIntent = new Intent(this, MatchPreferences.class);
                this.startActivity(matchIntent);
                break;
            case R.id.IParked:
                Log.d("LOM", "Park Button Clicked");
                LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
                if(permission == PackageManager.PERMISSION_GRANTED) {
                    manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
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
                        double latitude = currentLocation.getLatitude();
                        double longitude = currentLocation.getLongitude();
                        ImportantSpotArray[2] = latitude;
                        ImportantSpotArray[3] = longitude;
                        LocationArray[0] = latitude;
                        LocationArray[1] = longitude;
                        LocationArray[2] = 20;
                        LocationArray[3] = 20;
                        ContentValues values = new ContentValues();
                        values.put(EZParkingContract.EZParking.PARKING_COLUMN_LATITUDE_NAME, latitude);
                        values.put(EZParkingContract.EZParking.PARKING_COLUMN_LONGITUDE_NAME, longitude);
                        db.insert(EZParkingContract.EZParking.PARKING_TABLE_NAME,
                                  null,
                                  values);
                        Log.d("LQM", "Latitude: " + Double.toString(latitude) + " Longitude: " + Double.toString(longitude));
                    } else {
                        Log.d("LQM", "No geo location found.");
                    }
                }
//                Intent parkIntent = new Intent(this, ParkedHere.class);
//                this.startActivity(parkIntent);
                break;
            case R.id.ListView:
                Log.d("LOM", "Search Button Clicked");
                Intent listIntent = new Intent(this, ListView.class);
                //listIntent.putExtra(KEY_PARKSPOTS, parkSpots); // Possible source of error
                listIntent.putExtra(KEY_TARGETSPOT, ImportantSpotArray);
                //listIntent.putExtra(KEY_LISTOFSPOTS, parkSpots);
                //listIntent.putParcelableArrayListExtra(KEY_LISTOFSPOTS, parkSpots);
                //startActivityForResult(listIntent, REQUEST_CODE_TARGETSPOT);
                this.startActivity(listIntent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==REQUEST_CODE_CHANGE_BUTTON && resultCode == Activity.RESULT_OK) {
            mSearchRadius = data.getStringExtra(KEY_SEARCH_RADIUS);
            Log.d("LOM", "mNumButtons = " + mSearchRadius);
        }
    }
}
