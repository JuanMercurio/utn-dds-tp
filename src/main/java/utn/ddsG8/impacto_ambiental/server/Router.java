package utn.ddsG8.impacto_ambiental.server;

import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import utn.ddsG8.impacto_ambiental.controllers.LoginController;
import utn.ddsG8.impacto_ambiental.controllers.MiembroController;
import utn.ddsG8.impacto_ambiental.controllers.OrganizacionController;
import utn.ddsG8.impacto_ambiental.middleware.AuthMiddleware;
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
        adminConfig();
        loginConfig();
        prohibidoConfig();
        miembroConfig();
        orgConfig();
    }

    private static void orgConfig() {
        Spark.path("/createOrg", () -> {
            Spark.get("", OrganizacionController::createView, engine);
            Spark.post("", OrganizacionController::save);
        });

        Spark.path("/organizacion", () -> {
            Spark.before("/*", AuthMiddleware::authenticateSession);
            Spark.before("/:id",  AuthMiddleware::authenticateId);

            Spark.get("/:id", OrganizacionController::show, engine);
            Spark.get("/:id/edit", OrganizacionController::edit, engine);
            Spark.post("/:id", OrganizacionController::update);
//            Spark.post("/remove", orgController::remove);
        });
    }

    private static void miembroConfig() {
        Spark.path("/createMiembro", () -> {
            Spark.get("", MiembroController::createView, engine);
            Spark.post("", MiembroController::save);
        });

        Spark.path("/miembro", () -> {
            Spark.before("/*", AuthMiddleware::authenticateSession);
            Spark.before("/:id",  AuthMiddleware::authenticateId);

            Spark.get("/:id", MiembroController::show, engine);
        });
    }

    private static void prohibidoConfig() {
        Spark.get("/prohibido", (request, response) -> "Acceso denegado wacho");
    }

    private static void loginConfig() {
        Spark.path("/login", () -> {
            Spark.get("", LoginController::logInView, engine);
            Spark.post("", LoginController::login);
        });
    }

    private static void adminConfig() {
        Spark.path("/admin", () -> {
            Spark.get("", (request, response) -> "Sos un admin wacho");
            Spark.get("/*", (request, response) -> {
                response.redirect("/admin");
                return response;
            });
        });
    }
}
