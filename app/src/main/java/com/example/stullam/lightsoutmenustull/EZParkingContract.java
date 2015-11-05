package com.example.stullam.lightsoutmenustull;

import android.provider.BaseColumns;

/**
 * Created by raspst on 11/2/2015.
 */
public final class EZParkingContract {

    public EZParkingContract () {}

    public static abstract class EZParking implements BaseColumns {
        // Name and column names for table that will hold the parking spots
        public static final String PARKING_TABLE_NAME = "ParkingSpots";
        public static final String PARKING_COLUMN_ID = "id";
        //public static final String PARKING_COLUMN_SPOT_NAME = "Parking Spot Name";
        public static final String PARKING_COLUMN_LATITUDE_NAME = "Latitude";
        public static final String PARKING_COLUMN_LONGITUDE_NAME = "Longitude";
        public static final String PARKING_COLUMN_PRICE_NAME = "Price";

        // Name and column names for table that will hold settings and their values
        public static final String SETTINGS_TABLE_NAME = "Settings";
        public static final String SETTINGS_COLUMN_ID = "id";
        public static final String SETTINGS_COLUMN_NAME = "SpinnerVals";
        public static final String SETTINGS_COLUMN_SPINNER_NAME = "Spin";
        public static final String SETTINGS_COLUMN_SPINNER1_NAME = "Spin1";
        public static final String SETTINGS_COLUMN_SPINNER2_NAME = "Spin2";

        // Strings for entering types in SQL queries
        public static final String TEXT_TYPE = " TEXT";
        public static final String INTEGER_TYPE = " INTEGER";
        public static final String REAL_TYPE = " REAL";
        public static final String NULL_TYPE = " NULL";
        public static final String COMMA_SEP = " ,";

        // Strings for creating tables
        public static final String SQL_CREATE_PARKING_TABLE =
                "CREATE TABLE " + EZParking.PARKING_TABLE_NAME + " (" +
                        EZParking._ID + " INTEGER PRIMARY KEY," +
//                        EZParking.PARKING_COLUMN_SPOT_NAME + TEXT_TYPE + COMMA_SEP +
                        EZParking.PARKING_COLUMN_LATITUDE_NAME + REAL_TYPE + COMMA_SEP +
                        EZParking.PARKING_COLUMN_LONGITUDE_NAME + REAL_TYPE + COMMA_SEP +
                        EZParking.PARKING_COLUMN_PRICE_NAME + REAL_TYPE + " )";

        public static final String SQL_CREATE_SETTINGS_TABLE =
                "CREATE TABLE " + EZParking.SETTINGS_TABLE_NAME + " (" +
                        EZParking._ID + " INTEGER PRIMARY KEY," + EZParking.SETTINGS_COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                        EZParking.SETTINGS_COLUMN_SPINNER_NAME +
                        INTEGER_TYPE + COMMA_SEP + SETTINGS_COLUMN_SPINNER1_NAME + INTEGER_TYPE + COMMA_SEP +
                        SETTINGS_COLUMN_SPINNER2_NAME + INTEGER_TYPE + " )";

        // Strings for deleting tables
        public static final String SQL_DELETE_PARKING_TABLE =
                "DROP TABLE IF EXISTS " + PARKING_TABLE_NAME;

        public static final String SQL_DELETE_SETTINGS_TABLE =
                "DROP TABLE IF EXISTS " + SETTINGS_TABLE_NAME;
    }
}
