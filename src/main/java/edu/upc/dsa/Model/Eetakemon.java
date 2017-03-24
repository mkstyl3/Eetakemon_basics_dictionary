package edu.upc.dsa.Model;

/**
 * Created by $uperuser on 27/02/2017.
 */
public class Eetakemon {
    public int id;
    public String name;
    public String type;
    public int lvl;
    public Location currentLocation;

    public static int lastid = 1;

    public Eetakemon () {
        this.id = lastid;
        lastid++;
    }
    public Eetakemon (String name, String type, int lvl) {
        this.id = lastid;
        this.name = name;
        this.type = type;
        this.lvl = lvl;
        lastid++;
    }
}
