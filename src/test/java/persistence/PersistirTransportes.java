package persistence;

import org.junit.jupiter.api.Test;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.Transporte;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.publico.Colectivo;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.publico.Subte;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.publico.Tren;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;

public class PersistirTransportes {

    public final Repositorio<Transporte> repoTrans = FactoryRepositorio.get(Transporte.class);

    @Test
    public void persistirPublicosSimples (){
        Colectivo col = new Colectivo("318");
        Subte sub = new Subte("C");
        Tren roca = new Tren("Roca");

        repoTrans.agregar(col);
        repoTrans.agregar(sub);
        repoTrans.agregar(roca);
    }
}
