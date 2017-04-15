package edu.upc.dsa.Model;

import java.util.*;

/**
 * Created by $uperuser on 28/02/2017.
 */
public class User {
    public int id;
    public String username;
    public String password;
    public String email;
    public HashMap<Integer, Eetakemon> emap;
    public static int lastid = 0;
    public boolean isadmin;
    public Location currentloc;

    public User (String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        emap = new HashMap<>();
        id = lastid;
        lastid++;
    }
    public User (String username, String password, String email, Boolean isadmin, double lat, double lon) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.isadmin = isadmin;
        emap = new HashMap<>();
        currentloc = new Location(lat,lon);
        id = lastid;
        lastid++;
    }
    public User() {
        emap = new HashMap<>();
        id = lastid;
        lastid++;
    }

    public boolean isAdmin() {
        return isadmin;
    }
}
