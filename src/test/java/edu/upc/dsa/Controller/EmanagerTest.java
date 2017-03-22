package edu.upc.dsa.Controller;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import edu.upc.dsa.Model.Eetakemon;
import edu.upc.dsa.Model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import javax.jws.soap.SOAPBinding;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutput;
import java.net.UnknownServiceException;
import java.util.ArrayList;

/**
 * Created by $uperuser on 12/03/2017.
 */
public class EmanagerTest {
    Emanager em = new Emanager();
    User usr = new User();

    @Test
    public void loadEetakemonsMapTest() throws FileNotFoundException, JsonIOException, JsonSyntaxException {
        em.loadEetakemonsMap(usr);
        Assert.assertEquals(5, usr.emap.size());
    }
    @Before
    public void setUp() throws IOException {
        em.loadEetakemonsMap(usr);

    }
    @After
    public void tearDown() {
        em.delAllEetakemonsFromMap(usr);
    }
    @Test
    public  void addEetakemonToUserMapTest() {
        Object tmpobj = em.addEetakemonToUserMap(usr, usr.emap.get(1));
        Assert.assertNotEquals(null, tmpobj);
    }
    @Test
    public void delEetakemonFromMapTest() {
        em.delEetakemonFromMap(usr, 1);
        Assert.assertNotEquals(6, usr.emap.size());
    }
    @Test
    public void getEetakemonFromMapTest() {
        int two = em.getEetakemonFromMap(usr, 2).id;
        Assert.assertNotEquals(2, two);
    }
    @Test
    public void getEtakemonFromMapByNameAproximationTest() {
        ArrayList<Eetakemon> reslist = em.getEtakemonFromMapByNameAproximation(usr, "nmon");
        Assert.assertEquals("Juanmon", reslist.get(0).nombre);
        Assert.assertEquals("Rinconmon", reslist.get(1).nombre);
    }
}
