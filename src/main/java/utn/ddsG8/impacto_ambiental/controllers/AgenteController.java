package utn.ddsG8.impacto_ambiental.controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import utn.ddsG8.impacto_ambiental.domain.calculos.CalcularHC;
import utn.ddsG8.impacto_ambiental.domain.calculos.Huella;
import utn.ddsG8.impacto_ambiental.domain.estructura.Clasificacion;
import utn.ddsG8.impacto_ambiental.domain.estructura.Organizacion;
import utn.ddsG8.impacto_ambiental.domain.helpers.AgenteHelper;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.*;
import utn.ddsG8.impacto_ambiental.domain.helpers.RoleHelper;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;
import utn.ddsG8.impacto_ambiental.sessions.User;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

public class AgenteController {

    public final static Repositorio<AgenteSectorial> agentes = FactoryRepositorio.get(AgenteSectorial.class);
    public final static Repositorio<Organizacion> repoOrganizacion = FactoryRepositorio.get(Organizacion.class);

    public static ModelAndView createView(Request req, Response res) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("municipios", FactoryRepositorio.get(Municipio.class).buscarTodos());
        parametros.put("localidades", FactoryRepositorio.get(Localidad.class).buscarTodos());
        parametros.put("pronvincias", FactoryRepositorio.get(Provincia.class).buscarTodos());
        return new ModelAndView(parametros, "agenteSectorial/newAgente.hbs");
    }

    public static Response save(Request request, Response response) {

        User user = UserController.crearUsuario(request, response);
        if (user == null) return response;

        SectorTerritorial sector = FactoryRepositorio.get(SectorTerritorial.class).buscar(Integer.parseInt(request.queryParams("sector")));
        AgenteSectorial newAgente = new AgenteSectorial(request.queryParams("nombre"));
        newAgente.setSectorTerritorial(sector);
        newAgente.setId(user.getId());
        agentes.agregar(newAgente);

        return response;
    }

    public static ModelAndView show(Request request, Response response) {
        AgenteSectorial agente = agentes.buscar(new Integer(request.params("id")));
        List<Organizacion> orgs = repoOrganizacion.buscarTodos().stream().filter(o -> o.perteneceASector(agente.getSectorTerritorial())).collect(Collectors.toList());
        return new ModelAndView(new HashMap<String, Object>(){{
            put("orgs", orgs);
            put("agente", agente);
        }}, "agenteSectorial/agente.hbs");
    }

    public static ModelAndView showReporteAgente(Request request, Response response) {
        AgenteSectorial agente = AgenteHelper.getLoggerAgent(request);
        double huella = CalcularHC.getInstancia().obtenerHCSectorTerritorial(agente.getSectorTerritorial());
        List<Huella> huellas = agente.getSectorTerritorial().huellas.stream().sorted(Comparator.comparing(Huella::getFecha).reversed()).collect(Collectors.toList());
        double porcentajeSobrePais = CalcularHC.getInstancia().porcentajeHCSectorTerritorialEnPais(agente.getSectorTerritorial());

        Map<String, Object> parametros = new HashMap<>();

        for (Clasificacion clasificacion : Clasificacion.values()) {
            double h = CalcularHC.getInstancia().obtenerHC(agente.getSectorTerritorial(), clasificacion);
            parametros.put("HC" + clasificacion.name(),  new BigDecimal(h).setScale(2, RoundingMode.CEILING));
        }

        parametros.put("agente", agente);
        parametros.put("huella", new BigDecimal(huella).setScale(2, RoundingMode.CEILING));
        parametros.put("porcentajeSobrePais", ((double) Math.round(porcentajeSobrePais*100)/100) + " %");
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
    public static ModelAndView mostrarRecomendacionesView(Request request, Response respose) {
        return new ModelAndView(null, "/agenteSectorial/recomendaciones.hbs");
    }
}


