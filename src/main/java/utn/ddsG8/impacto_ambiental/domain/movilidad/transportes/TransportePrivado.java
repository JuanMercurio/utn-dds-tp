package utn.ddsG8.impacto_ambiental.domain.movilidad.transportes;

import utn.ddsG8.impacto_ambiental.domain.calculos.CalcularHC;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.combustibles.Combustible;

import javax.persistence.*;

@Entity
@DiscriminatorValue("privado")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_privado")
public abstract class TransportePrivado extends Transporte {

    @Transient //todo
    protected Combustible combustible;

    public double calcularHC(double distancia) {
        double fe;
        fe = (double) CalcularHC.getInstancia().buscarFactorEmision(this.nombreFE,"");
        if (fe != -1){
            return fe * distancia;
        }
        return -1;
    }

    public String getNombre() {
        return nombreFE;
    }

    public void setNombre(String nombre) {
        this.nombreFE = nombre;
    }
}
