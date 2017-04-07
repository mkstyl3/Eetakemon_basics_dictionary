package edu.upc.dsa.Controller;

import edu.upc.dsa.Model.Eetakemon;
import edu.upc.dsa.Model.Location;
import edu.upc.dsa.Model.User;

import java.util.HashMap;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Created by $uperuser on 23/03/2017.
 */
public class Umanager {
    public static HashMap<Integer, User> umap;
    public static int ultimoid = 1;
    private static Umanager instance = null;

    public static Umanager getInstance() {
        if (instance == null) instance = new Umanager();
        return instance;
    }
    public Umanager () {
        umap = new HashMap<>();
    }

    public Object addUserToUsersMap(User usr) {
        if(usr.isadmin) return umap.put(usr.id, usr);
        else return usr;
    }
    public Object delUserFromMap(int key) {
        return umap.remove(key);
    }
    public Object getUserFromMap(int key) {
        return umap.get(key);
    }
    public void delUsersFromMap() { umap.clear(); }

    public int usrAuthentication(String usrname, String pw) throws NullPointerException, MissingResourceException {
        int successful = -1;
        int isadmin = -1;
        ResourceBundle usrdata = ResourceBundle.getBundle("Users_data");
        ResourceBundle admincheck = ResourceBundle.getBundle("Admin_users");

        if (pw.equals(admincheck.getString(usrname))) {
            return isadmin = 1;
        }
        if (pw.equals(usrdata.getString(usrname))) {
            return successful = 0;
        }
        return successful;
    }
    public int setUserLoc (User usr, Location loc) {
        if (usr.isadmin) {
            usr.currentloc = loc;
            return 1;
        }
        else return -1;
    }
}
