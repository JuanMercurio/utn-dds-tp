package persistence;

import org.junit.jupiter.api.Test;
import utn.ddsG8.impacto_ambiental.domain.estructura.Organizacion;
import utn.ddsG8.impacto_ambiental.domain.estructura.Sector;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;

import java.util.ArrayList;
import java.util.List;

public class PersistirSectores {

    public final Repositorio<Organizacion> repoOrg = FactoryRepositorio.get(Organizacion.class);
    public final Repositorio<Sector> repoSector = FactoryRepositorio.get(Sector.class);

    @Test
    public void persistirSectores() {
        List<Organizacion> orgs = repoOrg.buscarTodos();
        orgs.forEach(o -> {
            this.crearSectores(o, 3);
            o.getSectores().forEach(s -> s.agregarMiembro(Random.getRandomMiembro()));
        });

        orgs.forEach(o -> repoOrg.modificar(o));
    }

    public List<Sector> crearSectores(Organizacion o, int cantidad) {

        List<Sector> sectores = new ArrayList<>();
        for (int i = 0; i<cantidad; i++ ) {
            sectores.add(new Sector("Sector " + String.valueOf(cantidad), o));
        }

        return sectores;
    }
}
