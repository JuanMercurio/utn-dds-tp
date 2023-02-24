package utn.ddsG8.impacto_ambiental.controllers;

import spark.*;
import utn.ddsG8.impacto_ambiental.db.Persistable;
import utn.ddsG8.impacto_ambiental.domain.estructura.Direccion;
import utn.ddsG8.impacto_ambiental.domain.estructura.Miembro;
import utn.ddsG8.impacto_ambiental.domain.estructura.Organizacion;
import utn.ddsG8.impacto_ambiental.domain.estructura.Sector;
import utn.ddsG8.impacto_ambiental.domain.helpers.MiembroHelper;
import utn.ddsG8.impacto_ambiental.domain.movilidad.Tramo;
import utn.ddsG8.impacto_ambiental.domain.movilidad.Trayecto;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.ServicioContratado;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.Transporte;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.TransporteNoContaminante;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.VehiculoParticular;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.combustibles.CombustibleE;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.publico.Parada;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.publico.TransportePublico;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.Localidad;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;

import java.util.*;
import java.util.stream.Collectors;

public class TrayectoController {

    private final static Repositorio<Parada> repoParada = FactoryRepositorio.get(Parada.class);
    private final static Repositorio<Miembro> repoMiembro = FactoryRepositorio.get(Miembro.class);
    private final static Repositorio<TransportePublico> repoTransportePublico = FactoryRepositorio.get(TransportePublico.class);
    private final static Repositorio<Transporte> repoTransporte = FactoryRepositorio.get(Transporte.class);
    private final static Repositorio<Trayecto> repoTrayecto = FactoryRepositorio.get(Trayecto.class);
    private final static Repositorio<Direccion> repoDireccion = FactoryRepositorio.get(Direccion.class);
    private final static Repositorio<Localidad> repoLocalidad = FactoryRepositorio.get(Localidad.class);
    private final static Repositorio<ServicioContratado> repoServicioContratado = FactoryRepositorio.get(ServicioContratado.class);

