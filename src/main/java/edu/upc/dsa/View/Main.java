package edu.upc.dsa.View;

/**
 * Created by $uperuser on 27/02/2017.
 */

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import edu.upc.dsa.Controller.AdminException;
import edu.upc.dsa.Controller.Emanager;
import edu.upc.dsa.Controller.Lmanager;
import edu.upc.dsa.Controller.Umanager;
import edu.upc.dsa.Model.Eetakemon;
import edu.upc.dsa.Model.Location;
import edu.upc.dsa.Model.User;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.*;

//               La intencionalidad de este programa es aprender jugando con las funciones de clase de EetakemonGo //
//               creando una interfaz interactiva de consola capaz de hacer un return a la opcion anterior y       //
//               cerrarse escribiendo exit o quit. Podria utilizarse para conectar con la base de datos en un      //
//               futuro.                                                                                           //
//                                                                                                                 //
public class Main {

    private static final Logger log4j = Logger.getLogger(Main.class);
    private static int showEetakemons(User u) {
        if(!u.emap.isEmpty()) {
            log4j.info("Lista de Eetakemons disponibles:");
            for (HashMap.Entry<Integer, Eetakemon> entry : u.emap.entrySet()) {
                log4j.info(entry.getValue().id + ": " + entry.getValue().name + ", lvl: " + entry.getValue().lvl + ", loc: "+entry.getValue().currentloc.lat +","+entry.getValue().currentloc.lon);
            } return 1;
        }
        else {
            log4j.info("Primero añada un Eetakemon. Reedirigiendo al menu de creacion de Eetakemons...");
            return -1;
        }
    }
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {

        Scanner s = new Scanner(System.in);
        User usr = new User();
        int main = 1;                     // Las necesito static porque cambia su valor en el login.
        int sm1 = 1;
        int sm2 = 1;
        boolean mbucle = true;
        boolean mjump = false;
        int successful = -1;

        final StringBuffer sb = new StringBuffer("Menu principal. Escriba quit o exit para salir. Escriba return para volver a este menu.\n");
        sb.append("1. Añada un Eetakemon.\n");
        sb.append("2. Vea todos sus Eetakemons.\n");
        sb.append("3. Borre un Eetakemon.\n");
        sb.append("4. Explore un Eetakemon (tambien por aproximacion).\n");
        sb.append("5. Cargue 5 Eetakemons para pruebas (Solo se puede usar con tu HashMap de Eetakemons vacio).\n");
        sb.append("6. Añada una localizacion random a su Eetakemon.\n");
        sb.append("7. Añada un nuevo User.\n");
        sb.append("8. Liste todos los usuarios del Hashmap.\n");
        sb.append("9. Borre un user.\n");
        sb.append("10. Borre todos los users.\n");
        sb.append("11. Explore un user.");
        sb.append("12. Salir.\n");

        final String str = ("Menu de creacion de Eetakemons. Escriba quit o exit para salir. Escriba return para volver al menu principal.\n1. Introduzca el nombre del Eetakemon (Debe contener mas de 3 a 15 caracteres y no empezar por un numero):");
        final String str2 = ("2. Introduzca de que type es (\"Fuego\", \"Tierra\" o \"Dragon\"):");
        final String str3 = ("3. Introduzca el lvl (0-100):");
        final String str4 = ("Introduzca el id del Eetakemon quiere borrar. Escriba quit o exit para salir. Escriba return para volver al menu principal.");
        final String str5 = ("Introduzca el id del User que quiere borrar. Escriba quit o exit para salir. Escriba return para volver al menu principal.");
        final String str8 = ("Tiene que introducir un type valido (\"Fuego\", \"Tierra\" o \"Dragon\"):");
        final String str10 = ("Tiene que introducir un nombre valido (Debe contener mas de 3 a 15 caracteres y no empezar por un numero):");
        final String str11 = ("Introduzca el name del Eetakemon que quiere explorar o una aproximacion para realizar la busqueda. Escriba quit o exit para salir. Escriba return para volver al menu principal.");
        final String str12 = ("Cargando sus Eetakemons... Escriba quit o exit para salir. Escriba return para volver al menu principal.");
        final StringBuffer sb2 = new StringBuffer("Bienvenido a su gestor de Eetakemons! Para entrar escriba sus credenciales o escriba exit para salir: \n");
        sb2.append("Escriba su username: ");
        final String str13 = ("Escriba su password: ");
        final String str14 = ("Menu de creacion de Usuarios. Escriba quit o exit para salir. Escriba return para volver al menu principal.\n1. Introduzca el nombre del User (Debe contener mas de 3 a 15 caracteres y no empezar por un numero):");
        final String str15 = ("Escriba un email valido:");
        final String str16 = ("Se le ha asignado una localizacion por defecto. En teoria, en el server, pedira un json con la localizacion del user");
        final String str17 = ("Tiene que introducir password valida (Debe contener de 3 a 15 caracteres:");

        while (mbucle) {
            boolean tmpbucle = true;
            String tmp = null;                                          //La reutilizo cuando la necesito
            try {
                while(successful == -1) {
                    log4j.info(sb2);
                    String tryname = s.nextLine();
                    if (tryname.equals("quit") || tryname.equals("exit")) {
                        main = 8;
                        mjump = true;
                        break;
                    }
                    log4j.info(str13);
                    String trypw = s.nextLine();
                    if (trypw.equals("quit") || trypw.equals("exit")) {
                        main = 8;
                        mjump = true;
                        break;
                    }
                    try {
                        successful = Umanager.getInstance().usrAuthentication(tryname, trypw);
                        if (successful == 1) {
                            usr.id = 0;                           //Se recibiran los datos de la bd//
                            usr.username = tryname;
                            usr.password = trypw;
                            usr.email = "default@eetac.edu";
                            usr.isadmin = true;                   // Se inicializa usr con derechos de administrador//
                            log4j.info("Access granted! Welcome admin "+tryname);
                        } else if  (successful == 0) {
                            usr.id = 0;                           //Se recibiran los datos de la bd//
                            usr.username = tryname;
                            usr.password = trypw;
                            usr.email = "default@eetac.edu";
                            usr.isadmin = false;                         // Se inicializa usr //
                            log4j.info("Access granted! Welcome user "+tryname);
                        } else if (successful == -1) log4j.info("Access denied.");
                    } catch (MissingResourceException e){
                        log4j.fatal("No se ha podido encontrar un user con esa password." + tryname);
                    }
                }
                if (!mjump) {
                    System.out.print(sb);
                    tmp = s.nextLine();
                    main = Integer.parseInt(tmp);
                }
                mjump = false;
                switch (main) {
                    case 1:
                        String ename = null;  //Las necesito inicializadas a null xk luego habra un try que me las pida en el case 3
                        String etype = null;                   //No reutilizo ciertas variables por legibilidad
                        int elvl;
                        switch (sm1) {
                            case 1:
                                log4j.info(str);
                                ename = s.nextLine();
                                while (ename.length() <= 2 || ename.length() >= 16 || Character.isDigit(ename.charAt(0))) {
                                    log4j.info(str10);
                                    ename = s.nextLine();
                                }
                                if (ename.equals("return")) {
                                    break;
                                }
                                if (ename.equals("quit") || ename.equals("exit")) {
                                    mbucle = false;
                                    break;
                                }
                            case 2:
                                log4j.info(str2);
                                etype = s.nextLine();
                                while (!(etype.equals("Fuego") || etype.equals("Tierra") || etype.equals("Dragon") || etype.equals("exit") || etype.equals("quit") || etype.equals("return"))) {
                                    log4j.info(str8);
                                    etype = s.nextLine();
                                }
                                if (etype.equals("return")) {
                                    main = 1;
                                    sm1 = 1;
                                    mjump = true;
                                    break;
                                }
                                if (etype.equals("quit") || etype.equals("exit")) {
                                    mbucle = false;
                                    break;
                                }
                            case 3:
                                while (tmpbucle) { // Para que en caso de input string regrese al case 3
                                    log4j.info(str3);
                                    tmp = s.nextLine();
                                    if (tmp.equals("quit") || tmp.equals("exit")) {
                                        mbucle = false;
                                        break;
                                    }
                                    if (tmp.equals("return")) {
                                        main = 1;
                                        sm1 = 2;
                                        mjump = true;
                                        break;
                                    }
                                    try {
                                        elvl = Integer.parseInt(tmp);
                                        if (elvl > 0 && elvl < 101) {
                                            Eetakemon e = new Eetakemon(ename, etype, elvl, 53.3213, 5.892734); //Le añado una loc random porque si.
                                            Object etmp = Emanager.getInstance().addEetakemonToUserMap(usr, e);
                                            if (etmp == null) {
                                                log4j.info("Eetakemon añadido correctamente! No se ha sobreescrito ningun Eetakemon anterior.");
                                                tmpbucle = false;
                                                sm1 = 1;
                                                break;
                                            } else {
                                                Field f = etmp.getClass().getField("id");
                                                log4j.info("Eetakemon añadido correctamente! Se ha sobreescrito el Eetakemon: " + f.getInt(etmp));
                                                tmpbucle = false;
                                                sm2 = 1;
                                                break;
                                            }
                                        }
                                    }
                                    catch (NumberFormatException e) {
                                        log4j.warn("Debe ser un numero entre 0 y 100");
                                    }
                                }
                        } break;
                    case 2:
                        int i = showEetakemons(usr); // La propia funcion ya retorna sus propios int
                        if (i == -1){
                            mjump = true; // Es necesario colocarlo aqui porque mjump es una variable del main, no del la clase Main.
                            main = 1;
                        }
                        break;
                    case 3:
                        int addefrst = showEetakemons(usr);
                        if (addefrst == 1) {
                            while (tmpbucle) { // Para que en caso de input string regrese al case 3
                                log4j.info(str4);
                                tmp = s.nextLine();
                                if (tmp.equals("quit") || tmp.equals("exit")) {
                                    mbucle = false;
                                    break;
                                }
                                if (tmp.equals("return")) {
                                    break;
                                }
                                try {
                                    int rmid = Integer.parseInt(tmp);
                                    Eetakemon nulle = Emanager.getInstance().delEetakemonFromMap(usr, rmid);
                                    if (nulle != null) {
                                        log4j.info("Eetakemon borrado correctamente!");
                                        break;
                                    } else log4j.warn("No hay ningun Eetakemomn con esa id.");
                                } catch (NumberFormatException e) {
                                    log4j.warn("Debe ser un correspondiente a alguna id.");
                                }
                            }
                            break;
                        } else if (addefrst == -1) {
                            main = 1;
                            mjump = true;
                        }
                        break;
                    case 4:
                        if (!usr.emap.isEmpty()) {
                            while (tmpbucle) {
                                log4j.info(str11);
                                String enameaprox = s.nextLine();
                                if (enameaprox.equals("return")) {
                                    break;
                                }
                                if (enameaprox.equals("quit") || enameaprox.equals("exit")) {
                                    mbucle = false;
                                    break;
                                }
                                if (!enameaprox.trim().isEmpty()) {
                                    ArrayList<Eetakemon> elist = Emanager.getInstance().getEtakemonFromMapByNameAproximation(usr, enameaprox);
                                    if (elist.size() > 1) {
                                        log4j.info("Han habido varias coincidencias");
                                        for (Eetakemon e : elist) {
                                            log4j.info("id: " + e.id + " name: " + e.name + " type: " + e.type + " lvl: " + e.lvl + " loc: " + e.currentloc);
                                        }
                                        break;
                                    } else if (elist.size() == 1) {
                                        log4j.info("Ha habido una coincidencia: ");
                                        log4j.info("id: " + elist.get(0).id + " name: " + elist.get(0).name + " type: " + elist.get(0).type + " lvl: " + elist.get(0).lvl +  " loc: " + elist.get(0).currentloc.lat + "," + elist.get(0).currentloc.lon);
                                        break;
                                    } else if (elist.size() == 0) {
                                        log4j.warn("No ha habido ninguna coincidencia.");
                                        break;
                                    }
                                } else {
                                    log4j.warn("Escriba una cadena de caracteres.");
                                }
                            }
                        } else {
                            mjump = true;
                            main = 1;
                            log4j.warn("Primero añada un Eetakemon. Reedirigiendo al menu de creacion de Eetakemons...");
                        }
                        break;
                    case 5:
                        if (usr.emap.isEmpty()) {
                            log4j.info(str12);
                            try {
                                Emanager.getInstance().loadEetakemonsUsersMap(usr);
                                showEetakemons(usr);
                                log4j.info("Sus Eetakemons se cargaron con exito!");
                            } catch (FileNotFoundException e) {
                                log4j.fatal("No se encontro el fichero txt con los Eetakemons.");
                            } catch (JsonIOException e) {
                                log4j.fatal("Hubo algun problema al leer del reader.");
                            } catch (JsonSyntaxException e) {
                                log4j.fatal("Problema de sintaxis en el JSON");
                            }
                        } else {
                            log4j.warn("Ya tienes Eetakemons añadidos. Solo puede usar esta funcion con el HashMap vacio. Habria que añadir dinamicamente Eetakemons en el fichero Eetakemons.txt para acabarla de implementarla bien, y eso desestructurizaria el parseo del JSON.");
                        }
                        break;
                    case 6:
                        int addefrst2 = showEetakemons(usr);
                        if (addefrst2 == 1) {
                            while (tmpbucle) { // para que en caso de input string regrese al case 3
                                log4j.info("Inserte una nueva localizacion random a su Eetakemon mediante su id. Escriba return para volver a atras o escriba quit o exit para salir.");
                                tmp = s.nextLine();
                                if (tmp.equals("quit") || tmp.equals("exit")) {
                                    main = 1;
                                    sm1 = 1;
                                    break;
                                }
                                if (tmp.equals("return")) {
                                    break;
                                }
                                try {
                                    int rmid = Integer.parseInt(tmp);
                                    if (rmid > 0 && rmid <= usr.emap.size()) {
                                        Lmanager.getInstance().setEtakemonRandLocationByType(Emanager.getInstance().getEetakemonFromMap(usr, rmid));
                                        log4j.info("La nueva localizacion es lat: " + usr.emap.get(rmid).currentloc.lat + " lon: " + usr.emap.get(rmid).currentloc.lon);
                                        break;
                                    }
                                } catch (NumberFormatException e) {
                                    log4j.warn("Debe ser un correspondiente a alguna id.");
                                }
                            }
                            break;
                        } else if (addefrst2 == -1) mjump = true;
                        break;
                    case 7:
                        String uname = null;
                        String upassword = null;
                        String uemail = null;
                        switch (sm2) {
                            case 1:
                                log4j.info(str14);
                                uname = s.nextLine();
                                while (uname.length() <= 2 || uname.length() >= 16 || Character.isDigit(uname.charAt(0))) {
                                    log4j.info(str10);
                                    uname = s.nextLine();
                                }
                                if (uname.equals("return")) {
                                    break;
                                } else if (uname.equals("quit") || uname.equals("exit")) {
                                    mbucle = false;
                                    break;
                                }
                            case 2:
                                log4j.info(str13);
                                upassword = s.nextLine();
                                while (upassword.length() <= 2 || upassword.length() >= 16) {
                                    log4j.info(str17);
                                    upassword = s.nextLine();
                                }
                                if (upassword.equals("return")) {
                                    main = 7;
                                    sm2 = 1;
                                    mjump = true;
                                    break;
                                }
                                else if (upassword.equals("quit") || upassword.equals("exit")) {
                                    mbucle = false;
                                    break;
                                }
                            case 3:
                                log4j.info(str15);
                                uemail = s.nextLine();
                                if (uemail.equals("return")) {
                                    main = 7;
                                    sm2 = 2;
                                    mjump = true;
                                    break;
                                }
                                if (uemail.equals("quit") || uemail.equals("exit")) {
                                    mbucle = false;
                                    break;
                                }
                                while (uemail.length() <= 2 || uemail.length() >= 20 || !uemail.contains("@")) {
                                    log4j.warn("Introduzca un email valido.");
                                    uemail = s.nextLine();
                                }
                            case 4:
                                try {
                                    User u = new User(uname, upassword, uemail);
                                    Location loc = new Location(41.27514444, 1.984991667);
                                    Umanager.getInstance().setUserLoc(usr, u, loc);
                                    Object utmp = Umanager.getInstance().addUserToUsersMap(usr, u);
                                    if (utmp == null) {
                                        log4j.info("Usuario añadido correctamente! No se ha sobreescrito ningun usuario anterior.");
                                        log4j.info(str16);
                                        break;
                                    } else {
                                        Field f = utmp.getClass().getField("id");
                                        log4j.info("Usuario añadido correctamente! Se ha sobreescrito el Usuario: " + f.getInt(utmp));
                                        break;
                                    }
                                } catch (AdminException e) {
                                    log4j.fatal("Necesitas ser administrador para usar esta funcion.");
                                }
                        } break;
                    case 8:
                        try {
                            HashMap<Integer, User> tmphmap = Umanager.getInstance().showAllUsersInHashmap(usr);
                            if (tmphmap.isEmpty()) {
                                log4j.warn("Hashmap vacio. No se pudo devolver ningun user.");
                                mjump = true;
                                main = 7;
                                break;
                            }
                            else {
                                log4j.info("Lista de users disponibles:");
                                for (HashMap.Entry<Integer, User> entry : tmphmap.entrySet()) {
                                    log4j.info(entry.getValue().id + ": " + entry.getValue().username + ",  con email: " + entry.getValue().email);
                                } break;
                            }
                        } catch (AdminException e) {
                            log4j.fatal("Necesitas ser administrador para usar esta funcion.");
                        } break;
                    case 9:
                        while (tmpbucle) {
                            try {
                                HashMap<Integer, User> tmphmap = Umanager.getInstance().showAllUsersInHashmap(usr);
                                if (!tmphmap.isEmpty()) {
                                    log4j.info("Lista de users disponibles:");
                                    for (HashMap.Entry<Integer, User> entry : tmphmap.entrySet()) {
                                        log4j.info(entry.getValue().id + ": " + entry.getValue().username + ",  con email: " + entry.getValue().email);
                                    }
                                    log4j.info(str5);
                                    tmp = s.nextLine();
                                    if (tmp.equals("quit") || tmp.equals("exit")) {
                                        mbucle = false;
                                        break;
                                    }
                                    if (tmp.equals("return")) {
                                        break;
                                    }
                                    int rmid = Integer.parseInt(tmp);
                                    User nullu = Umanager.getInstance().delUserFromMap(usr, rmid);
                                    if (nullu != null) {
                                        log4j.info("User: " + rmid + " borrado correctamente!");
                                        break;
                                    } else {
                                        log4j.warn("No hay ningun user con esa id.");
                                        break;
                                    }
                                } else {
                                    log4j.warn("No ha users en el Hashmap. Primero añada un user.");
                                    mjump = true;
                                    main = 7;
                                    break;
                                }
                            } catch(NumberFormatException e){
                                log4j.info("Debe ser un correspondiente a alguna id.");
                            } catch(AdminException e){
                                log4j.fatal("Necesitas ser administrador para usar esta funcion.");
                                tmpbucle = false;
                            }
                        } break;
                    case 10:
                        while (tmpbucle) {
                            try {
                                HashMap<Integer, User> tmphmap = Umanager.getInstance().showAllUsersInHashmap(usr);
                                if (tmphmap.isEmpty()) {
                                    log4j.warn("Hashmap vacio. No se pudo borrar ningun user.");
                                    mjump = true;
                                    main = 7;
                                    break;
                                } else {
                                    log4j.info("Lista de users disponibles:");
                                    for (HashMap.Entry<Integer, User> entry : tmphmap.entrySet()) {
                                        log4j.info(entry.getValue().id + ": " + entry.getValue().username);
                                    }
                                    log4j.info("Seguro que desea borrarlos? Escriba Y o N. Tambien puede escribir return o exit para salir.");
                                    tmp = s.nextLine();
                                    while (!(tmp.equals("Y") || tmp.equals("N") || tmp.equals("exit") || tmp.equals("quit") || tmp.equals("return"))) {
                                        log4j.info(str8);
                                        tmp = s.nextLine();
                                    }
                                    switch (tmp) {
                                        case "return":
                                        case "N":
                                            tmpbucle = false;
                                            break;
                                        case "quit":
                                        case "exit":
                                            mbucle = false;
                                            break;
                                        default:
                                            Umanager.getInstance().delUsersFromMap(usr);
                                            log4j.info("Los usuarios se borraron correctamente.");
                                            tmpbucle = false;
                                            break;
                                    }
                                }
                            } catch (AdminException e) {
                                log4j.fatal("Necesitas ser administrador para usar esta funcion.");
                            } catch (NumberFormatException e) {
                                log4j.warn("Debe ser un correspondiente a alguna id.");
                            }
                        } break;
                    case 11:
                        while (tmpbucle) {
                            try {
                                HashMap<Integer, User> tmphmap = Umanager.getInstance().showAllUsersInHashmap(usr);
                                if (tmphmap.isEmpty()) {
                                    log4j.warn("Hashmap vacio. No se pudo devolver ningun user.");
                                    mjump = true;
                                    main = 7;
                                    break;
                                }
                                else {
                                    log4j.info("Lista de users disponibles:");
                                    for (HashMap.Entry<Integer, User> entry : tmphmap.entrySet()) {
                                        log4j.info(entry.getValue().id + ": " + entry.getValue().username);
                                    }
                                    log4j.info("Escriba el id del user que desea explorar: ");
                                    tmp = s.nextLine();
                                    if (tmp.equals("return")) {
                                        break;
                                    }
                                    if (tmp.equals("quit") || tmp.equals("exit")) {
                                        mbucle = false;
                                        break;
                                    }
                                    int getid = Integer.parseInt(tmp);
                                    User nullu = Umanager.getInstance().getUserFromMap(usr, getid);
                                    if (nullu != null) {
                                        log4j.info("id: "+nullu.id+" username: "+nullu.username+" password: "+nullu.password+" email: "+nullu.email+" current location: ("+nullu.currentloc.lat+","+nullu.currentloc.lat+") isadmin: "+nullu.isadmin);
                                        break;
                                    } else log4j.warn("No hay ningun user con esa id.");
                                }
                            } catch (AdminException e) {
                                log4j.fatal("Necesitas ser administrador para usar esta funcion.");
                            } catch (NumberFormatException e) {
                                log4j.warn("Debe ser un correspondiente a alguna id.");
                            }
                        } break;
                    case 12:
                        mbucle = false;
                        break;
                    default:
                        log4j.warn("El numero que ha escrito no corresponde a ninguna opcion.");
                }
            } catch (NumberFormatException e) {
                switch (tmp) {
                    case "quit":
                    case "exit":
                        log4j.info("Cerrando el programa...");
                        mbucle = false;
                        break;
                    case "return":
                        log4j.info("Ya esta en el menu principal.");
                        break;
                    default:
                        log4j.warn("Para seleccionar una opcion del menu debe escribir un numero.");
                        break;
                }
            } catch (NullPointerException e) {
                log4j.fatal("Fallo en el programa. Null pointer ex.");
            } catch (MissingResourceException e) {
                log4j.fatal("Fallo en el programa. No se le ha podido dar autenticacion. Usuario inexistente.");
            }
        }
    }
}
