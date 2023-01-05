package persistence;

import org.junit.jupiter.api.Test;
import utn.ddsG8.impacto_ambiental.domain.estructura.Miembro;
import utn.ddsG8.impacto_ambiental.domain.movilidad.Trayecto;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;

public class tempfile {
    private Repositorio<Trayecto> trayectos = FactoryRepositorio.get(Trayecto.class);
    private Repositorio<Miembro> miembros = FactoryRepositorio.get(Miembro.class);

    @Test
    public void test(){
        Trayecto t = trayectos.buscar(1);
        Miembro m = miembros.buscar(21);

        t.getMiembros().forEach(mi -> System.out.println(mi.getNombre()));

        m.agregarATrayecto(t);

        t.getMiembros().forEach(mi -> System.out.println(mi.getNombre()));

        trayectos.modificar(t);



    }


    public void asdf() {
        Trayecto t = trayectos.buscar(1);
        Miembro m = miembros.buscar(21);



    }
}
