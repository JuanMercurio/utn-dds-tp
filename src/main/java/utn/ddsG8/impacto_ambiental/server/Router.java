package utn.ddsG8.impacto_ambiental.server;

import jdk.nashorn.internal.runtime.regexp.JoniRegExp;
import spark.ModelAndView;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import utn.ddsG8.impacto_ambiental.controllers.*;
import utn.ddsG8.impacto_ambiental.domain.estructura.Miembro;
import utn.ddsG8.impacto_ambiental.domain.helpers.MiembroHelper;
import utn.ddsG8.impacto_ambiental.domain.helpers.UserHelper;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.Transporte;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.VehiculoParticular;
import utn.ddsG8.impacto_ambiental.middleware.AuthMiddleware;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;
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
        agenteConfig();
        loginConfig();
        prohibidoConfig();
        miembroConfig();
        trayectoConfig();
        orgConfig();

    }

    private static void trayectoConfig() {
        Spark.path("/miembro/:id/trayecto", () -> {
//            Spark.before("/:idTrayecto", AuthMiddleware::trayectoEsDeMiembro);
//            Spark.before("/*", AuthMiddleware::authenticateSession);
//            Spark.before("",  AuthMiddleware::isMiembro);

            Spark.post("/:idTrayecto/eliminar", TrayectoController::eliminarTrayecto);
            Spark.get("/:idTrayecto/agregarTramo", TrayectoController::agregarTramoView, engine);
            Spark.post("/:idTrayecto/agregarTramo", TrayectoController::agregarTramo);
            Spark.post("/:idTrayecto/terminar", (request, response) -> {
                response.redirect("/miembro/" + request.session().attribute("id") + "/trayecto");
                System.out.println("saturno");
                return response;
            });
            Spark.get("/:idTrayecto/tramo/:idtramo", TrayectoController::tramoView);
            Spark.get("/:idTrayecto", TrayectoController::trayectoView, engine);
            Spark.get("", TrayectoController::trayectosMiembroView, engine);
            Spark.post("", TrayectoController::crearTrayecto);

        });

            Spark.get("/verTrayectos", TrayectoController::trayectosMiembroView, engine);
        Spark.get("/vehiculos", TrayectoController::agregarVehiculoView, engine);
        Spark.post("/agregarVehiculo", TrayectoController::agregarVehiculo);
        Spark.post("eliminarVehiculo", TrayectoController::eliminarVehiculo);
    }

    private static void agenteConfig() {
        Spark.path("/createAgente", () -> {

            Spark.get("", AgenteController::createView, engine);
            Spark.post("", AgenteController::save);

        });

        Spark.path("/agente", () -> {
            Spark.before("/*", AuthMiddleware::authenticateSession);
            Spark.before("/:id",  AuthMiddleware::authenticateId);

            Spark.get("/:id", AgenteController::show, engine);
            Spark.get("/:id/sector", AgenteController::showReporteAgente, engine);
            Spark.post("/:id/sector", AgenteController::nuevoHC);
        });
    }

//    private static void trayectoConfig() {
//        Spark.path("/createTrayecto", () -> {
//            Spark.before("", AuthMiddleware::authenticateSession);
//            Spark.get("",  TrayectoController::crearTrayectoView, engine);
//
//        });
//    }

    private static void orgConfig() {
        Spark.path("/createOrganizacion", () -> {
            Spark.get("", OrganizacionController::createView, engine);
            Spark.post("", OrganizacionController::save);
        });

        Spark.path("/organizacion", () -> {
            Spark.before("/*", AuthMiddleware::authenticateSession);
            Spark.before("/:id",  AuthMiddleware::authenticateId);

            Spark.get("/:id", OrganizacionController::show, engine);

//            Spark.get("/:id/edit", OrganizacionController::edit, engine);
            Spark.post("/:id", OrganizacionController::update);

            Spark.get("/:id/subirActividades", OrganizacionController::subirActividadesView, engine);
            Spark.post("/:id/subirActividades", OrganizacionController::subirActividades);

            Spark.get("/:id/solicitudes", OrganizacionController::solicitudes, engine);
            Spark.post("/:id/solicitudes", OrganizacionController::administrarSolicitud);
            Spark.get("/:id/huella", OrganizacionController::vistaCalculadora, engine);
            Spark.post("/:id/huella", OrganizacionController::persistirHuella);
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
            Spark.before("/:id/*",  AuthMiddleware::authenticateId);

            Spark.get("/:id", MiembroController::show, engine);
            Spark.get("/:id/trayectos", TrayectoController::trayectosMiembroView, engine);
            Spark.get("/:id/unirseAOrg", MiembroController::organizacionesParaUnirse, engine);
            Spark.post("/:id/unirseAOrg", MiembroController::unirseAOrg);
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
            Spark.before("", AuthMiddleware::isAdmin);
            Spark.before("/*", AuthMiddleware::isAdmin);

            Spark.get("", (request, response) -> new ModelAndView(null,"admin/admin.hbs"),engine);
            Spark.get("/factoresFE", AdminController::mostrarFactores, engine);
            Spark.post("/factoresFE", AdminController::editarFactor);
            Spark.get("/mostrarTransportes", AdminController::mostrarTransporterPublicosView, engine);
            Spark.post("/mostrarTransportes", AdminController::administrarTransportes);
            Spark.post("/agregandoParada", AdminController::agregarParada);
            Spark.get("/agregarParada", AdminController::agregarParadaView,engine);
            //agregarParadaView
            Spark.get("/actualizarFE", AdminController::actualizarFE);

            Spark.get("/:id", (request, response) -> {
                response.redirect("/admin");
                return response;
            });
//            Spark.get("/*", (request, response) -> {
//                response.redirect("/admin");
//                return response;
//            });
        });
    }
}
