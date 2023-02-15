package utn.ddsG8.impacto_ambiental.persistence;

import org.junit.jupiter.api.Test;
import utn.ddsG8.impacto_ambiental.domain.calculos.FE;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;

import java.util.ArrayList;
import java.util.List;

public class PersistirFactoresEmision {

    public final Repositorio<FE> repoFactores = FactoryRepositorio.get(FE.class);

    public void persistirFactores() {

        crearFactores().forEach(f -> {
            this.repoFactores.agregar(f);
        });
    }

    public static List<FE> crearFactores() {

        List<FE> factores =  new ArrayList<>();
        factores.add( new FE("autoFE", 30));
        factores.add( new FE("camionetaFE", 40));
        factores.add( new FE("motoFE", 10));
        factores.add( new FE("servicioContratadoFE", 30));
        factores.add( new FE("subteFE", 50));
        factores.add( new FE("colectivoFE", 100));
        factores.add( new FE("trenFE", 30));

        return factores;
    }


}
