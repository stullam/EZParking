package com.example.stullam.lightsoutmenustull;

/**
 * Created by stullam on 10/28/2015.
 */
public class ParkingSpot {
    public double lattitude;
    public double longitude;
    public String name;
    public double distance;

    public ParkingSpot(String Pname, double lat, double longit) {
        this.lattitude = lat;
        this.longitude = longit;
        this.name = Pname;
        this.distance = 0;
    }

    public double getLattitude() {
        return this.lattitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public String getName() {
        return this.name;
    }
    public void setDistance(double dist) {
        this.distance = dist;
        return;
    }
    public double getDistance() {
        return this.distance;
    }

}
