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

    public static HashMap umap;
    public static int ultimoid = 1;
    private static Umanager instance = null;
    private Location loc;

    public static Umanager getInstance() {
        if (instance == null) instance = new Umanager();
        return instance;
    }
    public Umanager () {
        umap = new HashMap<>();
    }
    public Object addUserToUsersMap(User usr) {
        return umap.put(usr.id, usr);
    }
    public Object delUserFromMap(int key) {
        return umap.remove(key);
    }
    public Object getUserFromMap(int key) {
        return umap.get(key);
    }
    public void delUsersFromMap() { umap.clear(); }
    public boolean usrAuthentication(String usrname, String pw) throws NullPointerException, MissingResourceException {
        Boolean successful = false;
        ResourceBundle usrdata = ResourceBundle.getBundle("Users_data");
        if (pw.equals(usrdata.getString(usrname))) {
            return successful = true;
        }
        else return successful;
    }
}
