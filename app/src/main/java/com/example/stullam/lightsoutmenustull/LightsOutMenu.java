package com.example.stullam.lightsoutmenustull;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;


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
    public double[] ParkingSpotsArray = new double[2];

    static final String KEY_SEARCH_RADIUS = "KEY_SEARCH_RADIUS";
    static final String KEY_PARKSPOTS = "KEY_PARKSPOTS";
    private String mSearchRadius = "";
    private static final int REQUEST_CODE_CHANGE_BUTTON = 1;
    private static final int REQUEST_CODE_PARKSPOTS = 1;



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

        ParkingSpotsArray[0] = 39.4696;
        ParkingSpotsArray[1] = -87.3898;

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
                Intent searchIntent = new Intent(this, Search.class);
                this.startActivity(searchIntent);
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
                this.startActivity(mapIntent);
                break;
            case R.id.MatchPreferences:
                Log.d("LOM", "Search Button Clicked");
                Intent matchIntent = new Intent(this, MatchPreferences.class);
                this.startActivity(matchIntent);
                break;
            case R.id.IParked:
                Log.d("LOM", "Search Button Clicked");
                Intent parkIntent = new Intent(this, ParkedHere.class);
                this.startActivity(parkIntent);
                break;
            case R.id.ListView:
                Log.d("LOM", "Search Button Clicked");
                Intent listIntent = new Intent(this, ListView.class);
                //listIntent.putExtra(KEY_PARKSPOTS, parkSpots); // Possible source of error
                listIntent.putExtra(KEY_PARKSPOTS, ParkingSpotsArray);
                startActivityForResult(listIntent, REQUEST_CODE_PARKSPOTS);
                //this.startActivity(listIntent);
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
