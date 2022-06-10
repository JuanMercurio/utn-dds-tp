package utn.ddsG8.impacto_ambiental.movilidad;

import utn.ddsG8.impacto_ambiental.estructura.Miembro;
import utn.ddsG8.impacto_ambiental.estructura.Organizacion;
import utn.ddsG8.impacto_ambiental.services.distancia.Distancia;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Trayecto {
    private List<Miembro> miembros;
    private List<Organizacion> organizaciones;
    private List<Tramo> tramos;
    private Distancia distancia;
    private int id;

public Trayecto() {
        this.miembros = new ArrayList<Miembro>() ;
        this.organizaciones = new ArrayList<Organizacion>() ;
        this.tramos = new ArrayList<Tramo>() ;
        // TODO: id
    }

    public Distancia getDistancia() {
        if (distancia == null) {
            this.setDistancia();
        }
        return distancia;
    }

    public Distancia getDistanciaDeTramo(int index) {
        return tramos.get(index).getDistancia();
    }


    private void setDistancia() {
        float valor = (float) tramos.stream().mapToDouble(t -> t.getDistancia().valor)
                .sum();
        final String unidad = "KM";         //hardcodeado .... deberia sacar que unidad segun las unidades de lo calculado
        this.distancia = new Distancia(valor, unidad);
    }


    public  void AgregarOrganizacion(Organizacion unaOrg){ organizaciones.add(unaOrg);}

    public void agregarTramo(Tramo tramo) {
        tramos.add(tramo);
    }

    public void agregarTramos(Tramo ... tramo) {
        Stream.of(tramo).forEach(t -> tramos.add(t));
    }

    public void agregarMiembro(Miembro miembro) {
        miembros.add(miembro);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
