package persistence;

import org.junit.jupiter.api.Test;
import utn.ddsG8.impacto_ambiental.domain.movilidad.Trayecto;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;

import java.util.List;

public class CalculadorHCTrayectosTest {

    private final static List<Trayecto> trayectoList = FactoryRepositorio.get(Trayecto.class).buscarTodos();

    @Test
    public void trayectosHC() {
        trayectoList.forEach(t -> {
            t.getTramos().forEach(tramo -> System.out.println(tramo.calcularHC()+ tramo.getTransporte().getNombreFE() + " ID " + tramo.getId()));

            System.out.println("El trayecto con id-" +t.getId()+ " HC = "  + t.CalcularHCTrayecto());
        });
    }
}
