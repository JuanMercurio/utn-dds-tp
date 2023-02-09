package utn.ddsG8.impacto_ambiental.controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import utn.ddsG8.impacto_ambiental.domain.calculos.FE;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;

import java.util.HashMap;
import java.util.List;

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
        return response;
    }

    public static Response actualizarFE(Request req, Response res) {
        factores.buscarTodos();
        res.redirect("/admin/factoresFE");
        return res;
    }
}
