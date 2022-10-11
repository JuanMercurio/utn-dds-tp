package utn.ddsG8.impacto_ambiental.domain.services.distancia;

public class Distancia {
    public double valor;
    public String unidad;

    public Distancia(double valor, String unidad) {
        this.valor = valor;
        this.unidad = unidad;
    }

    public Distancia sumar(Distancia distancia) {
        return new Distancia(distancia.valor + valor, "KM");
    }
}
