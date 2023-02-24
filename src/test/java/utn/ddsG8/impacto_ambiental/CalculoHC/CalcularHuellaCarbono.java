package utn.ddsG8.impacto_ambiental.CalculoHC;


import org.junit.jupiter.api.Test;

import utn.ddsG8.impacto_ambiental.domain.Notificaciones.Contacto;
import utn.ddsG8.impacto_ambiental.domain.estructura.*;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.Localidad;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;

public class CalcularHuellaCarbono {

    private final Repositorio<Organizacion> repoOrganizacion = FactoryRepositorio.get(Organizacion.class);
    private final Repositorio<Localidad> repoLocalidad = FactoryRepositorio.get(Localidad.class);

    @Test
    public void test() {
        Direccion dir = new Direccion("saturno", 123, repoLocalidad.buscar(3280));
        Organizacion org = new Organizacion("saturno", OrgTipo.Empresa, Clasificacion.Empresa_del_Sector_Primario, dir);
        org.setId(50);
        repoOrganizacion.agregar(org);
    }
}