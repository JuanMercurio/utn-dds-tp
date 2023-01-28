package utn.ddsG8.impacto_ambiental.domain.services.distancia;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import utn.ddsG8.impacto_ambiental.domain.estructura.Organizacion;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@DiscriminatorValue("provincia")
public class Provincia extends SectorTerritorial {

    @ManyToOne
    @JoinColumn(name = "pais", referencedColumnName = "id")
    public Pais pais;

    public Provincia(int id, String nombre, Pais pais) {
        this.id = id;
        this.nombre = nombre;
        this.pais = pais;
    }

    public Provincia(){}

    public double CalcularHCProvincia (List<Organizacion> organizaciones){
        double hc = 0;
        for (Organizacion org: organizaciones) {

            if(org.getDireccion().getLocalidad().municipio.provincia.nombre == this.nombre){
                hc+= org.CalcularHC();
            }
            //
        }
        return hc;
    }

    @Override
    public SectorTerritorial getPadre() {
        return null;
    }
}
