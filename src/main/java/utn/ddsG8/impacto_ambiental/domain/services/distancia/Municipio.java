package utn.ddsG8.impacto_ambiental.domain.services.distancia;

import lombok.Getter;
import lombok.Setter;
import utn.ddsG8.impacto_ambiental.domain.estructura.Organizacion;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@DiscriminatorValue("municipio")
public class Municipio extends SectorTerritorial {


    @ManyToOne
    @JoinColumn(name = "provincia", referencedColumnName = "id_db")
    public Provincia provincia;

    public Municipio(int id, String nombre, Provincia provincia) {
        this.id = id;
        this.nombre = nombre;
        this.provincia = provincia;
    }

    public double CalcularHCMunicipio (List<Organizacion> organizaciones){
        double hc = 0;
        for (Organizacion org: organizaciones) {

            if(org.getDireccion().getLocalidad().municipio.nombre == this.nombre){
                hc+= org.CalcularHC();
            }
            //
        }
        return hc;
    }

    @Override
    public SectorTerritorial getPadre() {
        return this.provincia;
    }
}
