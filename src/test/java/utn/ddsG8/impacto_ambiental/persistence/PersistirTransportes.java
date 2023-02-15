package utn.ddsG8.impacto_ambiental.persistence;

import utn.ddsG8.impacto_ambiental.domain.estructura.Miembro;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.*;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.combustibles.CombustibleE;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.publico.*;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;

import java.util.List;

public class PersistirTransportes {

    public final Repositorio<Transporte> repoTransporte = FactoryRepositorio.get(Transporte.class);
    public final Repositorio<Miembro> repoMiembro = FactoryRepositorio.get(Miembro.class);

    public void persistirPrivados() {

        TransporteNoContaminante bici = new TransporteNoContaminante("bicicleta", "bicicletaFE");
        TransporteNoContaminante pie = new TransporteNoContaminante("pie", "pieFE");
        TransporteNoContaminante monopatin = new TransporteNoContaminante("monopatin", "monopatinFE");

        ServicioContratado uber = new ServicioContratado("uber", "uberFE");
        ServicioContratado taxi = new ServicioContratado("taxi", "taxiFE");
        ServicioContratado remis = new ServicioContratado("remis", "taxiFE");

        repoTransporte.agregar(bici);
        repoTransporte.agregar(pie);
        repoTransporte.agregar(monopatin);
        repoTransporte.agregar(uber);
        repoTransporte.agregar(remis);
        repoTransporte.agregar(taxi);

        List<Miembro> miembros = repoMiembro.buscarTodos();
        miembros.forEach(m -> persistirVehiculosParticularesParaMiembro(m));

    }

    public void persistirVehiculosParticularesParaMiembro(Miembro miembro) {

        VehiculoParticular auto = new VehiculoParticular("Auto" + miembro.getNombre(), "auto", "autoFE", CombustibleE.values()[Random.intBetween(0, CombustibleE.values().length)], Random.intBetween(1, 100));
        VehiculoParticular camion = new VehiculoParticular("Camion" + miembro.getNombre(), "camion", "camionFE",  CombustibleE.values()[Random.intBetween(0, CombustibleE.values().length)], Random.intBetween(1, 100));
        VehiculoParticular moto = new VehiculoParticular("Moto" + miembro.getNombre(), "moto", "motoFE", CombustibleE.values()[Random.intBetween(0, CombustibleE.values().length)], Random.intBetween(1, 100));

        auto.setDuenio(miembro);
        camion.setDuenio(miembro);
        moto.setDuenio(miembro);

        repoTransporte.agregar(auto);
        repoTransporte.agregar(camion);
        repoTransporte.agregar(moto);
    }

    public void persistirPublicos (){
        TransportePublico col = new TransportePublico("318", "colectivo", "colectivoFE");
        TransportePublico tren = new TransportePublico("Roca", "tren", "trenFE");
        TransportePublico subte = new TransportePublico("C", "subte", "subteFE");

        crearParadas(col, 5);
        crearParadas(subte, 5);
        crearParadas(tren, 5);

        repoTransporte.agregar(col);
        repoTransporte.agregar(subte);
        repoTransporte.agregar(tren);
    }

    public void crearParadas(TransportePublico transporte, int cantParadas) {
        for (int i=0; i<cantParadas; i++) {
            Parada parada = new Parada(transporte.getNombre() + " parada " + String.valueOf(i), Random.getRandomDireccion(), Random.intBetween(1, 100), Random.intBetween(1, 100));
            transporte.agregarParada(parada, i);
        }
    }

    public void testRandomParada() {
        TransportePublico col = new TransportePublico("318", "colectivo", "colectivoFE") ;
        Parada parada = new Parada("parada", Random.getRandomDireccion(), 1, 4);
        col.agregarParada(parada, 0);
        col.getParadas().forEach(p -> {
            System.out.println(p.getDistanciaProximaParada().valor);
        });
    }


}
