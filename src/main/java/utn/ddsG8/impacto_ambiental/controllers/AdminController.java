package utn.ddsG8.impacto_ambiental.controllers;

import jdk.nashorn.internal.runtime.regexp.JoniRegExp;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import utn.ddsG8.impacto_ambiental.domain.calculos.FE;
import utn.ddsG8.impacto_ambiental.domain.estructura.*;
import utn.ddsG8.impacto_ambiental.domain.movilidad.Tramo;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.Transporte;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.TransporteNoContaminante;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.publico.Parada;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.publico.TransportePublico;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.Localidad;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;
import utn.ddsG8.impacto_ambiental.sessions.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class AdminController {

    private static final Repositorio<FE> factores = FactoryRepositorio.get(FE.class);
    private final static Repositorio<Parada> repoParada = FactoryRepositorio.get(Parada.class);

    public static ModelAndView mostrarFactores(Request request, Response response) {
        List<FE> factoresList = factores.buscarTodos();
        return new ModelAndView(new HashMap<String, Object>(){{
            put("factores", factoresList);
        }}, "admin/factoresFE.hbs");
    }

    public static Response editarFactor(Request request, Response response) {
        if (request.queryParams("form").equals("nuevo-FE")) {
            FE fe = new FE(request.queryParams("nombre-nuevo-fe"), Double.parseDouble(request.queryParams("valor-nuevo-fe")));
            factores.agregar(fe);
        } else {
            FE fe = factores.buscar(Integer.parseInt(request.queryParams("id")));
            fe.setValor(Double.parseDouble(request.queryParams("valor")));
            factores.modificar(fe);
        }
        response.redirect("/admin/actualizarFE");
//        response.redirect("/admin/factoresFE");
        return response;
    }

    public static Response actualizarFE(Request req, Response res) {
//        factores.buscarTodos();
        res.redirect("/admin/factoresFE");
        return res;
    }
    public static Response agregarTransporte(Request request, Response response){

        //System.out.println("Entro a agregar transporte");
        //response.redirect("admin/admin.hbs");
        //response.redirect("/agregarTransportes.hbs");
        //verTransporterView(request,response);
        //todo: agregar transporte
        System.out.println("Tipo transporte"+request.queryParams("tipoTransporte"));
        System.out.println("nombreTransporte "+request.queryParams("nombreTransporte"));
        System.out.println("nombreFE "+request.queryParams("nombreFE"));
        if(request.queryParams("tipoTransporte").equals("transportepublico")){
            TransportePublico transporteNuevo = new TransportePublico(request.queryParams("nombreTransporte"),request.queryParams("tipoTransporte"),request.queryParams("nombreFE"));

        }
        else if (request.queryParams("tipoTransporte").equals("otro")){
            TransporteNoContaminante transporteNuevo = new TransporteNoContaminante(request.queryParams("nombreTransporte"),request.queryParams("nombreFE"));


        }
        /*if()
        Transporte nuevoTransporte = new Transporte(
                request.queryParams("nombreTransporte"),
                request.queryParams("nombreFE"), request.queryParams("tipoTransporte")

        );*/

        response.redirect("mostrarTransportes");
        return response;

    }

    public static Response agregarParada(Request request, Response response) {
        System.out.println("LLEGO");
        Direccion dir = new Direccion(
                request.queryParams("calle"),
                Integer.parseInt(request.queryParams("altura")),
                FactoryRepositorio.get(Localidad.class).buscar(Integer.parseInt(request.queryParams("localidad"))));

        Parada newparada = new Parada(
                request.queryParams("nombre-parada"),
                dir,
                Integer.parseInt(request.queryParams("distancia-proxima-parada")),
                Integer.parseInt(request.queryParams("distancia-anterior-parada")));

        dir.setParada(newparada);

        TransportePublico tp = FactoryRepositorio.get(TransportePublico.class).buscar(Integer.parseInt(request.params("idTransporte")));
        tp.agregarParada(newparada, Integer.parseInt(request.queryParams("numero-parada")));

        System.out.println(tp.getParadas().size() + " tamanio");
        tp.getParadas().forEach(p -> System.out.println(p.getNombre()));

        //FactoryRepositorio.get(Direccion.class).agregar(dir);
        FactoryRepositorio.get(TransportePublico.class).modificar(tp);



        response.redirect("/admin/mostrarTransportes");
        return null;
    }

    public static ModelAndView agregarParadaView(Request request, Response response) {
        List<TransportePublico> transportes = FactoryRepositorio.get(TransportePublico.class).buscarTodos();
        List<Localidad> localidades = FactoryRepositorio.get(Localidad.class).buscarTodos();
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("localidad", localidades);
        parametros.put("transporte", transportes);
        return new ModelAndView(parametros, "/admin/agregarParada.hbs");
    }

    public static Response administrarTransportes(Request request, Response response) {
        int idTransporte = Integer.parseInt(request.queryParams("id_transporte"));

        System.out.println("Entro a administrarTransportes");
        // todo... hay que notificar?

        if (request.queryParams("estado").equals("agregarParada")) {
            String id = request.params("idTransporte");
            response.redirect("mostrarTransportes/"+idTransporte+"/agregarParada");
            System.out.println("Entro a agregar parada");

        }
        else if (request.queryParams("estado").equals("eliminar")){
            String id = request.params("idTransporte");
            response.redirect("mostrarTransportes");
            System.out.println("Entro a eliminar");
        }
        else if (request.queryParams("estado").equals("ver")){
            response.redirect("mostrarTransportes/"+idTransporte+"/verTransporte");
            System.out.println("Entro a modificar");
        }
        else {
            System.out.println("Entro al else");
            response.redirect("mostrarTransportes");

        }


        return response;
    }
    public static ModelAndView mostrarAgregarTransporterView(Request request, Response response) {
        //List<TransportePublico> transportes = FactoryRepositorio.get(TransportePublico.class).buscarTodos();
       List<Transporte> transportes = FactoryRepositorio.get(Transporte.class).buscarTodos();
        List<Localidad> localidades = FactoryRepositorio.get(Localidad.class).buscarTodos();
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("localidad", localidades);
        parametros.put("transporte", transportes);

        return new ModelAndView(parametros, "/admin/agregarTransporte.hbs");
    }


    public static ModelAndView mostrarTransporterPublicosView(Request request, Response response) {
        //List<TransportePublico> transportes = FactoryRepositorio.get(TransportePublico.class).buscarTodos();
        List<Transporte> transportes = FactoryRepositorio.get(Transporte.class).buscarTodos();
        List<Localidad> localidades = FactoryRepositorio.get(Localidad.class).buscarTodos();
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("localidad", localidades);
        parametros.put("transporte", transportes);
        return new ModelAndView(parametros, "/admin/mostrarTransportes.hbs");
    }
    public static ModelAndView verTransporterView(Request request, Response response) {
        List<Transporte> transportes = FactoryRepositorio.get(Transporte.class).buscarTodos();
        Transporte tran = transportes.stream().filter(t-> t.getId() == Integer.parseInt(request.params("idTransporte"))).collect(Collectors.toList()).get(0);
        List<Parada> paradas = repoParada.buscarTodos();
        List<Parada> paradasTransporte = new ArrayList<>();
        for (Parada para: paradas) {
            if( para.getNombre().contains(tran.getNombre())){
                paradasTransporte.add(para);
            }
        }
        // QUE FILTRE TU TRANSPORTE
        //Integer.parseInt(request.params("idTransporte"))
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("transporte", tran);
        parametros.put("paradas", paradasTransporte);
        return new ModelAndView(parametros, "/admin/verTransporte.hbs");
    }

    public static ModelAndView createView(Request request, Response respose) {
        return new ModelAndView(null, "/admin/createAdmin.hbs");
    }

    public static String save(Request request, Response response) {
        return "El registro de admin se encuentra desactivado vuelva a intentar luego";
    }
}