package utn.ddsG8.impacto_ambiental.domain.movilidad.transportes;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("pie")
public class Pie extends TransportePrivado {

    public Pie() {
        this.nombreFE = "Pie";
    }


}
