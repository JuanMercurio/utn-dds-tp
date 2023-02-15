package utn.ddsG8.impacto_ambiental.domain.services.distancia;

import lombok.Getter;
import lombok.Setter;
import utn.ddsG8.impacto_ambiental.domain.calculos.CalcularHC;
import utn.ddsG8.impacto_ambiental.domain.estructura.Direccion;
import utn.ddsG8.impacto_ambiental.domain.estructura.Organizacion;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@DiscriminatorValue("provincia")
public class Provincia extends SectorTerritorial implements Serializable {

    @ManyToOne
    @JoinColumn(name = "pais", referencedColumnName = "id")
    public Pais pais;

    public Provincia(int id, String nombre, Pais pais) {
        this.id = id;
        this.nombre = nombre;
        this.pais = pais;
    }

    public Provincia(){}

    @Override
    public SectorTerritorial getPadre() {
        return null;
    }

    @Override
    public boolean tieneDireccion(Direccion direccion) {
        return false;
    }
}
