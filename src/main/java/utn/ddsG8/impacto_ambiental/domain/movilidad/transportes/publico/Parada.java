package utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.publico;

import utn.ddsG8.impacto_ambiental.db.converters.DistanciaConverter;
import utn.ddsG8.impacto_ambiental.domain.estructura.Direccion;
import utn.ddsG8.impacto_ambiental.db.Persistable;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.Distancia;

import javax.persistence.*;

@Entity
@Table(name = "parada")
public class Parada extends Persistable {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parada_proxima", referencedColumnName = "id")
    private Parada proximaParada;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parada_anterior", referencedColumnName = "id")
    private Parada anteriorParada;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "direccion", referencedColumnName = "id")
    private Direccion direccion;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "distancia_proxima_parada")
    @Convert(converter = DistanciaConverter.class)
    private Distancia distanciaProximaParada;

    @Column(name = "distancia_anterior_parada")
    @Convert(converter = DistanciaConverter.class)
    private Distancia distanciaAnteriorParada; //TODO por ahora no se usa esto, analizar si lo sacamos

    public Parada(String nombre, Direccion direccion/*, double distanciaProxima,
                  double distanciaAnterior, int i*/) {
        this.nombre = nombre;
        //this.tranportePublico = transportePublico;
        this.direccion = direccion;
        // Analizar si la unidad de las distancia siempre sera KM y si deberia tener un parametro "unidad" el constructor
        //this.distanciaProximaParada = new Distancia(distanciaProxima, "KM");
        //this.distanciaAnteriorParada = new Distancia(distanciaAnterior, "KM");
        //transportePublico.agregarParada(this, distanciaProxima, distanciaAnterior, i);
    }

    public double distanciaAParada(Parada paradaFinal, TransportePublico transporte) {
        double sum = 0;
        for (Parada parada :transporte.getParadas()) {
            sum += parada.distanciaProximaParada.valor ;
            if (parada == paradaFinal) break;
        }
        return sum;
    }

    public Distancia getDistanciaProximaParada() {
        return this.distanciaProximaParada;
    }

    public void setDistanciaProximaParada(double distanciaProximaParada) {
        this.distanciaProximaParada = new Distancia(distanciaProximaParada, "KM");
    }

    public void setDistanciaAnteriorParada(double distanciaAnteriorParada) {
        this.distanciaAnteriorParada = new Distancia(distanciaAnteriorParada, "KM");
    }

    public void setAnteriorParada(Parada anteriorParada) {
        this.anteriorParada = anteriorParada;
    }

    public void setProximaParada(Parada parada) {
        this.proximaParada = parada;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }
}
