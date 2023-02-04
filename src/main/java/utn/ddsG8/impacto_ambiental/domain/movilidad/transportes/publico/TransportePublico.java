package utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.publico;

import lombok.Getter;
import utn.ddsG8.impacto_ambiental.domain.calculos.CalcularHC;
import utn.ddsG8.impacto_ambiental.domain.calculos.FE;
import utn.ddsG8.impacto_ambiental.domain.movilidad.Tramo;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.Transporte;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.calculadores.CalculadorDistanciaParadas;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.combustibles.CombustibleE;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.Distancia;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("publico")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "publico")
public class TransportePublico extends  Transporte {

    // esta forma es fea. Lo mejor es tener el transporte en la parada directamente
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "transporte", referencedColumnName = "id")
    protected List<Parada> paradas;

    public TransportePublico() {}
    public TransportePublico(String nombre, String tipoDeTransporte, String fe) {;
        this.nombre = nombre;
        this.tipoDeTransporte = tipoDeTransporte;
        this.nombreFE = fe;
        this.paradas = new ArrayList<>();
    }

    public void agregarParada(Parada nueva, int numeroParada) {
        // IMPORTANTE: Esta implementado como que el administrador pone 0 para la primera parada, no 1
        // Se puede cambiar con un simple -1. Decidir que queda
        paradas.add(numeroParada, nueva);
        Parada anterior = paradas.get(Math.max(0, numeroParada-1));
        Parada proxima = paradas.get(Math.min(numeroParada+1, paradas.size() - 1));
        if (numeroParada == 0) {
            nueva.setAnteriorParada(null);
            nueva.setDistanciaAnteriorParada(0);
        }
        else nueva.setAnteriorParada(anterior);
        if (numeroParada == paradas.size() - 1) {
            nueva.setProximaParada(null);
            nueva.setDistanciaProximaParada(0);
        }
        else nueva.setProximaParada(proxima);

        if (nueva.getAnteriorParada() != null) {
            nueva.getAnteriorParada().setDistanciaProximaParada(nueva.getDistanciaAnteriorParada().valor);
            nueva.getAnteriorParada().setProximaParada(nueva);
        }

        if (proxima.getProximaParada() != null) {
            nueva.getProximaParada().setDistanciaAnteriorParada(nueva.getDistanciaProximaParada().valor);
            nueva.getProximaParada().setAnteriorParada(nueva);
        }
    }

    public List<Parada> getParadas() {
        return this.paradas;
    }

    public double FEvalor() {
        return FactoryRepositorio.get(FE.class).query("FROM factor_emision where nombre = '" + this.nombreFE + "'").get(0).getValor();
    }
    public double getConsumo(Tramo tramo) {
        return tramo.getDistancia().valor;
    }

    public Boolean esPublico() {
        return true;
    }

    @Override
    public void setCalculadorDistanciaTramo(Tramo tramo) {
        tramo.setCalculadorTramo(new CalculadorDistanciaParadas());
    }
}
