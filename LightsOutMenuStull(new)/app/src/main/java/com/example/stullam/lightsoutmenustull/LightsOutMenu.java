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


public class LightsOutMenu extends AppCompatActivity implements View.OnClickListener {

    private Button mSettingsButton;
    private Button mSearchButton;
    private Button mFindNearestButton;
    private Button mMapViewButton;
    private Button mMatchPrefButton;
    private Button mParkedHereButton;
    private Button mListViewButton;
    private Button mDemoButton;

    static final String SEARCH_RADIUS = "SEARCH_RADIUS";
    private int mSearchRadius = 0;
    private static final int REQUEST_CODE_CHANGE_BUTTON = 1;

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
        switch (v.getId()) {
            case R.id.Settings:
                Log.d("LOM", "Settings Button Clicked");
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                settingsIntent.putExtra(SEARCH_RADIUS, mSearchRadius); // Possible source of error
                //this.startActivity(settingsIntent);
                startActivityForResult(settingsIntent, REQUEST_CODE_CHANGE_BUTTON);
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
                Intent mapIntent = new Intent(this, MapView.class);
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
                this.startActivity(listIntent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==REQUEST_CODE_CHANGE_BUTTON && resultCode == Activity.RESULT_OK) {
            mSearchRadius = data.getIntExtra(SEARCH_RADIUS, 7);
            mDemoButton.setText(mSearchRadius);
        }
    }
}
