package utn.ddsG8.impacto_ambiental.arq;

import org.junit.jupiter.api.Test;
import utn.ddsG8.impacto_ambiental.domain.estructura.Miembro;
import utn.ddsG8.impacto_ambiental.domain.movilidad.Tramo;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.Transporte;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.publico.Parada;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.publico.TransportePublico;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;

import java.util.List;
import java.util.stream.Collectors;

public class TrayectoController {
    private static Repositorio<Parada> paradaRepo = FactoryRepositorio.get(Parada.class);
    private static Repositorio<TransportePublico> transportesPublicos = FactoryRepositorio.get(TransportePublico.class);
    private static Repositorio<Miembro> miembrosRepo = FactoryRepositorio.get(Miembro.class);
    private static Repositorio<Transporte> Transporte = FactoryRepositorio.get(Transporte.class);

    @Test
    public void probar() {

        Miembro miembro = miembrosRepo.buscar(21);

        List<Transporte> VehiculosDeMiembro =  Transporte.query("from Transporte where duenio = 21");

        List<Tramo> tramos = miembro.getTrayectos().stream().flatMap(t -> t.getTramos().stream()).collect(Collectors.toList());
           List<Tramo> tramosDeAutos = tramos.stream().filter(t -> !t.getTransporte().esPublico()).collect(Collectors.toList());
        List<Transporte> transportesDeMiembro = tramosDeAutos.stream().map(t -> t.getTransporte()).collect(Collectors.toList());
        List<TransportePublico> tpublicos = transportesPublicos.buscarTodos();
        List<Parada> paradas = paradaRepo.buscarTodos();

        System.out.println(miembro.getNombre());
        System.out.println(VehiculosDeMiembro.size());
        transportesDeMiembro.forEach(t -> System.out.println(t.getNombreFE()));
//        tpublicos.forEach(t -> System.out.println(t.getNombreFE()));
//        System.out.println(paradas.size());

    }
}
