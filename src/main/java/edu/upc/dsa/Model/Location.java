package edu.upc.dsa.Model;

import java.util.Random;

import static java.lang.Math.PI;

/**
 * Created by $uperuser on 23/03/2017.
 */
public class Location {
    public double lon;
    public double lat;
    public Random r;

    public Location () {}
    public Location (double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }
}
