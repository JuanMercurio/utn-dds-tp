package utn.ddsG8.impacto_ambiental.controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import utn.ddsG8.impacto_ambiental.domain.estructura.Direccion;
import utn.ddsG8.impacto_ambiental.domain.estructura.Miembro;
import utn.ddsG8.impacto_ambiental.domain.estructura.Organizacion;
import utn.ddsG8.impacto_ambiental.domain.helpers.TrayectoHelper;
import utn.ddsG8.impacto_ambiental.domain.movilidad.Tramo;
import utn.ddsG8.impacto_ambiental.domain.movilidad.Trayecto;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.ServicioContratado;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.Transporte;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.TransporteNoContaminante;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.VehiculoParticular;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.publico.Parada;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.publico.TransportePublico;
import utn.ddsG8.impacto_ambiental.domain.helpers.MiembroHelper;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.Localidad;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TrayectoController {

    private static Repositorio<Parada> repoParada = FactoryRepositorio.get(Parada.class);
    private static Repositorio<Trayecto> trayectosRepo = FactoryRepositorio.get(Trayecto.class);
    private static Repositorio<TransportePublico> repoTransportePublico = FactoryRepositorio.get(TransportePublico.class);
    private static Repositorio<VehiculoParticular> repoVehiculoParticular = FactoryRepositorio.get(VehiculoParticular.class);
    private static Repositorio<Transporte> repoTransporte = FactoryRepositorio.get(Transporte.class);
    private static Repositorio<Trayecto> repoTrayecto = FactoryRepositorio.get(Trayecto.class);
    private static Repositorio<Direccion> repoDireccion = FactoryRepositorio.get(Direccion.class);
    private static List<Localidad> localidadList = FactoryRepositorio.get(Localidad.class).buscarTodos();
    private static Repositorio<Localidad> repoLocalidad = FactoryRepositorio.get(Localidad.class);
    private static Repositorio<ServicioContratado> repoServicioContratado = FactoryRepositorio.get(ServicioContratado.class);


    public static ModelAndView crearTramoView(Request request, Response response) {
        Miembro miembro = MiembroHelper.getCurrentMiembro(request);

        List<Transporte> transportesDeMiembro = repoTransporte.query("from transporte where duenio = " + miembro.getId());
        List<TransportePublico> tpublicos = repoTransportePublico.buscarTodos();
        List<Parada> paradas = repoParada.buscarTodos();
        List<ServicioContratado> servicioContratados = repoServicioContratado.buscarTodos();

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("localidades", localidadList);
        parametros.put("publicos", tpublicos);
        parametros.put("particulares", transportesDeMiembro);
        parametros.put("paradas", paradas);
        parametros.put("contratados", servicioContratados);
        return new ModelAndView(parametros, "/trayecto/newTrayecto.hbs");
    }

    public static ModelAndView trayectosMiembroView(Request request, Response response) {
        Miembro miembro = MiembroHelper.getCurrentMiembro(request);
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("miembro", miembro);
        return new ModelAndView(parametros, "trayecto/trayectosMiembro.hbs");
    }

    public static ModelAndView trayectoView(Request request, Response response) {
        Trayecto trayecto = TrayectoHelper.getTrayecto(request);
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("trayecto", trayecto);
        return new ModelAndView(parametros, "trayecto/trayecto.hbs");
    }

    public static ModelAndView agregarTramoView(Request request, Response response) {
        Miembro miembro = MiembroHelper.getCurrentMiembro(request);

        List<Transporte> transportesDeMiembro = repoTransporte.query("from transporte where duenio = " + miembro.getId());
        List<TransportePublico> tpublicos = repoTransportePublico.buscarTodos();
        List<Parada> paradas = repoParada.buscarTodos();
        List<ServicioContratado> servicioContratados = repoServicioContratado.buscarTodos();
        List<TransporteNoContaminante> noContaminantes = FactoryRepositorio.get(TransporteNoContaminante.class).buscarTodos();
        Set<Organizacion> orgs = miembro.getSectores().stream().map(s -> s.getOrganizacion()).collect(Collectors.toSet());

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("localidades", localidadList);
        parametros.put("publicos", tpublicos);
        parametros.put("particulares", transportesDeMiembro);
        parametros.put("paradas", paradas);
        parametros.put("contratados", servicioContratados);
        parametros.put("noContaminantes", noContaminantes);
        parametros.put("orgs", orgs);

        return new ModelAndView(parametros, "/trayecto/newTrayecto.hbs");
    }

    //todo
    public static String tramoView(Request request, Response response) {
        return "esto es un tramo";
    }

    public static Response agregarTramo(Request request, Response response) {

        Transporte transporte = repoTransporte.buscar(Integer.parseInt(request.queryParams("transporte")));

        Tramo tramo = crearTramo( request,
                determinarDireccion(request, "direccion-inicial"),
                determinarDireccion(request, "direccion-final"),
                transporte);


        // todo falta ver si llega bien el tramo y persistirlo
        //todo falta la redireccion

        return response;
    }

    private static Direccion determinarDireccion(Request request, String queryParam) {
        Direccion direccion = repoDireccion.buscar(Integer.parseInt(request.queryParams(queryParam)));
        if (direccion != null) return direccion;

        return  new Direccion(request.queryParams("calle"),
                Integer.parseInt(request.queryParams("altura")),
                repoLocalidad.buscar(Integer.parseInt(request.queryParams("localidad-" + queryParam))));
    }

    private static Tramo crearTramo(Request request, Direccion inicio, Direccion fin, Transporte transporte) {
        Tramo tramo = new Tramo(transporte, inicio, fin);
        return tramo;
    }

    public static Response crearTrayecto(Request request, Response response) {
        Trayecto trayecto = new Trayecto();
        trayecto.getMiembros().add(MiembroHelper.getCurrentMiembro(request));
        repoTrayecto.agregar(trayecto);
        response.redirect("trayecto/" + trayecto.getId() + "/agregarTramo");
        return response;
    }
}
