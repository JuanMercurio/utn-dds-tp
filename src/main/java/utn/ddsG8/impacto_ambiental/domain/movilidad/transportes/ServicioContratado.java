package utn.ddsG8.impacto_ambiental.domain.movilidad.transportes;

import lombok.NoArgsConstructor;
import utn.ddsG8.impacto_ambiental.domain.calculos.CalcularHC;
import utn.ddsG8.impacto_ambiental.domain.calculos.FE;
import utn.ddsG8.impacto_ambiental.domain.movilidad.Tramo;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.calculadores.CalculadorDistanciaCalles;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.calculadores.CalculadorDistanciaTramo;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.Distancia;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.DistanciaServicio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;

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
        return FactoryRepositorio.get(FE.class).query("FROM factor_emision where nombre = '" + this.nombreFE + "'").get(0).getValor();
    }

    public void calcularDistanciaDeTramo(Tramo tramo) {
        DistanciaServicio api = DistanciaServicio.getInstancia();
        tramo.setDistancia(new Distancia(api.distancia(tramo.getInicio(), tramo.getFin()).valor, "KM"));
    }

    public Boolean esPublico() {
        return false;
    }

    @Override
    public void calcularDistancia(Tramo tramo) {
        new CalculadorDistanciaCalles().calcularDistanciaTramo(tramo);
    }
}
