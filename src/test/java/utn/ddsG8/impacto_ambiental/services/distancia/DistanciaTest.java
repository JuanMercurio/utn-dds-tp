package utn.ddsG8.impacto_ambiental.services.distancia;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.*;

import java.io.IOException;
import java.util.List;

// TODO: eliminar prints de este archivo despues de mostarle a pablo
public class DistanciaTest {
    private final String token = "Bearer E8iN6xBPXQsUI+M72MfPdVhM/o3axkzywqKZjjOyhe0=";

    @Test
    public void usuarioYaExiste() throws IOException {
        DistanciaServicio api = DistanciaServicio.getInstancia();
        UserRespuesta respuesta = api.crearUsuario(new UserCrear("mail@ejemplo.com"));
        Assertions.assertNull(respuesta);
    }

    @Test
    public void retornaPaises() throws IOException {
        DistanciaServicio api = DistanciaServicio.getInstancia();
        List<Pais> paises = api.paises(token, 1);
        System.out.println();
        System.out.println("Imprime paises recibidos");
        System.out.println();
        System.out.format("%-10S%-20s\n", "ID", "Pais");
        paises.forEach(p -> System.out.format("%-10S%-20s\n", p.id, p.nombre));
        System.out.println();
        Assertions.assertEquals("ARGENTINA", paises.get(0).nombre);
    }

    @Test
    public void retornaProvincias() throws IOException {
        DistanciaServicio api = DistanciaServicio.getInstancia();
        List<Provincia> provincias = api.provincias(token, 1);
        System.out.println();
        System.out.println("Imprime provincias recibidas");
        System.out.println();
        System.out.format("%-10S%-20s%-30s\n", "ID", "Provincia", "Pais-id");
        provincias.forEach(p -> System.out.format("%-10S%-20s%-30s\n", p.id, p.nombre, p.pais.id));
        System.out.println();
        Assertions.assertTrue(provincias.size() > 0);
    }

    @Test
    public void retornaProvinciasDePais() throws IOException {
        DistanciaServicio api = DistanciaServicio.getInstancia();
        List<Provincia> provincias = api.provincias(token, 1, 9);
        Assertions.assertEquals("BUENOS AIRES", provincias.get(0).nombre);
    }

    @Test
    public void retornaMunicipios() throws IOException {
        DistanciaServicio api = DistanciaServicio.getInstancia();
        List<Municipio> municipios = api.municipios(token, 10);
        System.out.println();
        System.out.println("Imprime municipios recibidos");
        System.out.println();
        System.out.format("%-40s%-10s%-20s%-10s\n", "Municipio", "ID", "Provincia", "ID");
        municipios.forEach(m -> System.out.format("%-40s%-10s%-20s%-10s\n" ,m.nombre, m.id, m.provincia.nombre, m.provincia.id));
        System.out.println();
        Assertions.assertTrue(municipios.size() > 0);
    }

    @Test
    public void retornaMunicipiosDeProvincia() throws IOException {
        DistanciaServicio api = DistanciaServicio.getInstancia();
        List<Municipio> municipios = api.municipios(token, 1, 171);
        Assertions.assertEquals("RAWSON ", municipios.get(0).nombre);
    }

    @Test
    public void retornaLocalidades() throws IOException {
        DistanciaServicio api = DistanciaServicio.getInstancia();
        List<Localidad> localidades = api.localidades(token, 1);
        System.out.println();
        System.out.println("Imprime localidades y municipios obtenidos");
        System.out.println();
        System.out.format("%-25s%-10s%-12s%-18s%-10s\n", "Localidad", "ID Loca", "CodPostal", "Municipio", "ID Muni");
        for (Localidad unaLocalidad: localidades) {
            System.out.format("%-25s%-10s%-12s%-18s%-10s\n",
                    unaLocalidad.nombre,
                    unaLocalidad.id,
                    unaLocalidad.codPostal,
                    unaLocalidad.municipio.nombre,
                    unaLocalidad.municipio.id
                    );
        }
        System.out.println();
        Assertions.assertTrue(localidades.size() > 0);
    }

    @Test
    public void retornaLocalidadesDeMunicipio() throws IOException {
        DistanciaServicio api = DistanciaServicio.getInstancia();
        List<Localidad> localidades = api.localidades(token, 1, 9);
        Assertions.assertEquals("ALDEA BRASILERA ", localidades.get(0).nombre);
    }

    @Test
    public void retornaUnaDistancia() throws IOException {
        DistanciaServicio api = DistanciaServicio.getInstancia();
        Distancia distancia = api.distancia(token,
                1,
                "maipu",
                "100",
                457,
                "O'Higgins",
                "200");
        System.out.println(distancia.valor);
        Assertions.assertTrue(distancia.valor > 0);
        Assertions.assertEquals("KM", distancia.unidad);

    }
}
