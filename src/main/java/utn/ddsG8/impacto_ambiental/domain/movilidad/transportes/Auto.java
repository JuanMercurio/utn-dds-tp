package utn.ddsG8.impacto_ambiental.domain.movilidad.transportes;

import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.combustibles.Combustible;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("auto")
public class Auto extends VehiculoParticular {

    public Auto(Combustible combustible) {
        this.combustible = combustible;
        this.nombreFE = "Auto";
        // TODO: BUSCAR ..
    }

}
