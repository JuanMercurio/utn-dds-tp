package utn.ddsG8.impacto_ambiental.controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import utn.ddsG8.impacto_ambiental.domain.calculos.CalcularHC;
import utn.ddsG8.impacto_ambiental.domain.helpers.AgenteHelper;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.AgenteSectorial;
import utn.ddsG8.impacto_ambiental.domain.helpers.RoleHelper;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;
import utn.ddsG8.impacto_ambiental.sessions.User;

import java.util.HashMap;
import java.util.Map;

public class AgenteController {

    public final static Repositorio<AgenteSectorial> agentes = FactoryRepositorio.get(AgenteSectorial.class);

    public static ModelAndView createView(Request req, Response res) {
        return new ModelAndView(null, "agenteSectorial/newAgente.hbs");
    }

    public static Response save(Request request, Response response) {

        // TODO unificar los save de los controllers para que no se repita el codigo
        User user = UserController.create(request, response);
        if (user == null) return response;

        user.setRole(RoleHelper.getRole("agente"));
        Repositorio<User> users = FactoryRepositorio.get(User.class);
        users.modificar(user);

        // TODO falta que agregue su sector territorial
        AgenteSectorial newAgente = new AgenteSectorial(request.queryParams("nombre"));
        newAgente.setId(user.getId());
        agentes.agregar(newAgente);

        response.redirect("/agente/" + user.getId());

        return response;
    }

    public static ModelAndView show(Request request, Response response) {
        AgenteSectorial agente = agentes.buscar(new Integer(request.params("id")));
        return new ModelAndView(new HashMap<String, Object>(){{
            put("agente", agente);
        }}, "agenteSectorial/agente.hbs");
    }

    //TODO falta agregar bastante aca
    public static ModelAndView showReporteAgente(Request request, Response response) {
        AgenteSectorial agente = AgenteHelper.getLoggerAgent(request);
        double huella = CalcularHC.getInstancia().obtenerHCSectorTerritorial(agente.getSectorTerritorial());
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("agente", agente);
        parametros.put("huella", huella);
        return new ModelAndView(parametros, "agenteSectorial/sector.hbs");
    }
}


