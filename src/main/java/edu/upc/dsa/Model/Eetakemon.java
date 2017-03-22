package edu.upc.dsa.Model;

/**
 * Created by $uperuser on 27/02/2017.
 */
public class Eetakemon {
    public int id;
    public String nombre;
    public String tipo;
    public int nivel;

    public static int ultimoid = 1;

    public Eetakemon () {
        this.id = ultimoid;
        ultimoid++;
    }
    public Eetakemon (String nombre, String tipo, int nivel) {
        this.id = ultimoid;
        this.nombre = nombre;
        this.tipo = tipo;
        this.nivel = nivel;
        ultimoid++;
    }
}
