package utn.ddsG8.impacto_ambiental.CalculoHC;

import com.sun.xml.internal.ws.policy.AssertionSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utn.ddsG8.impacto_ambiental.domain.calculos.CalcularHC;
import utn.ddsG8.impacto_ambiental.domain.estructura.Miembro;
import utn.ddsG8.impacto_ambiental.domain.estructura.Sector;
import utn.ddsG8.impacto_ambiental.domain.movilidad.Trayecto;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;

import java.util.List;

public class test {

    private final Repositorio<Sector> repoSector = FactoryRepositorio.get(Sector.class);

    @Test

    public void persistirHuella() {



        Sector s = repoSector.buscar(4);
        System.out.println(s.getNombre());
        System.out.println(s.getMiembros().size());
        Miembro jana = s.getMiembros().get(1);
        System.out.println(jana.getTrayectos().size());

//        repoSector.modificar(s);

    }
}
