package utn.ddsG8.impacto_ambiental.domain.movilidad.transportes;


import lombok.Getter;
import lombok.Setter;
import utn.ddsG8.impacto_ambiental.db.Persistable;
import utn.ddsG8.impacto_ambiental.domain.movilidad.Tramo;

import javax.persistence.*;

/*
*  Se uso single table porque es la mas performante,
*  al ser un dominio pequeño no es necesario usar las otras
* */
@Getter
@Setter
@Entity(name = "transporte")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
public abstract class Transporte extends Persistable {

    @Column
    protected String nombre;

    @Column
    protected String tipoDeTransporte;

    @Column(name = "nombreFE")
    protected String nombreFE;

    public double calcularHCTramo(Tramo tramo) {
        double a = this.FEvalor();
        double b = this.getConsumo(tramo);
        return  a * b;
    }

    public abstract double FEvalor();

    public abstract double getConsumo(Tramo tramo);

    public abstract Boolean esPublico();

    public abstract void setCalculadorDistanciaTramo(Tramo tramo);
}
