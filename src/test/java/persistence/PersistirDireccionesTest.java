package persistence;

import org.junit.jupiter.api.Test;
import utn.ddsG8.impacto_ambiental.domain.estructura.Direccion;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.*;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;

import java.util.ArrayList;
import java.util.List;

public class PersistirDireccionesTest {
    private final Repositorio<Pais> paises = FactoryRepositorio.get(Pais.class);
    private final Repositorio<SectorTerritorial> sectores = FactoryRepositorio.get(SectorTerritorial.class);
    private final Repositorio<Localidad> localidades = FactoryRepositorio.get(Localidad.class);
    private final Repositorio<Direccion> direcciones  = FactoryRepositorio.get(Direccion.class);

    @Test
    public void persistirPaisesProvinciasMunicipiosLocalidades() {

        List<Pais> paisesList = DistanciaServicio.getInstancia().paises(1);
        List<Provincia> provincias = DistanciaServicio.getInstancia().provincias(1);
        List<Localidad> localidades = new ArrayList<>();
        List<Municipio> municipios = new ArrayList<>();

        paisesList.forEach(p ->DistanciaServicio.getInstancia().provincias(1, p.id).forEach( provincia -> provincia.setPais(p)));

        provincias.forEach(p -> DistanciaServicio.getInstancia().municipios(1, p.id).forEach(m ->
            {
                m.setProvincia(p);
                municipios.add(m);
            }));

        municipios.forEach(m -> DistanciaServicio.getInstancia().localidades(1, m.id).forEach(l ->
            {
                l.setMunicipio(m);
                localidades.add(l);
            }));

        paisesList.forEach(paises::agregar);
        provincias.forEach(p -> {
            Pais pais = paises.buscar(p.pais.id);
            p.setPais(pais);
            sectores.agregar(p);
        });

        municipios.forEach(m -> {
              Municipio muni = (Municipio) sectores.buscar(m.id_db);
              if (muni != null) return;
            sectores.agregar(m);
        });

        localidades.forEach(l -> {
            Localidad loca = (Localidad) sectores.buscar(l.id_db);
            if (loca != null) return;
            sectores.agregar(l);
        });

    }

    @Test
    public void persistirDirecciones() {

        List<Localidad> localidades = this.localidades.buscarTodos();
        localidades.forEach(l -> {
            Direccion d1 = new Direccion(Random.nombreCalleEjemplo(), Random.numeroCalle(), l);
            Direccion d2 = new Direccion(Random.nombreCalleEjemplo(), Random.numeroCalle(), l);

            direcciones.agregar(d1);
            direcciones.agregar(d2);
       });
    }
}
