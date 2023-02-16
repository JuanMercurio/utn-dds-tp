package utn.ddsG8.impacto_ambiental.controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import utn.ddsG8.impacto_ambiental.domain.calculos.CalcularHC;
import utn.ddsG8.impacto_ambiental.domain.calculos.Huella;
import utn.ddsG8.impacto_ambiental.domain.helpers.AgenteHelper;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.*;
import utn.ddsG8.impacto_ambiental.domain.helpers.RoleHelper;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;
import utn.ddsG8.impacto_ambiental.sessions.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AgenteController {

    public final static Repositorio<AgenteSectorial> agentes = FactoryRepositorio.get(AgenteSectorial.class);

    public static ModelAndView createView(Request req, Response res) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("municipios", FactoryRepositorio.get(Municipio.class).buscarTodos());
        parametros.put("localidades", FactoryRepositorio.get(Localidad.class).buscarTodos());
        parametros.put("pronvincias", FactoryRepositorio.get(Provincia.class).buscarTodos());
        return new ModelAndView(parametros, "agenteSectorial/newAgente.hbs");
    }

    public static Response save(Request request, Response response) {

        User user = UserController.create(request, response);
        if (user == null) return response;

        user.setRole(RoleHelper.getRole("agente"));
        Repositorio<User> users = FactoryRepositorio.get(User.class);
        users.modificar(user);

        SectorTerritorial sector = FactoryRepositorio.get(SectorTerritorial.class).buscar(Integer.valueOf(request.queryParams("sector")));
        AgenteSectorial newAgente = new AgenteSectorial(request.queryParams("nombre"));
        newAgente.setSectorTerritorial(sector);
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
        List<Huella> huellas = agente.getSectorTerritorial().huellas;
        double porcentajeSobrePais = CalcularHC.getInstancia().porcentajeHCSectorTerritorialEnPais(agente.getSectorTerritorial());

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("agente", agente);
        parametros.put("huella", (double) Math.round(huella*100.0)/100);
        parametros.put("porcentaje", ((double) Math.round(porcentajeSobrePais*100)/100) + " %");
        parametros.put("huellas", huellas);
        return new ModelAndView(parametros, "agenteSectorial/sector.hbs");
    }


    public static Response nuevoHC(Request request, Response response) {

        AgenteSectorial agente = AgenteHelper.getLoggerAgent(request);
        agente.getSectorTerritorial().persistirHC();
        response.status(200);
        response.redirect(request.uri());
        return null;
    }
}


