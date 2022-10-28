package utn.ddsG8.impacto_ambiental.server;

import spark.ModelAndView;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import utn.ddsG8.impacto_ambiental.controllers.LoginController;
import utn.ddsG8.impacto_ambiental.controllers.OrganizacionController;
import utn.ddsG8.impacto_ambiental.domain.estructura.Organizacion;
import utn.ddsG8.impacto_ambiental.helpers.PermisoHelper;
import utn.ddsG8.impacto_ambiental.helpers.UserHelper;
import utn.ddsG8.impacto_ambiental.middleware.AuthMiddleware;
import utn.ddsG8.impacto_ambiental.middleware.Redirector;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;
import utn.ddsG8.impacto_ambiental.sessions.Permiso;
import utn.ddsG8.impacto_ambiental.spark.utils.BooleanHelper;
import utn.ddsG8.impacto_ambiental.spark.utils.HandlebarsTemplateEngineBuilder;

import java.util.HashMap;

public class Router {
    private static HandlebarsTemplateEngine engine;

    private static void initEngine() {
        Router.engine = HandlebarsTemplateEngineBuilder
                .create()
                .withDefaultHelpers()
                .withHelper("isTrue", BooleanHelper.isTrue)
                .build();
    }

    public static void init() {
        Router.initEngine();
        Spark.staticFileLocation("/public");
        Router.configure();
    }

    private static void configure(){
        OrganizacionController orgController = new OrganizacionController();

        Spark.get("/prohibido", (request, response) -> "Acceso denegado wacho");

        Spark.path("/login", () -> {
//            Spark.before("", AuthMiddleware::authenticateSession);
            Spark.before("/*", AuthMiddleware::authenticateSession);
            Spark.get("", LoginController::logInResponse, engine);
            Spark.post("", LoginController::login);
        });

        Spark.get("/admin", ((request, response) -> "Congrats sos un admin"));

        Spark.path("/organizacion", () -> {
//            Spark.before("/*", AuthMiddleware::authenticateSession);
//            Spark.before("/:id",  AuthMiddleware::authenticateOrg);
//            Spark.before("/*", Redirector::aOrg);

            // TODO
            Spark.get("", (request, response) -> "azafran");
//            Spark.get("", orgController::showAll, engine);
            Spark.get("/create", orgController::create, engine);
            Spark.post("", orgController::save);
            Spark.get("/:id", orgController::show, engine);
            Spark.get("/:id/edit", orgController::edit, engine);
            Spark.post("/:id", orgController::update);
//            Spark.post("/remove", orgController::remove);
        });
    }
}
