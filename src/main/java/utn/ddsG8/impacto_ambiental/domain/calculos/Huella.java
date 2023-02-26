package utn.ddsG8.impacto_ambiental.domain.calculos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utn.ddsG8.impacto_ambiental.db.Persistable;
import utn.ddsG8.impacto_ambiental.domain.estructura.Organizacion;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.SectorTerritorial;

import javax.persistence.*;
import java.util.Date;

@Getter
@Entity
@Setter
@Table(name = "huella")
@NoArgsConstructor
public class Huella extends Persistable {

    @Column
    private Date fecha;
    @Column
    private double valor;

    public Huella(double valor) {
        this.fecha = new Date();
        this.valor = valor;
    }
}
