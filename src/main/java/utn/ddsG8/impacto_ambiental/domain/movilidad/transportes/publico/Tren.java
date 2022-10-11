package utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.publico;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.ArrayList;

@Entity
@DiscriminatorValue("tren")
public class Tren extends TransportePublico {

    public Tren(String nombre) {
        this.nombre = nombre;
        this.paradas = new ArrayList<Parada>();
        this.nombreFE = "Tren";
    }


}
