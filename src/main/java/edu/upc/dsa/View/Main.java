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
            System.out.println("Lista de Eetakemons disponibles:");
            for (HashMap.Entry<Integer, Eetakemon> entry : u.emap.entrySet()) {
                System.out.println(entry.getValue().id + ": " + entry.getValue().name + ", lvl " + entry.getValue().lvl + entry.getValue().currentloc.lat + + entry.getValue().currentloc.lon);
            } return 1;
        }
        else {
            System.out.println("Primero añada un Eetakemon. Reedirigiendo al menu de creacion de Eetakemons...");
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
        sb.append("8. Listar todos los usuarios del Hashmap\n");
        sb.append("9. Salir.\n");

        final String str = ("Menu de creacion de Eetakemons. Escriba quit o exit para salir. Escriba return para volver al menu principal.\n1. Introduzca el nombre del Eetakemon (Debe contener mas de 3 a 15 caracteres y no empezar por un numero):");
        final String str2 = ("2. Introduzca de que type es (\"Fuego\", \"Tierra\" o \"Dragon\"):");
        final String str3 = ("3. Introduzca el lvl (0-100):");
        final String str4 = ("Introduzca el id del Eetakemon quiere borrar. Escriba quit o exit para salir. Escriba return para volver al menu principal.");
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
            boolean bucle2 = true;     // Cada vez k recorran el bucle, se inicializen a true (k entren en todos los bucles, estas variables las utilizo para salirme de ellos)
            boolean sm1bucle = true;
            boolean sm2bucle = true;
            boolean bucle4 = true;
            boolean bucle5 = true;
            String tmp = null;                                          //La reutilizo cuando la necesito
            try {
                while(successful == -1) {
                    System.out.print(sb2);
                    String tryname = s.nextLine();
                    if (tryname.equals("quit") || tryname.equals("exit")) {
                        main = 8;
                        mjump = true;
                        break;
                    }
                    System.out.print(str13);
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
                        log4j.info("No se ha podido encontrar un user con esa password."+tryname);
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
                        String ename = null; //Las necesito inicializadas a null xk luego habra un try que me las pida en el case 3
                        String etype = null; //No reutilizo ciertas variables por legibilidad
                        int elvl;
                        while (sm1bucle) {
                            switch (sm1) {
                                case 1:
                                    System.out.println(str);
                                    ename = s.nextLine();
                                    while (ename.length() <= 2 || ename.length() >= 16 || Character.isDigit(ename.charAt(0))) {
                                        System.out.println(str10);
                                        ename = s.nextLine();
                                    }
                                    if (ename.equals("return")) {
                                        sm1bucle = false; // para salir del bucle
                                        main = 9;
                                        break;
                                    }
                                    if (ename.equals("quit") || ename.equals("exit")) {
                                        sm1bucle = false;
                                        main = 9;
                                        break;
                                    }
                                case 2:
                                    System.out.println(str2);
                                    etype = s.nextLine();
                                    while (!(etype.equals("Fuego") || etype.equals("Tierra") || etype.equals("Dragon") || etype.equals("exit") || etype.equals("quit") || etype.equals("return"))) {
                                        System.out.println(str8);
                                        etype = s.nextLine();
                                    }
                                    if (etype.equals("return")) {
                                        main = 1;
                                        sm1 = 1;
                                        mjump = true;
                                        break;
                                    }
                                    if (etype.equals("quit") || etype.equals("exit")) {
                                        sm1bucle = false;
                                        mbucle = false;
                                        break;
                                    }
                                case 3:
                                    while (bucle2) { // Para que en caso de input string regrese al case 3
                                        System.out.println(str3);
                                        tmp = s.nextLine();
                                        if (tmp.equals("quit") || tmp.equals("exit")) {
                                            sm1bucle = false;
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
                                                Eetakemon e = new Eetakemon(ename, etype, elvl);
                                                Object etmp = Emanager.getInstance().addEetakemonToUserMap(usr, e);
                                                if (etmp == null) {
                                                    log4j.info("Eetakemon añadido correctamente! No se ha sobreescrito ningun Eetakemon anterior.");
                                                    sm1bucle = false;
                                                    sm1 = 1;
                                                    break;
                                                } else {
                                                    Field f = etmp.getClass().getField("id");
                                                    log4j.info("Eetakemon añadido correctamente! Se ha sobreescrito el Eetakemon: " + f.getInt(etmp));
                                                    sm1bucle = false;
                                                    sm2 = 1;
                                                    break;
                                                }
                                            }
                                        }
                                        catch (NumberFormatException e) {
                                            log4j.info("Debe ser un numero entre 0 y 100");
                                        }
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
                            while (bucle4) { // Para que en caso de input string regrese al case 3
                                System.out.println(str4);
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
                                    } else log4j.info("No hay ningun Eetakemomn con esa id.");
                                } catch (NumberFormatException e) {
                                    log4j.info("Debe ser un correspondiente a alguna id.");
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
                            while (bucle5) {
                                System.out.println(str11);
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
                                            System.out.println("id: " + e.id + " name: " + e.name + " type: " + e.type + " lvl: " + e.lvl + " loc: " + e.currentloc);
                                        }
                                        break;
                                    } else if (elist.size() == 1) {
                                        log4j.info("Ha habido una coincidencia: ");
                                        System.out.println("id: " + elist.get(0).id + " name: " + elist.get(0).name + " type: " + elist.get(0).type + " lvl: " + elist.get(0).lvl +  " loc: " + elist.get(0).currentloc.lat + "," + elist.get(0).currentloc.lon);
                                        break;
                                    } else if (elist.size() == 0) {
                                        log4j.info("No ha habido ninguna coincidencia.");
                                        break;
                                    }
                                } else {
                                    log4j.info("Escriba una cadena de caracteres.");
                                }
                            }
                        } else {
                            mjump = true;
                            main = 1;
                            log4j.info("Primero añada un Eetakemon. Reedirigiendo al menu de creacion de Eetakemons...");
                        }
                        break;
                    case 5:
                        if (usr.emap.isEmpty()) {
                            System.out.println(str12);
                            try {
                                Emanager.getInstance().loadEetakemonsMap(usr);
                                showEetakemons(usr);
                                log4j.info("Sus Eetakemons se cargaron con exito!");
                            } catch (FileNotFoundException e) {
                                log4j.warn("No se encontro el fichero txt con los Eetakemons.");
                            } catch (JsonIOException e) {
                                log4j.warn("Hubo algun problema al leer del reader.");
                            } catch (JsonSyntaxException e) {
                                log4j.warn("Problema de sintaxis en el JSON");
                            }
                        } else {
                            log4j.info("Ya tienes Eetakemons añadidos. Solo puede usar esta funcion con el HashMap vacio. Habria que añadir dinamicamente Eetakemons en el fichero Eetakemons.txt para acabarla de implementarla bien, y eso desestructurizaria el parseo del JSON.");
                        }
                        break;
                    case 6:
                        int addefrst2 = showEetakemons(usr);
                        if (addefrst2 == 1) {
                            while (bucle4) { // para que en caso de input string regrese al case 3
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
                                    log4j.info("Debe ser un correspondiente a alguna id.");
                                }
                            }
                            break;
                        } else if (addefrst2 == -1) mjump = true;
                        break;
                    case 7:
                        String uname = null;
                        String upassword = null;
                        String uemail = null;
                        boolean goto2bis = false;
                        while (sm2bucle) {
                            switch (sm2) {
                                case 1:
                                    System.out.println(str14);
                                    uname = s.nextLine();
                                    while (uname.length() <= 2 || uname.length() >= 16 || Character.isDigit(uname.charAt(0))) {
                                        System.out.println(str10);
                                        uname = s.nextLine();
                                    }
                                    if (uname.equals("return")) {
                                        sm2bucle = false;
                                        break;
                                    } else if (uname.equals("quit") || uname.equals("exit")) {
                                        sm2bucle = false;
                                        mbucle = false;
                                        break;
                                    }
                                case 2:
                                    System.out.println(str13);
                                    upassword = s.nextLine();
                                    while (upassword.length() <= 2 || upassword.length() >= 16) {
                                        System.out.println(str17);
                                        upassword = s.nextLine();
                                    }
                                    if (upassword.equals("return")) {
                                        sm2bucle = false;
                                        main = 7;
                                        sm2 = 1;
                                        mjump = true;
                                        break;
                                    }
                                    else if (upassword.equals("quit") || upassword.equals("exit")) {
                                        sm2bucle = false;
                                        mbucle = false;
                                        break;
                                    }
                                case 3:
                                    System.out.println(str15);
                                    uemail = s.nextLine();
                                    if (uemail.equals("return")) {
                                        sm1bucle = false;
                                        main = 7;
                                        sm2 = 2;
                                        mjump = true;
                                        break;
                                    }
                                    if (uemail.equals("quit") || uemail.equals("exit")) {
                                        sm2bucle = false;
                                        mbucle = false;
                                        break;
                                    }
                                    while (uemail.length() <= 2 || uemail.length() >= 20 || !uemail.contains("@")) {
                                        System.out.println("Introduzca un email valido.");
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
                                            System.out.println(str16);
                                            sm2bucle = false;
                                            sm1bucle = false;
                                            break;
                                        } else if (!(Boolean)utmp) {
                                            u = null;
                                            User.lastid--;
                                            log4j.info("Usuario existente. No se pudo añadir al usuario.");
                                            break;
                                        }
                                        else {
                                            Field f = utmp.getClass().getField("id");
                                            log4j.info("Usuario añadido correctamente! Se ha sobreescrito el Usuario: " + f.getInt(utmp));
                                            break;
                                        }
                                    } catch (AdminException e) {
                                        log4j.info("Necesitas ser administrador para usar esta funcion.");
                                    }
                            } break;
                        }
                        break;
                    case 8:
                        HashMap<Integer, User> tmphmap = Umanager.getInstance().showAllUsersInHashmap(usr);
                        if (tmphmap.isEmpty()) {
                            log4j.warn("Hashmap vacio. No se pudo devolver ningun user.");
                            sm1bucle = false;
                            mjump = true;
                            main = 7;
                            break;
                        }
                        else {
                            log4j.info("Lista de Eetakemons disponibles:");
                            for (HashMap.Entry<Integer, User> entry : tmphmap.entrySet()) {
                                log4j.info(entry.getValue().id + ": " + entry.getValue().username + ",  con email: " + entry.getValue().email);
                            }
                            break;
                        }
                    case 9:
                        mbucle = false;
                        break;
                    default:
                        log4j.info("El numero que ha escrito no corresponde a ninguna opcion.");
                }
            } catch (NumberFormatException e) {
                if (tmp.equals("quit") || tmp.equals("exit")) {
                    log4j.info("Cerrando el programa.");
                    mbucle = false;
                }
                if (tmp.equals("return")) {
                    log4j.info("Ya esta en el menu principal.");
                }
                if (!(tmp.equals("quit") || tmp.equals("exit") || (tmp.equals("return")))) {
                    log4j.info("Para seleccionar una opcion del menu debe escribir un numero.");
                }
            } catch (NullPointerException e) {
                log4j.warn("Fallo en el programa. Null pointer ex.");
            } catch (MissingResourceException e) {
                log4j.info("Fallo en el programa. No se le ha podido dar autenticacion. Usuario inexistente.");
            }
        }
    }
}
