package utn.ddsG8.impacto_ambiental.domain.calculos;

public class FE  {

    private String nombre;
    private String tipo;
    private Double valor;
    private String unidad;
    // PARA TODOS LAS LOGISTICAS SON EL MISMO VALOR DE EMISION.


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public FE(String actividad, String tipoConsumo, String unidad, double valorFE) {
        this.nombre = actividad;
        this.tipo = tipoConsumo;
        this.unidad = unidad;
        this.valor = valorFE;
    }
    public FE(String actividad,  double valorFE) {
        this.nombre = actividad;
        this.unidad = unidad;
        this.valor = valorFE;
    }




    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }



}
