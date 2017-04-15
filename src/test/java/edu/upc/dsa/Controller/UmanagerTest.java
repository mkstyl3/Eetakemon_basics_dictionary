package edu.upc.dsa.Controller;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import edu.upc.dsa.Model.Location;
import edu.upc.dsa.Model.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import java.io.FileNotFoundException;


/**
 * Created by $uperuser on 15/04/2017.
 */
public class UmanagerTest {

    final User usr = new User("marc", "2314", "@mail", true, 34.21312, 4.43244); //Admin
    User u = new User(); // No admin

    @After
    public void setStaticsToDefault() {
        Umanager.umap.clear();
        User.lastid = 0;
    }
    @Test
    public void usrAuthenticationTest() {
        Assert.assertEquals(1, Umanager.getInstance().usrAuthentication("marc", "2314"));
    }

    @Test
    public void usrAuthenticationTest2() {
        Assert.assertEquals(0, Umanager.getInstance().usrAuthentication("ivan", "1234"));
    }

    @Test
    public void usrAuthenticationTest3() {
        Assert.assertEquals(-1, Umanager.getInstance().usrAuthentication("frefer", "freferf"));
    }

    @Test
    public void addUserToUsersMapTest() throws AdminException {
        try {
            Umanager.getInstance().addUserToUsersMap(usr, u);
            Assert.assertEquals(1, Umanager.umap.size());
        } catch (AdminException e) {
            Assert.fail("usr no era admin en la funcion addUserToUsersMapTest()");
        }
    }

    @Test
    public void getUserFromMapTest() throws AdminException {
        try {
            Umanager.getInstance().addUserToUsersMap(usr, u);
            Assert.assertNotNull(Umanager.getInstance().getUserFromMap(usr, 1));
        } catch (AdminException e) {
            Assert.fail("usr no era admin en la funcion addUserToUsersMapTest()");
        }
    }

    @Test
    public void delUsersFromMapTest() throws AdminException {

        try {
            Umanager.getInstance().addUserToUsersMap(usr, u);
            Umanager.getInstance().delUsersFromMap(usr);
            Assert.assertEquals(0, Umanager.umap.size());
        } catch (AdminException e) {
            Assert.fail("usr no era admin en la funcion addUserToUsersMapTest()");
        }

    }
    @Test
    public void setUserLocTest() throws AdminException {
        try {

            Location loc = new Location(43.213,42.2313);
            Umanager.getInstance().addUserToUsersMap(usr,u);
            Assert.assertEquals(true, Umanager.getInstance().setUserLoc(usr, u, loc));
        } catch (AdminException e) {
            Assert.fail("usr no era admin en la funcion addUserToUsersMapTest()");
        }
    }
    @Test
    public void showAllUsersInHashmapTest() throws AdminException {

        try {
            Umanager.getInstance().showAllUsersInHashmap(usr);
            Assert.assertEquals(0, Umanager.umap.size());

        }catch (AdminException e) {
            Assert.fail("usr no era admin en la funcion addUserToUsersMapTest()");
        }
    }
    @Test
    public void delUserFromMapTest() throws AdminException {

        try {
            Umanager.getInstance().addUserToUsersMap(usr,u);
            Umanager.getInstance().delUserFromMap(usr,1);
            Assert.assertEquals(0, Umanager.umap.size());
        } catch (AdminException e) {
            Assert.fail("usr no era admin en la funcion addUserToUsersMapTest()");
        }
    }
    @Test
    public void loadEetakemonsUsersMapTest() throws FileNotFoundException, JsonIOException, JsonSyntaxException {

        try {
            Emanager.getInstance().loadEetakemonsUsersMap(usr);
        } catch (FileNotFoundException e) {
            Assert.fail("No se encontro el fichero txt con los Eetakemons.");
        } catch (JsonIOException e) {
            Assert.fail("Hubo algun problema al leer del reader.");
        } catch (JsonSyntaxException e) {
            Assert.fail("Problema de sintaxis en el JSON");
        }
    }
}


