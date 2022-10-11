package utn.ddsG8.impacto_ambiental.domain.movilidad;

import utn.ddsG8.impacto_ambiental.domain.estructura.Direccion;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.TransportePrivado;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.Distancia;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.DistanciaServicio;

import javax.persistence.*;

@Entity
@DiscriminatorValue("tramo_privado")
public class TramoTransportePrivado extends Tramo {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "direccion_inicio", referencedColumnName = "id")
    private Direccion inicio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "direccion_fin", referencedColumnName = "id")
    private Direccion fin;

    public TramoTransportePrivado(TransportePrivado transportePrivado, Direccion inicio, Direccion fin, String token) {
        this.transporte = transportePrivado;
        this.inicio = inicio;
        this.fin = fin;
        this.distancia = this.calcularDistancia(token);
    }

    private Distancia calcularDistancia(String token) {
        DistanciaServicio api = DistanciaServicio.getInstancia();
        return distancia =  api.distancia(token, inicio, fin);
    }

    public Double calcularHC() {
        return this.transporte.calcularHC(this.distancia.valor);
    }

    public Double calcularHCAnual(int anio) {
        if( this.fecha.getYear() == anio){
            return this.transporte.calcularHC(this.distancia.valor);
        }
        return 0.0;
    }
    public Double calcularHCMensual(int anio, int mes) {
        if(this.fecha.getMonth().getValue() == mes && this.fecha.getYear() == anio){
            return this.transporte.calcularHC(this.distancia.valor);
        }
        return 0.0;
    }
}
