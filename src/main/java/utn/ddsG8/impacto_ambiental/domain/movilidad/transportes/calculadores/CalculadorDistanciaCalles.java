package utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.calculadores;

import utn.ddsG8.impacto_ambiental.domain.movilidad.Tramo;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.Distancia;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.DistanciaServicio;

public class CalculadorDistanciaCalles implements CalculadorDistanciaTramo {
    @Override
    public void calcularDistanciaTramo(Tramo tramo) {
        DistanciaServicio api = DistanciaServicio.getInstancia();
        tramo.setDistancia(new Distancia(api.distancia(tramo.getInicio(), tramo.getFin()).valor, "KM"));
    }
}
