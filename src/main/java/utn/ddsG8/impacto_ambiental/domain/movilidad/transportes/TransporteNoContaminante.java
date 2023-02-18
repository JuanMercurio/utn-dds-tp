package utn.ddsG8.impacto_ambiental.domain.movilidad.transportes;

import lombok.Getter;
import lombok.Setter;
import utn.ddsG8.impacto_ambiental.domain.calculos.CalcularHC;
import utn.ddsG8.impacto_ambiental.domain.movilidad.Tramo;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.calculadores.CalculadorDistanciaCalles;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.calculadores.CalculadorDistanciaParadas;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.Distancia;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@DiscriminatorValue("no_contamina")
public class TransporteNoContaminante extends Transporte {

    public TransporteNoContaminante() {}
    public TransporteNoContaminante(String nombre, String fe) {
        this.nombre = nombre;
        this.tipoDeTransporte = nombre;
        this.nombreFE = fe;
    }

    public double calcularHCTramo(Tramo tramo) {
        return 0;
    }

    public double FEvalor() {
        return 0;
    }

    public double getConsumo(Tramo tramo) {
        return 0;
    }

    public Boolean esPublico() {
        return false;
    }

    public void calcularDistancia(Tramo tramo) {
        new CalculadorDistanciaCalles().calcularDistanciaTramo(tramo);
    }

}
