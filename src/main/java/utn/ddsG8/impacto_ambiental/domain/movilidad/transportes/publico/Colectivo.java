package utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.publico;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.ArrayList;

@Entity
@Getter
@Setter
@DiscriminatorValue("colectivo")
public class Colectivo extends TransportePublico {
    public Colectivo() {}
    public Colectivo(String nombre) {
        this.nombre = nombre;
        this.paradas = new ArrayList<Parada>();
        this.nombreFE = "Colectivo";
    }
}
