package persistence;

import org.junit.jupiter.api.Test;
import utn.ddsG8.impacto_ambiental.domain.estructura.Direccion;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.publico.Parada;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;

import java.util.List;

public class ponerParadasEnDirecciones {
    public final Repositorio<Parada> repoParadas = FactoryRepositorio.get(Parada.class);
    public final Repositorio<Direccion> repoDireccions = FactoryRepositorio.get(Direccion.class);

    @Test
    public void test() {
        List<Parada> paradasList = repoParadas.buscarTodos();
        paradasList.forEach(p -> {
            p.getDireccion().setParada(p);
            repoDireccions.modificar(p.getDireccion());
        });
    }
}
