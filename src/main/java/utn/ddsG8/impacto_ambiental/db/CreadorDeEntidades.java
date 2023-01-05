package utn.ddsG8.impacto_ambiental.db;

import utn.ddsG8.impacto_ambiental.domain.estructura.Miembro;
import utn.ddsG8.impacto_ambiental.domain.estructura.TipoDoc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class CreadorDeEntidades {

    private static final String nombresRuta = "Persistencia/names.txt";
    private static final String apellidosRuta = "Persistencia/surnames.txt";
    private static List<String> nombres;
    private static List<String> apellidos;
    private static int cantidad;


    public static List<Miembro> crearMiembros(int numero) {
        cantidad = numero;
        List<Miembro> miembrosNuevos = new ArrayList<Miembro>();
        Scanner nombresArchivo = new Scanner(nombresRuta);
        Scanner apellidosArchivo = new Scanner(apellidosRuta);

        nombres = pasarALista(nombresRuta);
        apellidos = pasarALista(apellidosRuta);

        for (int i=0; i<cantidad; i++) {
            Miembro miembro = crearMiembroRandom();
        }

        return miembrosNuevos;
    }

    private static Miembro crearMiembroRandom() {
        String nombre = nombres.get(getRandomNumberInRange(1, cantidad));
        String apellido = apellidos.get(getRandomNumberInRange(1, cantidad));
        Miembro miembro = new Miembro(nombre, apellido, TipoDoc.DNI, String.valueOf(getRandomNumberInRange(300000, 70000000)));
        return miembro;
    }


    private static List<String> pasarALista(String path) {

        List<String> lista = new ArrayList<>();
        File myFile = new File(path) ;
        Scanner myReader = null;
        try {
            myReader = new Scanner(myFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while(myReader.hasNextLine()){
            String pass = myReader.nextLine();
            lista.add(pass);
        }

        return lista;
    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

}
