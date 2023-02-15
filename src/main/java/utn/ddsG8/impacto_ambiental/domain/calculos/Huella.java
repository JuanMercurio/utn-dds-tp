package utn.ddsG8.impacto_ambiental.domain.calculos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import utn.ddsG8.impacto_ambiental.db.Persistable;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Entity
@Table(name = "huella")
@NoArgsConstructor
public class Huella extends Persistable {
    private Date fecha;
    private double valor;

    public Huella(double valor) {
        this.fecha = new Date();
        this.valor = valor;
    }
}
