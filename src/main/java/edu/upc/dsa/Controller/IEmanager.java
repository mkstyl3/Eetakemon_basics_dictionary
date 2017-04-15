package edu.upc.dsa.Controller;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import edu.upc.dsa.Model.Eetakemon;
import edu.upc.dsa.Model.User;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by $uperuser on 29/03/2017.
 */
public interface IEmanager {
    public Eetakemon addEetakemonToUserMap(User usr, Eetakemon e);
    public Eetakemon delEetakemonFromMap(User usr, int key);
    public Eetakemon getEetakemonFromMap(User usr, int key);
    public ArrayList<Eetakemon> getEtakemonFromMapByNameAproximation (User usr, String input);
    public void delAllEetakemonsFromMap(User usr);
    public void loadEetakemonsUsersMap(User usr) throws FileNotFoundException, JsonIOException, JsonSyntaxException;
}
