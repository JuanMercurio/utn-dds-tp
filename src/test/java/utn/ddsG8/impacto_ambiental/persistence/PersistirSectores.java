package utn.ddsG8.impacto_ambiental.persistence;

import utn.ddsG8.impacto_ambiental.domain.estructura.Miembro;
import utn.ddsG8.impacto_ambiental.domain.estructura.Organizacion;
import utn.ddsG8.impacto_ambiental.domain.estructura.Sector;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PersistirSectores {

    public final Repositorio<Miembro> repoMiembro = FactoryRepositorio.get(Miembro.class);
    public final Repositorio<Organizacion> repoOrg = FactoryRepositorio.get(Organizacion.class);

    public void persistirSectores() {
        List<Organizacion> orgs = repoOrg.buscarTodos();
        for (Organizacion org : orgs) {
            crearSectores(org, 3);
            for (Sector sec : org.getSectores()) {
                sec.agregarMiembro(Random.getRandomMiembro());
            }
        }

        orgs.forEach(o -> repoOrg.modificar(o));
        arreglarMiembros();
    }

    public void crearSectores(Organizacion o, int cantidad) {
        List<Sector> sectores = new ArrayList<>();
        for (int i = 0; i<cantidad; i++) {
            o.getSectores().add(new Sector("Sector " + (i+1), o));
        }
    }

    public void arreglarMiembros() {
        List<Miembro> miembros = repoMiembro.buscarTodos().stream().filter(m -> m.getSectores().size() == 0).collect(Collectors.toList());
        if (miembros.size() == 0) return;
        miembros.forEach(m -> m.agregarSector(Random.getRandomSector()));
        miembros.forEach(m -> repoMiembro.modificar(m));
    }
}
