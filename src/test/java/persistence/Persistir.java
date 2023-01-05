package persistence;

import org.junit.jupiter.api.Test;
import utn.ddsG8.impacto_ambiental.db.CreadorDeEntidades;
import utn.ddsG8.impacto_ambiental.domain.estructura.Miembro;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;

import java.util.List;

public class Persistir {

    private Repositorio<Miembro> miembrosRepositorio = FactoryRepositorio.get(Miembro.class);

    @Test
    public void persistirOrganizaciones() {
    }

    public void persistirMiembros() {
        List<Miembro> miembros = CreadorDeEntidades.crearMiembros(50);
        miembros.forEach(m -> miembrosRepositorio.agregar(m));
    }
}
