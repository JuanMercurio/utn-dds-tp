package utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.calculadores;

import utn.ddsG8.impacto_ambiental.domain.movilidad.Tramo;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.publico.TransportePublico;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.Distancia;

public class CalculadorDistanciaParadas implements CalculadorDistanciaTramo {
    @Override
    public void calcularDistanciaTramo(Tramo tramo) {
        double valor = tramo.getInicio().getParada().distanciaAParada(tramo.getFin().getParada(), (TransportePublico) tramo.getTransporte());
        tramo.setDistancia(new Distancia(valor, "KM"));
    }
}
