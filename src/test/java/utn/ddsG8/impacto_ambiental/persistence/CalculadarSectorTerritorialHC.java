package utn.ddsG8.impacto_ambiental.persistence;

import org.junit.jupiter.api.Test;
import utn.ddsG8.impacto_ambiental.domain.calculos.CalcularHC;
import utn.ddsG8.impacto_ambiental.domain.estructura.Direccion;
import utn.ddsG8.impacto_ambiental.domain.movilidad.Trayecto;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.SectorTerritorial;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;

import java.util.List;
import java.util.stream.Collectors;

public class CalculadarSectorTerritorialHC {

    private Repositorio<Direccion> repoDireccion = FactoryRepositorio.get(Direccion.class);
    private Repositorio<Trayecto> repoTrayecto = FactoryRepositorio.get(Trayecto.class);
    private Repositorio<SectorTerritorial> repoSectorTerritorial = FactoryRepositorio.get(SectorTerritorial.class);

    @Test
    public void calcularHC() {
        List<SectorTerritorial> sectores = repoSectorTerritorial.buscarTodos();
        sectores.forEach(t -> System.out.println(CalcularHC.getInstancia().obtenerHCSectorTerritorial(t) + t.nombre));
    }
}
