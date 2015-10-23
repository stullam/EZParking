package com.example.stullam.lightsoutmenustull;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

public class SettingsActivity extends AppCompatActivity {

    Spinner spin;
    Spinner spin1;
    Spinner spin2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Intent data = this.getIntent();
        int settingData = data.getIntExtra(LightsOutMenu.SEARCH_RADIUS, -1);

        Button button = (Button) findViewById(R.id.button);
        spin = (Spinner) findViewById(R.id.spin);
        spin1 = (Spinner) findViewById(R.id.spin1);
        spin2 = (Spinner) findViewById(R.id.spin2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePreferences("spin", spin.getSelectedItemPosition());
                savePreferences("spin1", spin1.getSelectedItemPosition());
                savePreferences("spin2", spin2.getSelectedItemPosition());

                Intent returnIntent = new Intent();
                returnIntent.putExtra(LightsOutMenu.SEARCH_RADIUS, Integer.parseInt(spin.getSelectedItem().toString()));

                setResult(RESULT_OK, returnIntent);
            }
        });
        loadPreferences();

    }

    void loadPreferences() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        int spinValue = sharedPrefs.getInt("spin", -1);
        int spin1Value = sharedPrefs.getInt("spin1", -1);
        int spin2Value = sharedPrefs.getInt("spin2", -1);
        //Log.d(SettingsActivity.class.getSimpleName(), Integer.toString(spinValue));
        if(spinValue != -1) {
            spin.setSelection(spinValue);
        }
        if(spin1Value != -1) {
            spin1.setSelection(spin1Value);
        }
        if(spin2Value != -1) {
            spin2.setSelection(spin2Value);
        }
    }

    void savePreferences(String key, int value) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.clear();
        editor.putInt(key, value);
        editor.commit();
    }
}
