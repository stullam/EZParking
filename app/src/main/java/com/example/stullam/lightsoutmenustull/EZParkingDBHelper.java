package com.example.stullam.lightsoutmenustull;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by raspst on 11/5/2015.
 */
public class EZParkingDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "EZParking.db";
    private static EZParkingDBHelper instance = null;

    public EZParkingDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized  EZParkingDBHelper getInstance(Context context) {
        if(instance == null) {
            instance = new EZParkingDBHelper(context);
        }
        return instance;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(EZParkingContract.EZParking.SQL_CREATE_PARKING_TABLE);
        db.execSQL(EZParkingContract.EZParking.SQL_CREATE_SETTINGS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
