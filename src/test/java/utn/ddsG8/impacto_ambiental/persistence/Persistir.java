package utn.ddsG8.impacto_ambiental.persistence;

import org.junit.jupiter.api.Test;
public class Persistir {

    @Test
    public void persistirDatos() {
        new PersistirDireccionesTest().persistirDirecciones();
        System.out.println("Direcciones persistidas");
        new PersistirFactoresEmision().persistirFactores();
        System.out.println("FactoresFE persistidos");
        new PersistirUsuarios().persistirUsuarios();
        System.out.println("Usuarios Persistidos");
        new PersistirSectores().persistirSectores();
        System.out.println("Sectores Persistidos");
        new PersistirTransportes().persistirPrivados();
        System.out.println("Transporte Privado Persitido");
        new PersistirTransportes().persistirPublicos();
        System.out.println("Transporte Publico Persitido");
        new ponerParadasEnDirecciones().test();
        System.out.println("Paradas en direcciones persitidas");
        PersistirTrayectosTest trayectosTest = new  PersistirTrayectosTest();
        trayectosTest.persistirTrayectos();
        System.out.println("Trayectos Persistidos");
        trayectosTest.agregarOrganizacionesATrayectos();
        System.out.println("Organizaciones agregadas a los trayectos");
        trayectosTest.agregarTramosNoContamientante();
        System.out.println("Trayectos completos con tramos no contaminantes");
    }

}
