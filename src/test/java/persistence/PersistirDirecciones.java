package persistence;

import org.junit.jupiter.api.Test;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.*;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;

import java.util.ArrayList;
import java.util.List;

public class PersistirDirecciones {
    private final String token = "Bearer E8iN6xBPXQsUI+M72MfPdVhM/o3axkzywqKZjjOyhe0=";
    private final Repositorio<Pais> paises = FactoryRepositorio.get(Pais.class);
    private final Repositorio<SectorTerritorial> sectores = FactoryRepositorio.get(SectorTerritorial.class);

    @Test
    public void persistir() {

        List<Pais> paisesList = DistanciaServicio.getInstancia().paises(token, 1);
        List<Provincia> provincias = DistanciaServicio.getInstancia().provincias(token, 1);
        List<Localidad> localidades = new ArrayList<>();
        List<Municipio> municipios = new ArrayList<>();

        paisesList.forEach(p ->DistanciaServicio.getInstancia().provincias(token, 1, p.id).forEach( provincia -> provincia.setPais(p)));

        provincias.forEach(p -> {
            DistanciaServicio.getInstancia().municipios(token, 1, p.id).forEach( m ->
                {
                    m.setProvincia(p);
                    municipios.add(m);
                });

        });

        municipios.forEach(m -> {
            DistanciaServicio.getInstancia().localidades(token, 1, m.id).forEach(l ->
                {
                    l.setMunicipio(m);
                    localidades.add(l);
                });
        });

        paisesList.forEach(p -> paises.agregar(p));
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
}