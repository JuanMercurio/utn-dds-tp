package utn.ddsG8.impacto_ambiental.domain.calculos;


import utn.ddsG8.impacto_ambiental.db.Persistable;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "factor_emision")
public class FE extends Persistable {

    @Column
    private String nombre;
    @Column
    private String tipo;
    @Column
    private Double valor;
    @Column
    private String unidad;
    // PARA TODOS LAS LOGISTICAS SON EL MISMO VALOR DE EMISION.

    public FE() {}
    public FE(String nombre, String tipoConsumo, String unidad, double valorFE) {
        this.nombre = nombre;
        this.tipo = tipoConsumo;
        this.unidad = unidad;
        this.valor = valorFE;
    }

    public FE(String actividad,  double valorFE) {
        this.nombre = actividad;
        this.valor = valorFE;
    }

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

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }



}
