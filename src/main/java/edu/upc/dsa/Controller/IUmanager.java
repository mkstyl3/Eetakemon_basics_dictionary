package edu.upc.dsa.Controller;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import edu.upc.dsa.Model.Location;
import edu.upc.dsa.Model.User;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.MissingResourceException;

/**
 * Created by $uperuser on 15/04/2017.
 */
public interface IUmanager {
    public Object addUserToUsersMap(User usr, User newusr) throws AdminException;
    public User getUserFromMap(User usr, int key) throws AdminException;
    public void delUsersFromMap(User usr) throws AdminException;
    public int usrAuthentication(String usrname, String pw) throws NullPointerException, MissingResourceException;
    public Boolean setUserLoc(User usr, User u, Location loc) throws AdminException;
    public HashMap<Integer, User> showAllUsersInHashmap(User usr) throws AdminException;
    public User delUserFromMap(User usr, int key) throws AdminException;
}
