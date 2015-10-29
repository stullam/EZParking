package com.example.stullam.lightsoutmenustull;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class ListView extends AppCompatActivity implements View.OnClickListener {

    private Button Park1;
    private Button Park2;
    private Button Park3;
    private Button Park4;
    private Button Park5;
    private Button Park6;
    private Button Park7;
    private Button Park8;
    private Button Park9;
    private Button Park10;

    private ParkingSpot Parking1 = new ParkingSpot("Parking1", 39.4698, -87.3898);
    private ParkingSpot Parking2 = new ParkingSpot("Parking2", 42.4700, -87.3898);
    private ParkingSpot Parking3 = new ParkingSpot("Parking3", 45.4702, -87.3898);
    private ParkingSpot Parking4 = new ParkingSpot("Parking4", 38.4704, -87.3898);
    private ParkingSpot Parking5 = new ParkingSpot("Parking5", 39.5706, -87.3898);
    private ParkingSpot Parking6 = new ParkingSpot("Parking6", 43.4708, -87.3898);
    private ParkingSpot Parking7 = new ParkingSpot("Parking7", 51.4710, -87.3898);
    private ParkingSpot Parking8 = new ParkingSpot("Parking8", 30.4712, -87.3898);
    private ParkingSpot Parking9 = new ParkingSpot("Parking9", 32.4714, -87.3898);
    private ParkingSpot Parking10 = new ParkingSpot("Parking10", 35.4716, -87.3898);

    //private ArrayList<ParkingSpot> ListOfSpots = new ArrayList<ParkingSpot>();
    private double[] TargetSpot = new double[2];
    private double[][] SortedListByDistances = new double[10][2];

    public ArrayList<ParkingSpot> parkSpots = new ArrayList<ParkingSpot>();
    public ArrayList<ParkingSpot> distanceSortedSpots = new ArrayList<ParkingSpot>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

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

        TargetSpot = (double[]) this.getIntent().getDoubleArrayExtra(LightsOutMenu.KEY_PARKSPOTS);
        Log.d("LOM", "list of spots 0 = " + TargetSpot[0]);
        Log.d("LOM", "list of spots 1 = " + TargetSpot[1]);

        sortSpotByDistance(parkSpots, TargetSpot);

        Park1 = (Button) findViewById(R.id.Park1);
        Park1.setText((String) distanceSortedSpots.get(0).getName());
        Park1.setOnClickListener(this);

        Park2 = (Button)findViewById(R.id.Park2);
        Park2.setText((String)distanceSortedSpots.get(1).getName());
        Park2.setOnClickListener(this);

        Park3 = (Button)findViewById(R.id.Park3);
        Park3.setText((String)distanceSortedSpots.get(2).getName());
        Park3.setOnClickListener(this);

        Park4 = (Button)findViewById(R.id.Park4);
        Park4.setText((String)distanceSortedSpots.get(3).getName());
        Park4.setOnClickListener(this);

        Park5 = (Button)findViewById(R.id.Park5);
        Park5.setText((String)distanceSortedSpots.get(4).getName());
        Park5.setOnClickListener(this);

        Park6 = (Button)findViewById(R.id.Park6);
        Park6.setText((String)distanceSortedSpots.get(5).getName());
        Park6.setOnClickListener(this);

        Park7 = (Button)findViewById(R.id.Park7);
        Park7.setText((String)distanceSortedSpots.get(6).getName());
        Park7.setOnClickListener(this);

        Park8 = (Button)findViewById(R.id.Park8);
        Park8.setText((String)distanceSortedSpots.get(7).getName());
        Park8.setOnClickListener(this);

        Park9 = (Button)findViewById(R.id.Park9);
        Park9.setText((String)distanceSortedSpots.get(8).getName());
        Park9.setOnClickListener(this);

        Park10 = (Button)findViewById(R.id.Park10);
        Park10.setText((String)distanceSortedSpots.get(9).getName());
        Park10.setOnClickListener(this);
    }

    private void sortSpotByDistance(ArrayList<ParkingSpot> possibleSpots, double[] targetSpot) {
        double targetLat = targetSpot[0];
        double targetLong = targetSpot[1];
        double[] locDistHolder = new double[possibleSpots.size()];

        for(int i = 0; i< possibleSpots.size(); i++) {
            double tempLat = possibleSpots.get(i).getLattitude();
            double tempLong = possibleSpots.get(i).getLongitude();

            double crowDist = Math.sqrt(((targetLat-tempLat) * (targetLat-tempLat)) + ((targetLong - tempLong) * (targetLat-tempLat)));
            possibleSpots.get(i).setDistance(crowDist);
            locDistHolder[i] = crowDist;
        }

        Arrays.sort(locDistHolder);

        for(int j = 0; j< possibleSpots.size(); j++) {
            for(int k = 0; k< possibleSpots.size(); k++) {
                if(possibleSpots.get(k).getDistance() == locDistHolder[j]) {
                    distanceSortedSpots.add(possibleSpots.get(k));
                }
            }
        }
    }

    public void onClick(View v) {
        //if(v.getId() == R.id.button)
        switch (v.getId()) {
            case R.id.Park1:
                Log.d("LOM", "Settings Button Clicked");
                Intent Park1Intent = new Intent(this, SettingsActivity.class);
                //settingsIntent.putExtra(KEY_SEARCH_RADIUS, mSearchRadius); // Possible source of error
                //startActivityForResult(settingsIntent, REQUEST_CODE_CHANGE_BUTTON);
                //Park1Intent.putExtra(KEY_PARK1, Park1Array);
                this.startActivity(Park1Intent);
                break;
            case R.id.Park2:
                Log.d("LOM", "Search Button Clicked");
                Intent Park2Intent = new Intent(this, Search.class);
                //Park1Intent.putExtra(KEY_PARK1, Park1Array);
                this.startActivity(Park2Intent);
                break;
            case R.id.Park3:
                Log.d("LOM", "Settings Button Clicked");
                Intent Park3Intent = new Intent(this, SettingsActivity.class);
                //settingsIntent.putExtra(KEY_SEARCH_RADIUS, mSearchRadius); // Possible source of error
                //startActivityForResult(settingsIntent, REQUEST_CODE_CHANGE_BUTTON);
                //Park1Intent.putExtra(KEY_PARK1, Park1Array);
                this.startActivity(Park3Intent);
                break;
            case R.id.Park4:
                Log.d("LOM", "Search Button Clicked");
                Intent Park4Intent = new Intent(this, Search.class);
                //Park1Intent.putExtra(KEY_PARK1, Park1Array);
                this.startActivity(Park4Intent);
                break;
            case R.id.Park5:
                Log.d("LOM", "Settings Button Clicked");
                Intent Park5Intent = new Intent(this, SettingsActivity.class);
                //settingsIntent.putExtra(KEY_SEARCH_RADIUS, mSearchRadius); // Possible source of error
                //startActivityForResult(settingsIntent, REQUEST_CODE_CHANGE_BUTTON);
                //Park1Intent.putExtra(KEY_PARK1, Park1Array);
                this.startActivity(Park5Intent);
                break;
            case R.id.Park6:
                Log.d("LOM", "Search Button Clicked");
                Intent Park6Intent = new Intent(this, Search.class);
                //Park1Intent.putExtra(KEY_PARK1, Park1Array);
                this.startActivity(Park6Intent);
                break;
            case R.id.Park7:
                Log.d("LOM", "Settings Button Clicked");
                Intent Park7Intent = new Intent(this, SettingsActivity.class);
                //settingsIntent.putExtra(KEY_SEARCH_RADIUS, mSearchRadius); // Possible source of error
                //startActivityForResult(settingsIntent, REQUEST_CODE_CHANGE_BUTTON);
                //Park1Intent.putExtra(KEY_PARK1, Park1Array);
                this.startActivity(Park7Intent);
                break;
            case R.id.Park8:
                Log.d("LOM", "Search Button Clicked");
                Intent Park8Intent = new Intent(this, Search.class);
                //Park1Intent.putExtra(KEY_PARK1, Park1Array);
                this.startActivity(Park8Intent);
                break;
            case R.id.Park9:
                Log.d("LOM", "Settings Button Clicked");
                Intent Park9Intent = new Intent(this, SettingsActivity.class);
                //settingsIntent.putExtra(KEY_SEARCH_RADIUS, mSearchRadius); // Possible source of error
                //startActivityForResult(settingsIntent, REQUEST_CODE_CHANGE_BUTTON);
                //Park1Intent.putExtra(KEY_PARK1, Park1Array);
                this.startActivity(Park9Intent);
                break;
            case R.id.Park10:
                Log.d("LOM", "Search Button Clicked");
                Intent Park10Intent = new Intent(this, Search.class);
                //Park1Intent.putExtra(KEY_PARK1, Park1Array);
                this.startActivity(Park10Intent);
                break;
        }
    }
}
