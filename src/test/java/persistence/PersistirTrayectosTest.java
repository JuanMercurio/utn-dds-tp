package persistence;


import org.junit.jupiter.api.Test;
import utn.ddsG8.impacto_ambiental.domain.estructura.Miembro;
import utn.ddsG8.impacto_ambiental.domain.estructura.Organizacion;
import utn.ddsG8.impacto_ambiental.domain.movilidad.Tramo;

import utn.ddsG8.impacto_ambiental.domain.movilidad.Trayecto;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.TransporteNoContaminante;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.VehiculoParticular;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.publico.TransportePublico;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PersistirTrayectosTest {

    public final Repositorio<Trayecto> repoTrayectos = FactoryRepositorio.get(Trayecto.class);
    public final Repositorio<Tramo> repoTramos = FactoryRepositorio.get(Tramo.class);
    public final Repositorio<Miembro> repoMiembro = FactoryRepositorio.get(Miembro.class);
    public final Repositorio<VehiculoParticular> repoParticular = FactoryRepositorio.get(VehiculoParticular.class);
    public final Repositorio<TransportePublico> repoTransportePublico = FactoryRepositorio.get(TransportePublico.class);
    public final List<TransporteNoContaminante> repoNoContaminante = FactoryRepositorio.get(TransporteNoContaminante.class).buscarTodos();


    @Test
    public void persistirTrayectos() {

        List<Miembro> miembros = repoMiembro.buscarTodos();

        miembros.forEach(m -> {
            Trayecto trayecto = new Trayecto();
            trayecto.agregarMiembro(m);
            m.agregarATrayecto(trayecto);
            this.crearRandomTramosPublicos(trayecto,3).forEach(t -> trayecto.agregarTramo(t));
            this.crearRandomTramosVehiculoParticular(trayecto, m).forEach(t -> trayecto.agregarTramo(t));;
            repoTrayectos.agregar(trayecto);
        });

        miembros.forEach(m -> repoMiembro.modificar(m));

    }

    public List<Tramo> crearRandomTramosPublicos(Trayecto trayecto, int cantidadTramos) {
        List<TransportePublico> transportePublicoList = repoTransportePublico.buscarTodos();
        List<Tramo> tramos = new ArrayList<>();

        for (int i=0; i < cantidadTramos; i++) {
            Tramo tramo = new Tramo(transportePublicoList.get(Random.intBetween(0, transportePublicoList.size())), Random.direccionesDeParadas().get(0), Random.direccionesDeParadas().get(2));
            tramos.add(tramo);
        }

        tramos.forEach(t -> trayecto.agregarTramo(t));

        return tramos;
    }

    public List<Tramo> crearRandomTramosVehiculoParticular(Trayecto trayecto, Miembro miembro) {
        List<VehiculoParticular> transportes = repoParticular.buscarTodos().stream().filter(t -> t.getDuenio() == miembro).collect(Collectors.toList());
        List<Tramo> tramos = new ArrayList<>();
        transportes.forEach(t -> {
            Tramo tramo = new Tramo(t, Random.getRandomDireccion(), Random.getRandomDireccion());
            tramos.add(tramo);
        });

        return tramos;
    }

    @Test
    public void agregarOrganizacionesATrayectos() {
        List<Miembro> miembrosList = repoMiembro.buscarTodos();
        miembrosList.forEach(m -> {
            m.getTrayectos().forEach(t -> {
                Organizacion org = m.getSectores().get(Random.intBetween(0, m.getSectores().size())).getOrganizacion();
                t.agregarOrganizacion(org);
            });
        });

        miembrosList.forEach(m -> {
            repoMiembro.modificar(m);
        });

    }


    @Test
    public void arreglarMiembros() {
        List<Miembro> miembros = repoMiembro.buscarTodos().stream().filter(m -> m.getTrayectos().size() == 0).collect(Collectors.toList());
        System.out.println(miembros.size());
//        miembros.forEach(m -> m.agregarSector(Random.getRandomSector()));
//        miembros.forEach(m -> repoMiembro.modificar(m));
    }

    @Test
    public void agregarTramosNoContamientante() {

        repoTrayectos.buscarTodos().forEach(t -> {
            Tramo tramo = new Tramo(repoNoContaminante.get(Random.intBetween(0, repoNoContaminante.size())), Random.getRandomDireccion(), Random.getRandomDireccion());
            t.agregarTramo(tramo);
            repoTrayectos.modificar(t);
        });
    }

    @Test
    public void noHayTrayectosRepetidos() {
        Miembro miembro = repoMiembro.buscar(13);
        System.out.println(miembro.getTrayectos().size());
    }
}
