package persistence;

import utn.ddsG8.impacto_ambiental.domain.estructura.Direccion;
import utn.ddsG8.impacto_ambiental.domain.estructura.Miembro;
import utn.ddsG8.impacto_ambiental.domain.estructura.Sector;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.publico.Parada;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.SectorTerritorial;
import utn.ddsG8.impacto_ambiental.domain.helpers.UserHelper;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;

import java.util.ArrayList;
import java.util.List;

public class Random {

    public final static Repositorio<Direccion> direcciones = FactoryRepositorio.get(Direccion.class);
    public final static Repositorio<SectorTerritorial> sectores = FactoryRepositorio.get(SectorTerritorial.class);
    public final static Repositorio<Parada> paradas = FactoryRepositorio.get(Parada.class);
    public final static Repositorio<Miembro> repoMiembros = FactoryRepositorio.get(Miembro.class);
    public final static Repositorio<Sector> repoSectores = FactoryRepositorio.get(Sector.class);

    public static int numeroCalle() {
        return (int) Math.floor(Math.random() *(10000 - 1 + 1) + 1);
    }

    public static String nombreCalleEjemplo() {
        return "Calle" + intBetween(0, 10000);
    }

    public static String nombreUsuarioMiembro() {
        String nombre = "miembro"+ intBetween(0, 10000);
        while (UserHelper.usernameExists(nombre)) {
            nombre = "miembro"+ intBetween(0, 10000);
        }
        return nombre;
    }

    public static String nombreUsuarioOrg() {
        String nombre = "org"+ intBetween(0, 10000);
        while (UserHelper.usernameExists(nombre)) {
            nombre = "org"+ intBetween(0, 10000);
        }
        return nombre;
    }

    public static String nombreUsuarioAgente() {
        String nombre = "agente"+ intBetween(0, 10000);
        while (UserHelper.usernameExists(nombre)) {
            nombre = "agente"+ intBetween(0, 10000);
        }
        return nombre;
    }

    public static int intBetween(int min, int max) {
        return (int) Math.floor(Math.random() * (max - 1 + min) + min);
    }

    public static Direccion getRandomDireccion() {
        List<Direccion> direccionList = direcciones.buscarTodos();
        return direccionList.get(intBetween(0, direccionList.size()));
    }

    public static SectorTerritorial getRandomSectorTerritorial() {
        List<SectorTerritorial> sectoresList = sectores.buscarTodos();
        return sectoresList.get(intBetween(0, sectoresList.size()));
    }

    public static List<Direccion> direccionesDeParadas() {
        List<Parada> paradasList = paradas.buscarTodos();
        List<Direccion> direcciones  = new ArrayList<>();

        direcciones.add(paradasList.get(0).getDireccion());
        direcciones.add(paradasList.get(1).getDireccion());
        direcciones.add(paradasList.get(2).getDireccion());

        return direcciones;
    }

    public static Miembro getRandomMiembro() {
        List<Miembro> miembrosList = repoMiembros.buscarTodos();
        return miembrosList.get(intBetween(0, miembrosList.size()-1));
    }

    public static Sector getRandomSector() {
        List<Sector> sectoresList = repoSectores.buscarTodos();
        return sectoresList.get(Random.intBetween(0, sectoresList.size()));
    }
}
