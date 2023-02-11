package persistence;


import org.junit.jupiter.api.Test;
import utn.ddsG8.impacto_ambiental.domain.estructura.Miembro;
import utn.ddsG8.impacto_ambiental.domain.estructura.Organizacion;
import utn.ddsG8.impacto_ambiental.domain.estructura.Sector;
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
    public final Repositorio<Miembro> repoMiembro = FactoryRepositorio.get(Miembro.class);
    public final Repositorio<VehiculoParticular> repoParticular = FactoryRepositorio.get(VehiculoParticular.class);
    public final Repositorio<TransportePublico> repoTransportePublico = FactoryRepositorio.get(TransportePublico.class);
    public final List<TransporteNoContaminante> repoNoContaminante = FactoryRepositorio.get(TransporteNoContaminante.class).buscarTodos();


    public void persistirTrayectos() {

        List<Miembro> miembros = repoMiembro.buscarTodos();

        miembros.forEach(m -> {
            Trayecto trayecto = new Trayecto();
            trayecto.agregarMiembro(m);
            m.agregarATrayecto(trayecto);
            this.crearRandomTramosPublicos(trayecto,3).forEach(trayecto::agregarTramo);
            this.crearRandomTramosVehiculoParticular(m).forEach(trayecto::agregarTramo);
            repoTrayectos.agregar(trayecto);
        });

        miembros.forEach(repoMiembro::modificar);

    }

    public List<Tramo> crearRandomTramosPublicos(Trayecto trayecto, int cantidadTramos) {
        List<TransportePublico> transportePublicoList = repoTransportePublico.buscarTodos();
        List<Tramo> tramos = new ArrayList<>();

        for (int i=0; i < cantidadTramos; i++) {
            Tramo tramo = new Tramo(transportePublicoList.get(Random.intBetween(0, transportePublicoList.size())), Random.direccionesDeParadas().get(0), Random.direccionesDeParadas().get(2));
            tramos.add(tramo);
        }

        tramos.forEach(trayecto::agregarTramo);

        return tramos;
    }

    public List<Tramo> crearRandomTramosVehiculoParticular(Miembro miembro) {
        List<VehiculoParticular> transportes = repoParticular.buscarTodos().stream().filter(t -> t.getDuenio() == miembro).collect(Collectors.toList());
        List<Tramo> tramos = new ArrayList<>();
        transportes.forEach(t -> {
            Tramo tramo = new Tramo(t, Random.getRandomDireccion(), Random.getRandomDireccion());
            tramos.add(tramo);
        });

        return tramos;
    }

    public void agregarOrganizacionesATrayectos() {
        List<Miembro> miembrosList = repoMiembro.buscarTodos();
        for (Miembro m : miembrosList) {
            for (Trayecto t : m.getTrayectos()) {
                List<Sector> jeje = m.getSectores();
                int i = Random.intBetween(0, jeje.size());
                Organizacion org = jeje.get(i).getOrganizacion();
                t.agregarOrganizacion(org);
            }
        }

        miembrosList.forEach(repoMiembro::modificar);
    }

    public void agregarTramosNoContamientante() {

        repoTrayectos.buscarTodos().forEach(t -> {
            Tramo tramo = new Tramo(repoNoContaminante.get(Random.intBetween(0, repoNoContaminante.size())), Random.getRandomDireccion(), Random.getRandomDireccion());
            t.agregarTramo(tramo);
            repoTrayectos.modificar(t);
        });
    }

    @Test
    public void miembrosSinTrayecto() {
        List<Miembro> miembrosSinTrayecto = repoMiembro.buscarTodos().stream().filter(m -> m.getTrayectos().size() == 0).collect(Collectors.toList());
        System.out.println(miembrosSinTrayecto.size());
    }
}
