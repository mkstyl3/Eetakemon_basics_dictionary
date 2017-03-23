package edu.upc.dsa.Controller;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import edu.upc.dsa.Model.Eetakemon;
import edu.upc.dsa.Model.User;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by $uperuser on 10/03/2017.
 */

public class Emanager {

    private static Emanager instance = null;

    public static Emanager getInstance() {
        if (instance == null) instance = new Emanager();
        return instance;
    }

    public void loadEetakemonsMap(User usr) throws FileNotFoundException, JsonIOException, JsonSyntaxException {
        BufferedReader reader = new BufferedReader(new FileReader(new File("./src/main/resources/Eetakemons.txt")));
        Type type = new TypeToken<Map<Integer, Eetakemon>>(){}.getType();
        usr.emap = new Gson().fromJson(reader, type);
        Eetakemon.ultimoid = usr.emap.size() + 1; //Actualizo a 6, xk cargo 5 Eetakemons
    }
    public Eetakemon addEetakemonToUserMap(User usr, Eetakemon e) {
        return usr.emap.put(e.id, e);
    }


    public Eetakemon delEetakemonFromMap(User usr, int key) {
        return usr.emap.remove(key);
    }

    public Eetakemon getEetakemonFromMap(User usr, int key) {
        return usr.emap.get(key);
    }

    public ArrayList<Eetakemon> getEtakemonFromMapByNameAproximation (User usr, String input) {
        ArrayList<Eetakemon> aproxlist = new ArrayList<>();
        for (Map.Entry<Integer,Eetakemon> entry : usr.emap.entrySet()) {
            if (entry.getValue().nombre.contains(input)) {
                aproxlist.add(entry.getValue());
            }
        }
        return aproxlist;
    }
    public void delAllEetakemonsFromMap(User usr) {
        usr.emap.clear();
    }

}


