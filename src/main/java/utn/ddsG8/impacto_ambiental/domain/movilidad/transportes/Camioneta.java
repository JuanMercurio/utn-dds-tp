package utn.ddsG8.impacto_ambiental.domain.movilidad.transportes;

import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.combustibles.Combustible;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("camioneta")
public class Camioneta extends VehiculoParticular {

    public Camioneta(Combustible combustible) {
        this.combustible = combustible;
        this.nombreFE="Camion";
    }


}
