package edu.upc.dsa.Controller;

import edu.upc.dsa.Model.Eetakemon;
import edu.upc.dsa.Model.Location;
import edu.upc.dsa.Model.User;

import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.*;

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

    public Umanager() {
        umap = new HashMap<>();
    }

    public Object addUserToUsersMap(User usr) throws AdminException {
        if (usr.isadmin) return umap.put(usr.id, usr);
        else {
            throw new AdminException();
        }
    }

    public Object delUserFromMap(int key) {
        return umap.remove(key);
    }

    public Object getUserFromMap(int key) {
        return umap.get(key);
    }

    public void delUsersFromMap() {
        umap.clear();
    }

    public int usrAuthentication(String usrname, String pw) throws NullPointerException, MissingResourceException {
        ResourceBundle rbdata = ResourceBundle.getBundle("Users_data");
        ResourceBundle rbadmin = ResourceBundle.getBundle("Admin_users");

        if (userCheck(rbdata, usrname, pw)) return 0;
        else if (userCheck(rbadmin, usrname, pw)) return 1;
        else return -1;
    }

    public int setUserLoc(User usr, Location loc) {
        if (usr.isadmin) {
            usr.currentloc = loc;
            return 1;
        } else return -1;
    }

    public boolean userCheck(ResourceBundle rbdata, String usrname, String pw) {
        if (rbdata.containsKey(usrname)) {
            Enumeration<String> keys = rbdata.getKeys();
            while (keys.hasMoreElements()) {
                String key = keys.nextElement();
                if (key.equals(usrname) && rbdata.getString(key).equals(pw)) {
                    return true;
                }
            }
        } return false;
    }
}
