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
        this.emap = new HashMap<>();
        this.id = lastid;
        lastid++;
    }
    public User() {
        this.emap = new HashMap<>();
        this.id = lastid;
        lastid++;
    }

    public boolean isAdmin() {
        return isadmin;
    }
}
