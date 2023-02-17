package utn.ddsG8.impacto_ambiental.domain.movilidad.transportes;

import lombok.NoArgsConstructor;
import utn.ddsG8.impacto_ambiental.domain.calculos.CalcularHC;
import utn.ddsG8.impacto_ambiental.domain.calculos.FE;
import utn.ddsG8.impacto_ambiental.domain.movilidad.Tramo;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.calculadores.CalculadorDistanciaCalles;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.calculadores.CalculadorDistanciaTramo;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.Distancia;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.DistanciaServicio;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("servicio_contratado")
@NoArgsConstructor
public class ServicioContratado extends Transporte {

    public ServicioContratado(String nombre, String nombreFE) {
        this.nombre = nombre;
        this.tipoDeTransporte = nombre;
        this.nombreFE = nombreFE;
    }

    public double getConsumo(Tramo tramo) {
        return tramo.getDistancia().valor;
    }

    public double FEvalor() {
        return CalcularHC.getInstancia().buscarFactorEmisionTransporte(nombreFE);
    }

    public void calcularDistanciaDeTramo(Tramo tramo) {
        DistanciaServicio api = DistanciaServicio.getInstancia();
        tramo.setDistancia(new Distancia(api.distancia(tramo.getInicio(), tramo.getFin()).valor, "KM"));
    }

    public Boolean esPublico() {
        return false;
    }

    @Override
    public void setCalculadorDistanciaTramo(Tramo tramo) {
        tramo.setCalculadorTramo(new CalculadorDistanciaCalles());
    }
}
