package utn.ddsG8.impacto_ambiental.estructura;

import org.junit.jupiter.api.Test;
import utn.ddsG8.impacto_ambiental.domain.estructura.*;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;

import java.util.ArrayList;
import java.util.List;

public class OrganizacionTest {
    private Repositorio<Organizacion> organizacionRepositorio = FactoryRepositorio.get(Organizacion.class);
    private Repositorio<Sector> sectorRepositorio = FactoryRepositorio.get(Sector.class);
    private Repositorio<Miembro> miembroRepositorio = FactoryRepositorio.get(Miembro.class);

    @Test
    public void persistirSolicitantesAOrg() {

        Organizacion org = organizacionRepositorio.buscar(1);
        Sector sec = sectorRepositorio.buscar(4);
        Miembro unMiembro = miembroRepositorio.buscar(21);
        unMiembro.unirseAOrg(org, sec);
        org.aceptarTodosLosMiembros();
        organizacionRepositorio.modificar(org);
    }
}
