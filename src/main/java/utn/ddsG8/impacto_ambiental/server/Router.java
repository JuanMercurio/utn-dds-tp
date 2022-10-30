package utn.ddsG8.impacto_ambiental.server;

import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import utn.ddsG8.impacto_ambiental.controllers.LoginController;
import utn.ddsG8.impacto_ambiental.controllers.OrganizacionController;
import utn.ddsG8.impacto_ambiental.controllers.UserController;
import utn.ddsG8.impacto_ambiental.spark.utils.BooleanHelper;
import utn.ddsG8.impacto_ambiental.spark.utils.HandlebarsTemplateEngineBuilder;

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

        Spark.get("/prohibido", (request, response) -> "Acceso denegado wacho");

        Spark.path("/login", () -> {
//            Spark.before("", AuthMiddleware::authenticateSession);
//            Spark.before("/*", AuthMiddleware::authenticateSession);
            Spark.get("", LoginController::logInView, engine);
            Spark.post("", LoginController::login);
        });

        Spark.path("/createOrg", () -> {
            Spark.get("", OrganizacionController::createView, engine);
            Spark.post("", OrganizacionController::save);

//            Spark.post("/miembro", MiembroController::save);
        });

        Spark.get("/admin", ((request, response) -> "Congrats sos un admin"));

        Spark.path("/organizacion", () -> {
//            Spark.before("/*", AuthMiddleware::authenticateSession);
//            Spark.before("/:id",  AuthMiddleware::authenticateOrg);
//            Spark.before("/*", Redirector::aOrg);

            // TODO
            Spark.get("", (request, response) -> "azafran");
//            Spark.get("", orgController::showAll, engine);
            Spark.post("", OrganizacionController::save);
            Spark.get("/:id", OrganizacionController::show, engine);
            Spark.get("/:id/edit", OrganizacionController::edit, engine);
            Spark.post("/:id", OrganizacionController::update);
//            Spark.post("/remove", orgController::remove);
        });
    }
}
