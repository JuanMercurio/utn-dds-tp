package utn.ddsG8.impacto_ambiental.domain.movilidad.transportes;

import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.combustibles.Combustible;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("moto")
public class Moto extends VehiculoParticular {

    public Moto(Combustible combustible) {
        this.combustible = combustible;
        this.nombreFE = "Moto";
    }


}
