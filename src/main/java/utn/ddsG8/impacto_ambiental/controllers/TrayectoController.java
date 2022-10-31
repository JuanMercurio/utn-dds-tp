package utn.ddsG8.impacto_ambiental.controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.Transporte;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.publico.Colectivo;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;

import java.util.HashMap;

public class TrayectoController {

    private static Repositorio<Colectivo> colectivos = FactoryRepositorio.get(Colectivo.class);

    public static ModelAndView createView(Request request, Response response) {
        colectivos.buscarTodos().forEach(c -> System.out.println("tenemos a el colectivo: " + c.getNombre()));
        return new ModelAndView(new HashMap<String, Object>(){{
            put("colectivo", colectivos.buscarTodos());
        }}, "/trayecto/newTrayecto.hbs");
    }
}
