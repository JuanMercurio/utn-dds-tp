package utn.ddsG8.impacto_ambiental.estructura;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utn.ddsG8.impacto_ambiental.domain.estructura.*;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.Localidad;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.Municipio;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.Provincia;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.Pais;
import java.io.IOException;

class MiembroTest {

    private Miembro unMiembro = new Miembro("Matias","Carasi", TipoDoc.DNI, "42998217");
    private Pais unPais = new Pais(1, "pais");
    private Provincia unaProvincia = new Provincia(9, "Buenos Aires", unPais);
    private Municipio unMunicipio = new Municipio(2, "General Pueyrredon", unaProvincia);
    private Localidad unaLocalidad = new Localidad(1,"Mar del Plata",3546, unMunicipio);
    private Direccion direccionOrg = new Direccion("medrano", 1234, unaLocalidad);
    private Organizacion unaOrganizacion = new Organizacion("Escuela", OrgTipo.ONG, Clasificacion.Ministerio, direccionOrg);
    private Sector unSector = new Sector("Desarrollo", unaOrganizacion);
    private Sector otroSector = new Sector("Direccion", unaOrganizacion);

    @Test
    public void solicitarMiembroAOrg() throws IOException {
        unMiembro.unirseAOrg(unaOrganizacion,unSector);
        unaOrganizacion.aceptarTodosLosMiembros();
        Assertions.assertTrue(unSector.getMiembros().contains(unMiembro));
    }

    @Test
    public void miembroEnDosSectoresDeOrg() throws IOException {
        unMiembro.unirseAOrg(unaOrganizacion,unSector);
        unMiembro.unirseAOrg(unaOrganizacion,otroSector);
        unaOrganizacion.aceptarTodosLosMiembros();
        Assertions.assertTrue(unSector.getMiembros().contains(unMiembro));
        Assertions.assertTrue(otroSector.getMiembros().contains(unMiembro));
    }


}