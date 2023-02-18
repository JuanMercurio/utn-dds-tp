package utn.ddsG8.impacto_ambiental.controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import utn.ddsG8.impacto_ambiental.domain.estructura.Direccion;
import utn.ddsG8.impacto_ambiental.domain.estructura.Miembro;
import utn.ddsG8.impacto_ambiental.domain.estructura.Organizacion;
import utn.ddsG8.impacto_ambiental.domain.estructura.Sector;
import utn.ddsG8.impacto_ambiental.domain.helpers.TrayectoHelper;
import utn.ddsG8.impacto_ambiental.domain.movilidad.Tramo;
import utn.ddsG8.impacto_ambiental.domain.movilidad.Trayecto;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.ServicioContratado;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.Transporte;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.TransporteNoContaminante;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.publico.Parada;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.publico.TransportePublico;
import utn.ddsG8.impacto_ambiental.domain.helpers.MiembroHelper;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.Localidad;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;

import java.util.*;
import java.util.stream.Collectors;

public class TrayectoController {

    private final static Repositorio<Parada> repoParada = FactoryRepositorio.get(Parada.class);
    private final static Repositorio<TransportePublico> repoTransportePublico = FactoryRepositorio.get(TransportePublico.class);
    private final static Repositorio<Transporte> repoTransporte = FactoryRepositorio.get(Transporte.class);
    private final static Repositorio<Trayecto> repoTrayecto = FactoryRepositorio.get(Trayecto.class);
    private final static Repositorio<Direccion> repoDireccion = FactoryRepositorio.get(Direccion.class);
    private final static List<Localidad> localidadList = FactoryRepositorio.get(Localidad.class).buscarTodos();
    private final static Repositorio<Localidad> repoLocalidad = FactoryRepositorio.get(Localidad.class);
    private final static Repositorio<ServicioContratado> repoServicioContratado = FactoryRepositorio.get(ServicioContratado.class);

    public static ModelAndView trayectosMiembroView(Request request, Response response) {
//        repoTrayecto.buscarTodos();
        Miembro miembro = MiembroHelper.getCurrentMiembro(request);
        List<Trayecto> trayectos = miembro.getTrayectos().stream().sorted(Comparator.comparing(Trayecto::getFecha)).collect(Collectors.toList());
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("miembro", miembro);
        parametros.put("trayectos", trayectos);
        response.header("Cache-Control", "no-cache, no-store, must-revalidate");
        return new ModelAndView(parametros, "trayecto/trayectosMiembro.hbs");
    }

    public static ModelAndView trayectoView(Request request, Response response) {
        Trayecto trayecto = TrayectoHelper.getTrayecto(request);
        Map<String, Object> parametros = new HashMap<>();
        double huella = trayecto.getHuella();
        parametros.put("trayecto", trayecto);
        parametros.put("huella", (double) Math.round(huella*100.0)/100);
        return new ModelAndView(parametros, "trayecto/trayecto.hbs");
    }

    public static ModelAndView agregarTramoView(Request request, Response response) {
        Miembro miembro = MiembroHelper.getCurrentMiembro(request);

        List<Transporte> transportesDeMiembro = repoTransporte.query("from transporte where duenio = " + miembro.getId());
        List<TransportePublico> tpublicos = repoTransportePublico.buscarTodos();
        List<Parada> paradas = repoParada.buscarTodos();
        List<ServicioContratado> servicioContratados = repoServicioContratado.buscarTodos();
        List<TransporteNoContaminante> noContaminantes = FactoryRepositorio.get(TransporteNoContaminante.class).buscarTodos();
        Set<Organizacion> orgs = miembro.getSectores().stream().map(Sector::getOrganizacion).collect(Collectors.toSet());

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("localidades", localidadList);
        parametros.put("publicos", tpublicos);
        parametros.put("particulares", transportesDeMiembro);
        parametros.put("paradas", paradas);
        parametros.put("contratados", servicioContratados);
        parametros.put("noContaminantes", noContaminantes);
        parametros.put("orgs", orgs);

        return new ModelAndView(parametros, "/trayecto/newTramo.hbs");
    }

    //todo
    public static String tramoView(Request request, Response response) {
        return "To be developed";
    }

    public static Response agregarTramo(Request request, Response response) {

        Transporte transporte = repoTransporte.buscar(Integer.parseInt(request.queryParams("transporte")));
        Trayecto trayecto = repoTrayecto.buscar(Integer.parseInt(request.params("idTrayecto")));
        if (trayecto == null) {
            System.out.println("rompio todo");
            Spark.halt();
        }

        Tramo tramo = new Tramo(transporte,
                determinarDireccion(request, "direccion-inicial"),
                determinarDireccion(request, "direccion-final"));

        agregarOrganizaciones(request, trayecto);
        trayecto.agregarTramo(tramo);
        repoTrayecto.modificar(trayecto);

        response.status(200);
        response.redirect("agregarTramo");
        return response;
    }

    public static Response crearTrayecto(Request request, Response response) {
        Trayecto trayecto = new Trayecto();
        trayecto.getMiembros().add(MiembroHelper.getCurrentMiembro(request));
        repoTrayecto.agregar(trayecto);
        response.redirect("trayecto/" + trayecto.getId() + "/agregarTramo");
        return response;
    }

    private static void agregarOrganizaciones(Request request, Trayecto trayecto) {
        Miembro miembro = MiembroHelper.getCurrentMiembro(request);
        int cantidad = (int) miembro.getSectores().stream().map(Sector::getOrganizacion).count();
        for(int i=0; i<cantidad; i++) {
            if (request.queryParams("org"+i) != null) {
                Organizacion org = FactoryRepositorio.get(Organizacion.class).buscar(Integer.parseInt(request.queryParams("org"+i)));
                trayecto.agregarOrganizacion(org);
            }
        }
    }

    private static Direccion determinarDireccion(Request request, String queryParam) {
        // codigo horrible hecho rapido. Basicamente se fija si existe la direccion
        // despues se fija si existe de otra forma
        // si no existe crea

        if (request.queryParams(queryParam) != null) {
            return repoDireccion.buscar(Integer.parseInt(request.queryParams(queryParam)));
        }

        String query = "from direccion where altura= " + request.queryParams("altura-" + queryParam)
                        + " and calle = '" + request.queryParams("calle-" + queryParam)
                        + "' and localidad = '" + request.queryParams("localidad-" + queryParam) + "'";

        List<Direccion> direccionList = repoDireccion.query(query);

        if (direccionList.size() == 0) {
            Direccion dir = new Direccion(request.queryParams("calle-" + queryParam),
                    Integer.parseInt(request.queryParams("altura-" + queryParam)),
                    repoLocalidad.buscar(Integer.parseInt(request.queryParams("localidad-" + queryParam))));
            repoDireccion.agregar(dir);
            return dir;
        } else {
            return direccionList.get(0);
        }
    }


    public static Response eliminarTrayecto(Request request, Response response) {
        Trayecto trayecto = repoTrayecto.buscar(Integer.parseInt(request.queryParams("trayecto")));
        repoTrayecto.eliminar(trayecto);

        response.redirect("/trayectos/actualizar");
        response.header("Cache-Control", "no-cache, no-store, must-revalidate");
        return response;
    }

    public static Response actualizarTrayectos(Request request, Response response) {
        repoTrayecto.buscarTodos();
        response.redirect("/miembro/" + request.session().attribute("id") + "/trayecto");
        response.header("Cache-Control", "no-cache, no-store, must-revalidate");
        return response;
    }
}
