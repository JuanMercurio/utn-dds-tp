package utn.ddsG8.impacto_ambiental.controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.Transporte;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.publico.Colectivo;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.publico.Parada;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrayectoController {

    private static Repositorio<Colectivo> colectivosRepo = FactoryRepositorio.get(Colectivo.class);
    private static Repositorio<Parada> paradaRepo = FactoryRepositorio.get(Parada.class);

    public static ModelAndView createView(Request request, Response response) {
        List<Colectivo> colectivos = colectivosRepo.buscarTodos();
        List<Parada> paradas = paradaRepo.buscarTodos();
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("colectivo", colectivos);
        parametros.put("parada", paradas);
        return new ModelAndView(parametros, "/trayecto/newTrayecto.hbs");
    }
}
