package com.example.stullam.lightsoutmenustull;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    Spinner spin;
    Spinner spin1;
    Spinner spin2;

    private EZParkingDBHelper dbHelper;
    private SQLiteDatabase readDB;
    private SQLiteDatabase writeDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Intent data = this.getIntent();
        int settingData = data.getIntExtra(LightsOutMenu.KEY_SEARCH_RADIUS, -1);

        Button button = (Button) findViewById(R.id.button);
        spin = (Spinner) findViewById(R.id.spin);
        spin1 = (Spinner) findViewById(R.id.spin1);
        spin2 = (Spinner) findViewById(R.id.spin2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePreferences();

                Intent returnIntent = new Intent();
//                returnIntent.putExtra(LightsOutMenu.KEY_SEARCH_RADIUS, Integer.parseInt(spin.getSelectedItem().toString().replaceAll("[\\D]", "")));
//                returnIntent.putExtra(LightsOutMenu.KEY_MAX_PRICE, Integer.parseInt(spin1.getSelectedItem().toString().replaceAll("[\\D]", "")));
                returnIntent.putExtra(LightsOutMenu.KEY_PREFERENCE, spin2.getSelectedItem().toString());

                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
        dbHelper = EZParkingDBHelper.getInstance(this.getApplicationContext());
        readDB = dbHelper.getReadableDatabase();
        writeDB = dbHelper.getWritableDatabase();

        loadPreferences();

    }

    @Override
    public void onClick(View v) {

    }

    void loadPreferences() {
//        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        int spinValue = -1;
        int spin1Value = -1;
        int spin2Value = -1;
        //SharedPreferences sharedPrefs = this.getSharedPreferences("prefs", Activity.MODE_PRIVATE);
        String[] projection = {EZParkingContract.EZParking.SETTINGS_COLUMN_SPINNER_NAME,
                                                 EZParkingContract.EZParking.SETTINGS_COLUMN_SPINNER1_NAME,
                                                 EZParkingContract.EZParking.SETTINGS_COLUMN_SPINNER2_NAME};

        Cursor c = readDB.query(EZParkingContract.EZParking.SETTINGS_TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            int spinIndex = c.getColumnIndex(EZParkingContract.EZParking.SETTINGS_COLUMN_SPINNER_NAME);
            spinValue = c.getInt(spinIndex);
            System.out.println("SPIN VALUE: " + spinValue);
            int spin1Index = c.getColumnIndex(EZParkingContract.EZParking.SETTINGS_COLUMN_SPINNER1_NAME);
            spin1Value = c.getInt(spin1Index);
            System.out.println("SPIN1 VALUE: " + spin1Value);
            int spin2Index = c.getColumnIndex(EZParkingContract.EZParking.SETTINGS_COLUMN_SPINNER2_NAME);
            spin2Value = c.getInt(spin2Index);
            System.out.println("SPIN2 VALUE: " + spin2Value);
            //Log.d(SettingsActivity.class.getSimpleName(), Integer.toString(spinValue));
        }
        if(spinValue != -1) {
            spin.setSelection(spinValue);
        }
        if (spin1Value != -1) {
            spin1.setSelection(spin1Value);
        }
        if(spin2Value != -1) {
            spin2.setSelection(spin2Value);
        }
    }

    void savePreferences() {
//        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        //SharedPreferences sharedPrefs = this.getSharedPreferences("prefs", Activity.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPrefs.edit();
        ContentValues values = new ContentValues();
        String[] proj = {EZParkingContract.EZParking.SETTINGS_COLUMN_SPINNER2_NAME};
        Cursor c = readDB.query(EZParkingContract.EZParking.SETTINGS_TABLE_NAME,proj,null,null,null,null,null );
        values.put(EZParkingContract.EZParking.SETTINGS_COLUMN_NAME, "SettingsVal");
        values.put(EZParkingContract.EZParking.SETTINGS_COLUMN_SPINNER_NAME, spin.getSelectedItemPosition());
        values.put(EZParkingContract.EZParking.SETTINGS_COLUMN_SPINNER1_NAME, spin1.getSelectedItemPosition());
        values.put(EZParkingContract.EZParking.SETTINGS_COLUMN_SPINNER2_NAME, spin2.getSelectedItemPosition());
        if(c.getCount() == 0) {
            writeDB.insert(EZParkingContract.EZParking.SETTINGS_TABLE_NAME, null, values);
        }
        else {
            writeDB.update(EZParkingContract.EZParking.SETTINGS_TABLE_NAME, values, EZParkingContract.EZParking.SETTINGS_COLUMN_NAME + "= SpinnerVals", null);
        }
//        editor.clear();
//        editor.putInt(key, value);
//        editor.commit();
    }
}
