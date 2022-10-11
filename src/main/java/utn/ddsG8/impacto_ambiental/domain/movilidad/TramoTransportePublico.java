package utn.ddsG8.impacto_ambiental.domain.movilidad;

import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.publico.Parada;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.publico.TransportePublico;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.Distancia;

import javax.persistence.*;

@Entity
@DiscriminatorValue("tramo_publico")
public class TramoTransportePublico extends Tramo {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parada_inicio", referencedColumnName = "id")
    private Parada inicio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parada_fin", referencedColumnName = "id")
    private Parada fin;

    public TramoTransportePublico(TransportePublico transporte, Parada inicio, Parada fin) {
        this.transporte = transporte;
        this.inicio = inicio;
        this.fin = fin;
        this.distancia = calcularDistancia(inicio, fin);
    }

    private Distancia calcularDistancia(Parada inicio, Parada fin) {
        return new Distancia(inicio.distanciaAParada(fin,(TransportePublico) this.transporte), "KM");
    }

}
