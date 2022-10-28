package persistence;

import org.junit.jupiter.api.Test;
import utn.ddsG8.impacto_ambiental.domain.estructura.Organizacion;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;

import java.util.List;

public class RepositorioTest {

    @Test
    public void organizacionRepoTest() {
        Repositorio<Organizacion> repo = FactoryRepositorio.get(Organizacion.class);
        List<Organizacion> organizaciones = repo.buscarTodos();
        for (Organizacion organizacion : organizaciones) {
           System.out.println(organizacion.getId());
        }
    }

    @Test
    public void cambiarrol() {

    }
}
