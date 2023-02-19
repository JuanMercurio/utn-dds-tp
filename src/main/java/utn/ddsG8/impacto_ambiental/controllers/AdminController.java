package utn.ddsG8.impacto_ambiental.controllers;

import jdk.nashorn.internal.runtime.regexp.JoniRegExp;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import utn.ddsG8.impacto_ambiental.domain.calculos.FE;
import utn.ddsG8.impacto_ambiental.domain.estructura.Direccion;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.publico.Parada;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.publico.TransportePublico;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.Localidad;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminController {

    private static final Repositorio<FE> factores = FactoryRepositorio.get(FE.class);

    public static ModelAndView mostrarFactores(Request request, Response response) {
        List<FE> factoresList = factores.buscarTodos();
        return new ModelAndView(new HashMap<String, Object>(){{
            put("factores", factoresList);
        }}, "admin/factoresFE.hbs");
    }

    // TODO pregunta. Cuando se edita el factor y el redirect es a /admin/factoresFE el dato no cambia en la pantalla
    // si le damos refresh si cambia. El dato queda en memoria? Como hacer para que vaya al server siempre
    // esto lo solucione haciendo un redicrect a actulizarFE donde pide todos los factores de nuevo.
    // No es una solucin elegante o como se debe hacer supongo. Preguntar a pablo
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

    public static Response agregarParada(Request request, Response response) {

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

        TransportePublico tp = FactoryRepositorio.get(TransportePublico.class).buscar(Integer.parseInt(request.queryParams("transporte")));
        tp.agregarParada(newparada, Integer.parseInt(request.queryParams("numero-parada")));

        System.out.println(tp.getParadas().size() + " tamanio");
        tp.getParadas().forEach(p -> System.out.println(p.getNombre()));

//        FactoryRepositorio.get(TransportePublico.class).modificar(tp);

//        FactoryRepositorio.get(Direccion.class).agregar(dir);

        response.redirect("admin/admin.hbs");
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
}
