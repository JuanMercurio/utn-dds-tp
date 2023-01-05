package utn.ddsG8.impacto_ambiental.controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import utn.ddsG8.impacto_ambiental.db.EntityManagerHelper;
import utn.ddsG8.impacto_ambiental.domain.estructura.Miembro;
import utn.ddsG8.impacto_ambiental.domain.movilidad.Tramo;
import utn.ddsG8.impacto_ambiental.domain.movilidad.Trayecto;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.Transporte;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.TransportePrivado;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.publico.Colectivo;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.publico.Parada;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.publico.Subte;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.publico.TransportePublico;
import utn.ddsG8.impacto_ambiental.helpers.MiembroHelper;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TrayectoController {

    private static Repositorio<Colectivo> colectivosRepo = FactoryRepositorio.get(Colectivo.class);
    private static Repositorio<Parada> paradaRepo = FactoryRepositorio.get(Parada.class);
    private static Repositorio<Trayecto> trayectosRepo = FactoryRepositorio.get(Trayecto.class);
    private static Repositorio<Subte> subtesRepo = FactoryRepositorio.get(Subte.class);
    private static Repositorio<TransportePublico> transportesPublicos = FactoryRepositorio.get(TransportePublico.class);
    private static Repositorio<Transporte> transportesRepo = FactoryRepositorio.get(Transporte.class);

    public static ModelAndView createView(Request request, Response response) {
        Miembro miembro = MiembroHelper.getCurrentMiembroInURL(request);

        List<Transporte> transportesDeMiembro = transportesRepo.query("from Transporte where duenio = 21");
        List<TransportePublico> tpublicos = transportesPublicos.buscarTodos();
        List<Parada> paradas = paradaRepo.buscarTodos();

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("transportesPublicos", tpublicos);
        parametros.put("transportesPrivados", transportesDeMiembro);
        parametros.put("paradas", paradas);
        return new ModelAndView(parametros, "/trayecto/newTrayecto.hbs");
    }

    public static ModelAndView mostrarTrayectosMiembro(Request request, Response response) {
//        List<Trayecto> trayectos = trayectosRepo.buscarTodos().stream().filter(p -> {
//            return !p.getMiembros().contains(miembros.buscar(request.params("id")));
//        }).collect(Collectors.toList());
//        List<Trayecto> trayectos =  trayectosRepo.query("from Trayecto where miembros ='" + request.params("id") + "'");
        String query = "from Trayecto t join t.miembros tm where tm.id = '"+ request.params("id") + "'";
        List<Trayecto> trayectos = EntityManagerHelper.getEntityManager().createQuery(query).getResultList();
//        trayectos.forEach(tr -> System.out.println(tr.getId()));

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("trayecto", trayectos);

        return new ModelAndView(parametros, "trayecto/trayectosMiembro.hbs");
    }
}
