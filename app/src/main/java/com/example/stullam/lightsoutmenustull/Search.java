package com.example.stullam.lightsoutmenustull;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.util.List;

public class Search extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();
        String address = intent.getStringExtra("address");
        double address2 = intent.getDoubleExtra("address2", 0);

        Geocoder gc = new Geocoder(this);
        try {
            List<Address> list = gc.getFromLocationName(address,1);
            Address add = list.get(0);
            String locality = add.getLocality();
            System.out.println(add.getLatitude() + ", " + add.getLongitude());
            Intent MapIntent = new Intent(this, MapsActivity.class);
            MapIntent.putExtra("Lat",add.getLatitude());
            MapIntent.putExtra("Lon",add.getLongitude());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
