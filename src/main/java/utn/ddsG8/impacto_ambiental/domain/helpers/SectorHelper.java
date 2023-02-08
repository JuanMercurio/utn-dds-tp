package utn.ddsG8.impacto_ambiental.domain.helpers;

import utn.ddsG8.impacto_ambiental.domain.estructura.Organizacion;
import utn.ddsG8.impacto_ambiental.domain.estructura.Sector;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;

public class SectorHelper {
    private static Repositorio<Sector> repoSector = FactoryRepositorio.get(Sector.class);

    public static Sector getSector(int i) {
        return repoSector.buscar(i);
    }

}