    public static ModelAndView trayectosMiembroView(Request request, Response response) {
        Miembro miembro = repoMiembro.buscar(request.session().attribute("id"));
        double huella = miembro.calcularHC();
        response.header("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
        response.header("Pragma", "no-cache");
        miembro.setTrayectos(miembro.getTrayectos().stream().sorted(Comparator.comparing(Trayecto::getFecha).reversed()).collect(Collectors.toCollection(LinkedHashSet::new)));
        return new ModelAndView(new HashMap<String, Object>(){{
                put("miembro", miembro);
                put("huella", huella);
        }}, "trayecto/trayectosMiembro.hbs");
    }

    public static ModelAndView trayectoView(Request request, Response response) {

        Trayecto trayecto = repoTrayecto.buscar(Integer.parseInt(request.params("idTrayecto")));
        Map<String, Object> parametros = new HashMap<>();
        double huella = trayecto.getHuella();
        parametros.put("trayecto", trayecto);
        parametros.put("huella", (double) Math.round(huella*100.0)/100);
        return new ModelAndView(parametros, "trayecto/trayecto.hbs");
    }

    public static ModelAndView agregarTramoView(Request request, Response response) {
        Miembro miembro = repoMiembro.buscar(request.session().attribute("id"));

        List<Transporte> transportesDeMiembro = repoTransporte.query("from transporte where duenio = " + miembro.getId());
        List<TransportePublico> tpublicos = repoTransportePublico.buscarTodos();
        List<Parada> paradas = repoParada.buscarTodos();
        List<ServicioContratado> servicioContratados = repoServicioContratado.buscarTodos();
        List<TransporteNoContaminante> noContaminantes = FactoryRepositorio.get(TransporteNoContaminante.class).buscarTodos();
//        List<Localidad> localidadList = repoLocalidad.buscarTodos();//.stream().filter(l -> l.id_db % 2 == 0).collect(Collectors.toList());
        Set<Organizacion> orgs = miembro.getSectores().stream().map(Sector::getOrganizacion).collect(Collectors.toSet());

        Map<String, Object> parametros = new HashMap<>();
//        parametros.put("localidades", localidadList.subList(0, 100)); sacamos porque no carga cuando esta desplegado
        parametros.put("publicos", tpublicos);
        parametros.put("particulares", transportesDeMiembro);
        parametros.put("paradas", paradas);
        parametros.put("contratados", servicioContratados);
        parametros.put("noContaminantes", noContaminantes);
        parametros.put("orgs", orgs);

        return new ModelAndView(parametros, "trayecto/newTramo.hbs");
    }

    //todo
    public static String tramoView(Request request, Response response) {
        return "To be developed";
    }

    public static Response agregarTramo(Request request, Response response) {


        Transporte transporte;
        try {
            transporte = repoTransporte.buscar(Integer.parseInt(request.queryParams("transporte")));
        } catch (Exception e) {
            transporte = repoTransporte.buscar(Integer.parseInt(request.queryParams("transportePublico")));
        }


        Trayecto trayecto = repoTrayecto.buscar(Integer.parseInt(request.params("idTrayecto")));
        if (trayecto == null) {
            System.out.println("rompio todo el id del trayecto esa: "+ request.params("idTrayecto"));
            Spark.halt();
        }

        Direccion direccionInicial;
        Direccion direccionFinal;

        if (request.queryParams("direccion-inicial") != null) {
            direccionInicial = repoDireccion.buscar(Integer.parseInt(request.queryParams("direccion-inicial")));
            direccionFinal = repoDireccion.buscar(Integer.parseInt(request.queryParams("direccion-final")));
        } else {


            List<Localidad> localidades = repoLocalidad.buscarTodos();
            List<Localidad> localidadInicial = localidades.stream().filter(l -> l.getNombre().equals(request.queryParams("localidad-direccion-inicial").toUpperCase())).collect(Collectors.toList());
            List<Localidad> localidadFinal = localidades.stream().filter(l -> l.getNombre().equals(request.queryParams("localidad-direccion-final").toUpperCase())).collect(Collectors.toList());

            if (localidadFinal.size() == 0 || localidadInicial.size() == 0) {
                response.body("error");
                response.status(500);
                return response;
            }

            direccionInicial = new Direccion(request.queryParams("calle-direccion-inicial"),
                Integer.parseInt(request.queryParams("altura-direccion-inicial")),
                localidadInicial.get(0));

            repoDireccion.agregar(direccionInicial);

            direccionFinal = new Direccion(request.queryParams("calle-direccion-final"),
                Integer.parseInt(request.queryParams("altura-direccion-final")),
                localidadFinal.get(0));

            repoDireccion.agregar(direccionFinal);
        }


        Tramo tramo = new Tramo(transporte, direccionInicial, direccionFinal);

        agregarOrganizaciones(request, trayecto);
        trayecto.agregarTramo(tramo);
        repoTrayecto.modificar(trayecto);

        response.status(200);
        response.redirect("agregarTramo");
        return response;
    }

    public static Response crearTrayecto(Request request, Response response) {
        Trayecto trayecto = new Trayecto();
        Miembro miembro = repoMiembro.buscar(request.session().attribute("id"));
        trayecto.getMiembros().add(miembro);
        repoTrayecto.agregar(trayecto);
        response.redirect("trayecto/" + trayecto.getId() + "/agregarTramo");
        return response;
    }

    private static void agregarOrganizaciones(Request request, Trayecto trayecto) {
        Miembro miembro = repoMiembro.buscar(request.session().attribute("id"));
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
        Trayecto trayectoEliminar = repoTrayecto.buscar(Integer.parseInt(request.params("idTrayecto")));
        repoTrayecto.eliminar(trayectoEliminar);
        response.redirect("/miembro/" + request.session().attribute("id") + "/trayecto");
        return response;
    }

    public static ModelAndView agregarVehiculoView(Request request, Response response) {
        Miembro miembro = MiembroHelper.getCurrentMiembro(request);
        List<Transporte> vehiculos = repoTransporte.query("from transporte where duenio = " + miembro.getId());
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("vehiculos", vehiculos);
        return new ModelAndView(parametros, "trayecto/vehiculos.hbs");
    }

    public static Response agregarVehiculo(Request request, Response response) {

        VehiculoParticular v = new VehiculoParticular(
                request.queryParams("nombre"),
                request.queryParams("tipo-transporte"),
                request.queryParams("tipo-transporte"),
                CombustibleE.valueOf(request.queryParams("combustible").toUpperCase()),
                Double.parseDouble(request.queryParams("consumo"))
        );
        v.setDuenio(MiembroHelper.getCurrentMiembro(request));

        FactoryRepositorio.get(VehiculoParticular.class).agregar(v);
        response.redirect("/vehiculos");
        return response;
    }

    public static Response eliminarVehiculo(Request request, Response response) {
        Transporte t = repoTransporte.buscar(Integer.parseInt(request.queryParams("transporte")));
        repoTransporte.eliminar(t);
        response.redirect("vehiculos");
        return response;
    }

    public static ModelAndView unirseATrayectoView(Request request, Response response) {
        Miembro miembro = MiembroHelper.getCurrentMiembro(request);
        List<Trayecto> trayectos = MiembroHelper.getCurrentMiembro(request).getSectores().stream().map(s -> s.getOrganizacion()).flatMap(o -> o.getTrayectos().stream()).collect(Collectors.toList());
        List<Trayecto> trayectosFiltrados = new ArrayList<>();

        for (Trayecto trayecto : trayectos) {
           if (trayectosFiltrados.stream().filter(t -> t.getId() == trayecto.getId()).count() == 0)  {
               trayectosFiltrados.add(trayecto);
           }
        }

        List<Trayecto> trayectosSorteados = trayectosFiltrados.stream()
                .sorted(Comparator.comparingInt(Persistable::getId))
                .filter(t -> t.getMiembros().stream().filter(m -> m.getId() == miembro.getId()).count() == 0)
                .collect(Collectors.toList());

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("trayectos", trayectosSorteados);
        return new ModelAndView(parametros, "miembro/unirseATrayecto.hbs");
    }

    public static Response unirseATrayecto(Request request, Response response) {

        Trayecto trayecto = repoTrayecto.buscar(Integer.parseInt(request.queryParams("trayecto")));
        Miembro miembro = MiembroHelper.getCurrentMiembro(request);
        trayecto.agregarMiembro(miembro);
        repoTrayecto.modificar(trayecto);

        response.redirect("/miembro/" + request.session().attribute("id") + "/trayectos");
        return response;
    }
}
