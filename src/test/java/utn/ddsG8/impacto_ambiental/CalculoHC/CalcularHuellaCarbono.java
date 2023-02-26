package utn.ddsG8.impacto_ambiental.CalculoHC;


import org.junit.jupiter.api.Test;

import utn.ddsG8.impacto_ambiental.domain.Notificaciones.Contacto;
import utn.ddsG8.impacto_ambiental.domain.estructura.*;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.Localidad;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class CalcularHuellaCarbono {

    private final Repositorio<Organizacion> repoOrganizacion = FactoryRepositorio.get(Organizacion.class);
    private final Repositorio<Localidad> repoLocalidad = FactoryRepositorio.get(Localidad.class);

    @Test
    public void test() throws IOException {
//        Writer output;
//        output = new BufferedWriter(new FileWriter("localidades.txt", true));

        FileWriter fw = new FileWriter("src/main/resources/localidades.txt", true); // Set second parameter to true for append mode

        List<Localidad> localidadList = repoLocalidad.buscarTodos();
        localidadList.forEach(l -> {
            try {
                fw.write(l.getNombre() + "\n"); // Add the new line to the end of the file
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
//
        fw.close();
//
//
    }
}