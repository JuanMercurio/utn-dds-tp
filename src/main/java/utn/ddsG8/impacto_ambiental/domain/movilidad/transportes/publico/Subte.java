package utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.publico;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@DiscriminatorValue("subte")
public class Subte extends TransportePublico {
    public Subte(String nombre) {
        this.nombre = nombre;
        this.paradas = new ArrayList<>();
        this.nombreFE = "Subte";
    }



}
