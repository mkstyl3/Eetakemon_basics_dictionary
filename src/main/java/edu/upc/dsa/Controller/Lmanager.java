//////////////////////////////////////////////////////////////////////////////////
// Credits to Snippet $28591 from Julian Mann https://gitlab.com/snippets/28591 //
///////////////////////// Adaptation from Javascript /////////////////////////////
//////////////////////////////////////////////////////////////////////////////////

package edu.upc.dsa.Controller;

import edu.upc.dsa.Model.Eetakemon;
import edu.upc.dsa.Model.Location;

import static java.lang.Math.PI;

public class Lmanager {

    private static Lmanager instance = null;
    private final double EARTH_RADIUS = 6371000; /* meters  */
    private final double THREE_PI = Math.PI*3;
    private final double TWO_PI = Math.PI*2;

    public static Lmanager getInstance() {
        if (instance == null) instance = new Lmanager();
        return instance;
    }

    private Location pointAtDistance(Location inputCoords, double distance) {
        Location coords = toRadians(inputCoords);
        double sinLat =  Math.sin(coords.lat);
        double cosLat =  Math.cos(coords.lat);

    /* go a fixed distance in a random direction*/
        double bearing = Math.random() * TWO_PI;
        double theta = distance/EARTH_RADIUS;
        double sinBearing = Math.sin(bearing);
        double cosBearing = Math.cos(bearing);
        double sinTheta = Math.sin(theta);
        double cosTheta = Math.cos(theta);

        Location resultcoords = new Location();
        resultcoords.lat = Math.asin(sinLat*cosTheta+cosLat*sinTheta*cosBearing);
        resultcoords.lon = coords.lon + Math.atan2 (sinBearing*sinTheta*cosLat, cosTheta-sinLat*Math.sin(resultcoords.lat));
    /* normalize -PI -> +PI radians (-180 - 180 deg)*/
        resultcoords.lon = ((resultcoords.lon+THREE_PI)%TWO_PI) - PI;
        return toDegrees(resultcoords);
    }
    public Location pointInCircle(Location coord, double distance) {
    double rnd =  Math.random();
    /*use square root of random number to avoid high density at the center*/
    double randomDist = Math.sqrt(rnd) * distance;
        return pointAtDistance(coord, randomDist);
    }
    private Location toRadians(Location loc) {
        loc.lat = (loc.lat*PI)/180;
        loc.lon = (loc.lon*PI)/180;
        return loc;
    }
    private Location toDegrees(Location loc) {
        loc.lat = (loc.lat*180)/PI;
        loc.lon = (loc.lon*180)/PI;
        return loc;
    }
    public void setEtakemonRandLocationByType(Eetakemon e) {
        if (e.type.equals("Dragon")) e.currentloc = pointInCircle(new Location(41.27558333, 1.98675), 70);
        if (e.type.equals("Fuego")) e.currentloc = pointInCircle(new Location(41.27514444, 1.984991667), 70);
        if (e.type.equals("Tierra")) e.currentloc = pointInCircle(new Location(41.27455556, 1.987327778), 28);
    }
}
