package edu.upc.dsa.Controller;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import edu.upc.dsa.Model.Eetakemon;
import edu.upc.dsa.Model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by $uperuser on 12/03/2017.
 */
public class EmanagerTest {
    User usr = new User();

    @Before public void loadEetakemonsUsersMapTest() throws FileNotFoundException, JsonIOException, JsonSyntaxException {
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
    @After
    public void tearDown() {
        Emanager.getInstance().delAllEetakemonsFromMap(usr);
        usr=null;
        Umanager.umap.clear();
    }
    @Test
    public  void addEetakemonToUserMapTest() {
        Object tmpobj = Emanager.getInstance().addEetakemonToUserMap(usr, usr.emap.get(1));
        Assert.assertNotEquals(null, tmpobj);
    }
    @Test
    public void delEetakemonFromMapTest() {
        Emanager.getInstance().delEetakemonFromMap(usr, 1);
        Assert.assertNotEquals(6, usr.emap.size());
    }
    @Test
    public void getEetakemonFromMapTest() {
        int two = Emanager.getInstance().getEetakemonFromMap(usr, 2).id;
        Assert.assertEquals(2, two);
    }
    @Test
    public void getEtakemonFromMapByNameAproximationTest() {
        ArrayList<Eetakemon> reslist = Emanager.getInstance().getEtakemonFromMapByNameAproximation(usr, "nmon");
        Assert.assertEquals("Juanmon", reslist.get(0).name);
        Assert.assertEquals("Rinconmon", reslist.get(1).name);
    }
}
