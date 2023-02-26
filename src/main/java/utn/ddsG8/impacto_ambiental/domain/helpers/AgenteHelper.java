package utn.ddsG8.impacto_ambiental.domain.helpers;

import spark.Request;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.AgenteSectorial;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;

public class AgenteHelper {

    public static Repositorio<AgenteSectorial> repositoryAgenteSectorials = FactoryRepositorio.get(AgenteSectorial.class);

    public static AgenteSectorial getLoggerAgent(Request request) {
        return repositoryAgenteSectorials.buscar(UserHelper.loggedUser(request).getId());
    }



}
