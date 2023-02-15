package utn.ddsG8.impacto_ambiental.persistence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utn.ddsG8.impacto_ambiental.db.EntityManagerHelper;
import utn.ddsG8.impacto_ambiental.domain.estructura.*;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.*;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class EntityManagerTest {
    private static List<Pais> paises;
    private static List<Provincia> provincias;
    private static List<Municipio> municipios;
    private static List<Localidad> localidades;


    public void getLocations() {
        // TODO: Este test es muy lento, hay que usar mockito
        paises = DistanciaServicio.getInstancia().paises( 1);
        provincias = DistanciaServicio.getInstancia().provincias( 1);
        municipios = new ArrayList<>();
        localidades = new ArrayList<>();
        for (Provincia p : provincias) {
            List<Municipio> municipiosProvicia = DistanciaServicio.getInstancia().municipios( 1, p.id);
            municipios.addAll(municipiosProvicia);
        }
        for (Municipio m : municipios) {
            List<Localidad> localidadesMunicipio = DistanciaServicio.getInstancia().localidades( 1, m.id);
            localidades.addAll(localidadesMunicipio);
        }
    }

    @Test
    public void persistirLocations() {

        getLocations();
        EntityManagerHelper.beginTransaction();
        paises.forEach(p -> EntityManagerHelper.getEntityManager().persist(p));
        EntityManagerHelper.commit();
        EntityManagerHelper.beginTransaction();
        provincias.forEach(p -> EntityManagerHelper.getEntityManager().persist(p));
        EntityManagerHelper.commit();
        EntityManagerHelper.beginTransaction();
        municipios.forEach(m -> EntityManagerHelper.getEntityManager().persist(m));
        EntityManagerHelper.commit();
        EntityManagerHelper.beginTransaction();
        localidades.forEach(l -> EntityManagerHelper.getEntityManager().persist(l));
        EntityManagerHelper.commit();
    }

    @Test
    public void persistirMovilidad() {
//        Auto auto = new Auto(null);
//        Subte subte = new Subte(null);
//        Colectivo colectivo = new Colectivo(null);
//
//        EntityManagerHelper.beginTransaction();
//
//        EntityManagerHelper.getEntityManager().persist(auto);
//        EntityManagerHelper.getEntityManager().persist(subte);
//        EntityManagerHelper.getEntityManager().persist(colectivo);
//
//        EntityManagerHelper.commit();

    }

    @Test
    public void persistirEstructura() {

        Direccion direccion = new Direccion("ejemplo_calle", 123, null);
        Organizacion org = new Organizacion(
                "ejemplo razonSocial",
                OrgTipo.Empresa,
                Clasificacion.Empresa_del_Sector_Primario,
                direccion);
        Sector s = new Sector("soy un sector", org);

        Miembro m1 = new Miembro( "aprobame",  "pablo", TipoDoc.DNI, "12341234");
        Miembro m2 = new Miembro( "aprobame",  "pablo", TipoDoc.DNI, "12341234");

        m1.unirseAOrg(org, s);

        org.aceptarTodosLosMiembros();

        m2.unirseAOrg(org, s);

        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(org);
        EntityManagerHelper.commit();
    }

    @Test
    public void existeJana(){
        String queryString = "FROM Miembro where id = 1";
        Query query = EntityManagerHelper.createQuery(queryString);
        Miembro miembro = (Miembro) query.getResultList().get(0);
        Assertions.assertTrue(miembro.getNombre().equals("Jana"));

    }

    @Test
    public void encontrarProvincias() {
        String queryString = "FROM Provincia";
        Query query = EntityManagerHelper.createQuery(queryString);
        List<Provincia> provincias = query.getResultList();
        provincias.forEach(p -> System.out.println(p.nombre));
    }



    @Test
    public void persistirTrayectoDeJana() {
        String queryString = "FROM Miembro where nombre = 'Jana'";
        Query query = EntityManagerHelper.createQuery(queryString);
        Miembro jana = (Miembro) query.getResultList().get(0);

        Query query1 = EntityManagerHelper.createQuery("FROM Organizacion where id = 1");
        Organizacion org = (Organizacion) query1.getResultList().get(0);
    }

    @Test
    public void test() {
    }
}
