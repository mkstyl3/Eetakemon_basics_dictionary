package edu.upc.dsa.Model;

import java.util.*;

/**
 * Created by $uperuser on 28/02/2017.
 */
public class User {
    public int id;
    public String password;
    public String email;
    public HashMap<Integer, Eetakemon> emap;
    public static int lastid = 1;

    public User () {
        this.emap = new HashMap<>();
        this.id = lastid;
        lastid++;
    }
}
