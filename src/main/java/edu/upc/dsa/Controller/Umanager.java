package edu.upc.dsa.Controller;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import edu.upc.dsa.Model.Eetakemon;
import edu.upc.dsa.Model.Location;
import edu.upc.dsa.Model.User;

import javax.swing.text.StyledEditorKit;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by $uperuser on 23/03/2017.
 */
public class Umanager implements IUmanager {
    public static HashMap<Integer, User> umap;
    private static Umanager instance = null;
    ResourceBundle rbdata = ResourceBundle.getBundle("Users_data");
    ResourceBundle rbadmin = ResourceBundle.getBundle("Admin_users");


    public static Umanager getInstance() {
        if (instance == null) instance = new Umanager();
        return instance;
    }

    public Umanager() {
        umap = new HashMap<>();
    }

    public Object addUserToUsersMap(User usr, User newusr) throws AdminException {
        if (usr.isadmin) {
            //Properties prop = new Properties();
            //prop.setProperty(newusr.username,newusr.password); //No se puede escribir en el properties siendo un rb?...
            //File fileout = new File("Users_data.properties");
            //prop.store(new FileOutputStream(fileout), null);
            return umap.put(newusr.id, newusr);
        } else {
            throw new AdminException();
        }
    }
    public User getUserFromMap(User usr, int key) throws AdminException {
        if (usr.isadmin) {
            return umap.get(key);
        } else throw new AdminException();

    }

    public void delUsersFromMap(User usr) throws AdminException {
        if (usr.isadmin) {
            umap.clear();
        } else throw new AdminException();
    }

    public int usrAuthentication(String usrname, String pw) throws NullPointerException, MissingResourceException {
        if (userCheck(rbdata, usrname, pw)) return 0;
        else if (userCheck(rbadmin, usrname, pw)) return 1;
        else return -1;
    }

    public Boolean setUserLoc(User usr, User u, Location loc) throws AdminException {
        if (usr.isadmin) {
            u.currentloc = loc;
            return true;
        } else throw new AdminException();
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

    public HashMap <Integer, User> showAllUsersInHashmap(User usr) throws AdminException {
        if (usr.isadmin) {
            return umap;
        } else throw new AdminException();

    }
    public User delUserFromMap(User usr, int key) throws AdminException {
        if (usr.isadmin) {
            return umap.remove(key);
        } else throw new AdminException();
    }
}
