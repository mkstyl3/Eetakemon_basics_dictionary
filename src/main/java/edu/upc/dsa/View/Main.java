package edu.upc.dsa.View;

/**
 * Created by $uperuser on 27/02/2017.
 */

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import edu.upc.dsa.Controller.Emanager;
import edu.upc.dsa.Controller.Umanager;
import edu.upc.dsa.Model.Eetakemon;
import edu.upc.dsa.Model.User;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

//               La intencionalidad de este programa es aprender jugando con las funciones de clase de EetakemonGo //
//               creando una interfaz interactiva de consola capaz de hacer un return a la opcion anterior y       //
//               cerrarse escribiendo exit o quit. Podria utilizarse para conectar con la base de datos en un      //
//               futuro (aunque es improbable xD).                                                                 //
//                                                                                                                 //
public class Main {

    private static final Logger log4j = Logger.getLogger(Main.class);
    private static int showEetakemons(User u) {
        if(!u.emap.isEmpty()) {
            System.out.println("Lista de Eetakemons disponibles:");
            for (HashMap.Entry<Integer, Eetakemon> entry : u.emap.entrySet()) {
                System.out.println(entry.getValue().id + ": " + entry.getValue().nombre + ", lvl " + entry.getValue().nivel);
            } return 1;
        }
        else {
            System.out.println("Primero añada un Eetakemon");
            return -1;
        }
    }
    public static void main(String[] args) {


        Scanner s = new Scanner(System.in);
        User usr = new User(); // Habria que hacer un login para diferenciar entre users...
        boolean mbucle = true;
        boolean mjump = false;
        boolean successful = false;

        final StringBuffer sb = new StringBuffer("Menu principal. Escriba quit o exit para salir. Escriba return para volver a este menu.\n");
        sb.append("1. Añada un Eetakemon.\n");
        sb.append("2. Vea todos sus Eetakemons.\n");
        sb.append("3. Borre un Eetakemon.\n");
        sb.append("4. Explore un Eetakemon (tambien por aproximacion).\n");
        sb.append("5. Cargue 5 Eetakemons para pruebas (Solo se puede usar con tu HashMap de Eetakemons vacio).\n");
        sb.append("6. Salir.\n");

        final String str = ("Menu de creacion de Eetakemons. Escriba quit o exit para salir. Escriba return para volver al menu principal.\n1. Introduzca el nombre del Eetakemon (Debe contener mas de 3 a 15 caracteres y no empezar por un numero):");
        final String str2 = ("2. Introduzca de que tipo es (\"Fuego\", \"Tierra\" o \"Dragon\"):");
        final String str3 = ("3. Introduzca el nivel (0-100):");
        final String str4 = ("Introduzca el id del Eetakemon quiere borrar. Escriba quit o exit para salir. Escriba return para volver al menu principal.");
        final String str8 = ("Tiene que introducir un tipo valido (\"Fuego\", \"Tierra\" o \"Dragon\"):");
        final String str10 = ("Tiene que introducir un nombre valido (Debe contener mas de 3 a 15 caracteres y no empezar por un numero):");
        final String str11 = ("Introduzca el nombre del Eetakemon que quiere explorar o una aproximacion para realizar la busqueda. Escriba quit o exit para salir. Escriba return para volver al menu principal.");
        final String str12 = ("Cargando sus Eetakemons... Escriba quit o exit para salir. Escriba return para volver al menu principal.");
        final StringBuffer sb2 = new StringBuffer("Bienvenido a su gestor de Eetakemons! Para entrar escriba sus credenciales o escriba exit para salir: \n");
        sb2.append("Escriba su username: ");
        final String str13 = ("Escriba su password: ");





            while (mbucle) {
                boolean bucle2 = true;
                boolean sm1bucle = true;
                boolean bucle4 = true;
                boolean bucle5 = true;
                String tmp = null;
                int main = 1;
                String trypw = null;

                try {
                   while(!successful) {
                       System.out.print(sb2);
                       String tryname = s.nextLine();
                       if (tryname.equals("quit") || tryname.equals("exit")) {
                           main = 6;
                           mjump = true;
                           break;
                       }
                       System.out.print(str13);
                       trypw = s.nextLine();
                       if (trypw.equals("quit") || trypw.equals("exit")) {
                           main = 6;
                           mjump = true;
                           break;
                       }
                       successful = Umanager.getInstance().usrAuthentication(tryname, trypw);

                       if (successful) {
                           successful = true;
                           log4j.info("Access granted!");
                       } else log4j.info("Access denied.");
                   }

                    if (!mjump) {
                        System.out.print(sb);
                        tmp = s.nextLine();
                        main = Integer.parseInt(tmp);
                    }
                    mjump = false;
                    switch (main) {
                        case 1:
                            int sm1 = 1;
                            String ename = null;
                            String etype = null;
                            int elvl;
                            String tmp2 = null;
                            String enameaprox = null;
                            boolean goto2 = false;
                            while (sm1bucle) {
                                switch (sm1) {
                                    case 1:
                                        if (!goto2) {
                                            System.out.println(str);
                                            ename = s.nextLine();
                                            while (ename.length() <= 2 || ename.length() >= 16 || Character.isDigit(ename.charAt(0))) {
                                                System.out.println(str10);
                                                ename = s.nextLine();
                                            }
                                            if (ename.equals("return")) {
                                                sm1bucle = false;
                                                mjump = false;
                                                break;
                                            }
                                            if (ename.equals("quit") || ename.equals("exit")) {
                                                sm1bucle = false;
                                                break;
                                            }
                                        }
                                    case 2:
                                        System.out.println(str2);
                                        etype = s.nextLine();
                                        while (!(etype.equals("Fuego") || etype.equals("Tierra") || etype.equals("Dragon") || etype.equals("exit") || etype.equals("quit") || etype.equals("return"))) {
                                            System.out.println(str8);
                                            etype = s.nextLine();
                                        }
                                        if (etype.equals("return")) {
                                            goto2 = false;
                                            sm1 = 1;
                                            mjump = false;
                                            break;
                                        }
                                        if (etype.equals("quit") || etype.equals("exit")) {
                                            sm1bucle = false;
                                            break;
                                        }
                                    case 3:
                                        while (bucle2) { // para que en caso de input string regrese al case 3
                                            System.out.println(str3);
                                            tmp2 = s.nextLine();

                                            if (tmp2.equals("quit") || tmp2.equals("exit")) {
                                                sm1bucle = false;
                                                break;
                                            }
                                            if (tmp2.equals("return")) {
                                                goto2 = true;
                                                mjump = false;
                                                break;
                                            }
                                            try {
                                                elvl = Integer.parseInt(tmp2);
                                                if (elvl > 0 && elvl < 101) {
                                                    Eetakemon e = new Eetakemon(ename, etype, elvl);
                                                    Eetakemon etmp = Emanager.getInstance().addEetakemonToUserMap(usr, e);
                                                    if (etmp == null) {
                                                        log4j.info("Eetakemon añadido correctamente! No se ha sobreescrito ningun Eetakemon anterior.");
                                                        sm1bucle = false;
                                                        break;
                                                    } else {
                                                        log4j.info("Eetakemon añadido correctamente! Se ha sobreescrito el Eetakemon: " + etmp.id);
                                                        sm1bucle = false;
                                                        break;
                                                    }
                                                }
                                            } catch (NumberFormatException e) {
                                                log4j.info("Debe ser un numero entre 0 y 100");
                                            }
                                        }
                                }
                            }
                            if (ename.equals("return")) {
                                log4j.info("Volviendo al menu principal...");
                                break;
                            }
                            if (ename.equals("quit") || ename.equals("exit") || etype.equals("quit") || etype.equals("exit") || tmp2.equals("quit") || tmp2.equals("exit")) {
                                log4j.info("Cerrando el programa.");
                                mbucle = false;
                                break;
                            }
                            if (etype.equals("return")) {
                                log4j.info("Volviendo al menu principal...");
                                break;
                            }
                            if (tmp2.equals("return")) {
                                log4j.info("Volviendo al menu principal...");
                                break;
                            }
                            break;
                        case 2:
                            int i = showEetakemons(usr);
                            if (i == -1) mjump = true;
                            break;
                        case 3:
                            int addefrst = showEetakemons(usr);
                            if (addefrst == 1) {
                                String tmp3;
                                while (bucle4) { // para que en caso de input string regrese al case 3
                                    System.out.println(str4);
                                    tmp3 = s.nextLine();
                                    if (tmp3.equals("quit") || tmp3.equals("exit")) {
                                        mbucle = false;
                                        break;
                                    }
                                    if (tmp3.equals("return")) {
                                        break;
                                    }
                                    try {
                                        int rmid = Integer.parseInt(tmp3);
                                        Eetakemon nulle = Emanager.getInstance().delEetakemonFromMap(usr, rmid);
                                        if (nulle != null) {
                                            log4j.info("Eetakemon borrado correctamente!");
                                            break;
                                        } else log4j.info("No hay ningun Eetakemomn con esa id.");
                                    } catch (NumberFormatException e) {
                                        log4j.info("Debe ser un entero correspondiente a alguna id.");
                                    }
                                }
                                break;
                            } else if (addefrst == -1) mjump = true;
                            break;
                        case 4:
                            if (!usr.emap.isEmpty()) {
                                while (bucle5) {
                                    System.out.println(str11);
                                    enameaprox = s.nextLine();
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
                                                System.out.println("id: " + e.id + " nombre: " + e.nombre + " tipo: " + e.tipo + " nivel: " + e.nivel);
                                            }
                                            break;
                                        } else if (elist.size() == 1) {
                                            log4j.info("Ha habido una coincidencia: ");
                                            System.out.println("id: " + elist.get(0).id + " nombre: " + elist.get(0).nombre + " tipo: " + elist.get(0).tipo + " nivel: " + elist.get(0).nivel);
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
                                log4j.info("Primero introduzca un Eetakemon!");
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
                    log4j.warn("Fallo en el programa. No se le ha podido dar autenticacion. DB vacia.");
                } catch (MissingResourceException e) {
                    log4j.info("Fallo en el programa. No se le ha podido dar autenticacion. Usuario inexistente.");
                }

            }

    }
}
