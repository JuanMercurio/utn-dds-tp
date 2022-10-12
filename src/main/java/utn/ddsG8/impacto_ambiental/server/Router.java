package utn.ddsG8.impacto_ambiental.server;

import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import utn.ddsG8.impacto_ambiental.controllers.OrganizacionController;
import utn.ddsG8.impacto_ambiental.helpers.PermisoHelper;
import utn.ddsG8.impacto_ambiental.middleware.AuthMiddleware;
import utn.ddsG8.impacto_ambiental.sessions.Permiso;
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
        OrganizacionController orgController = new OrganizacionController();

        Spark.path("/login", () -> {

        });

        Spark.path("/organizacion", () -> {
            //authenticar las sessiones
            Spark.before("", AuthMiddleware::authenticateSession);
            Spark.before("/*", AuthMiddleware::authenticateSession);

            //autenticar los permisos
            //TODO se puede hacer directamente con roles, o con permisos
            Spark.before("", (((request, response) -> {
                if(!PermisoHelper.userHasPermissions(request, Permiso.EJEMPLO)){
                    response.redirect("/prohibido");
                    Spark.halt();
                }
            })));

            Spark.get("", orgController::showAll, engine);
            Spark.get("/create", orgController::create, engine);
            Spark.post("", orgController::save);
            Spark.get("/:id", orgController::show, engine);
            Spark.get("/:id/edit", orgController::edit, engine);
            Spark.post("/:id", orgController::update);
//            Spark.post("/remove", orgController::remove);
        });
    }
}
