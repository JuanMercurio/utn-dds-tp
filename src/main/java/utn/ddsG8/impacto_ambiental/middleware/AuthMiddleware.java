package utn.ddsG8.impacto_ambiental.middleware;


import spark.Request;
import spark.Response;
import spark.Spark;
import utn.ddsG8.impacto_ambiental.db.EntityManagerHelper;
import utn.ddsG8.impacto_ambiental.domain.estructura.Organizacion;
import utn.ddsG8.impacto_ambiental.helpers.UserHelper;

public class AuthMiddleware {
    public static Response authenticateSession(Request request, Response response) {
        if (request.session().isNew() || request.session().attribute("id") == null) {
            response.redirect("/login");
        }
        return response;
    }

    public static Response authenticateOrg(Request request, Response response) {
        if (!UserHelper.isAdmin(request) && !isOrganization(request)) {
            response.redirect("/prohibido");
            Spark.halt();
        }

        return response;
    }

    public static boolean isOrganization(Request request) {
        String query = "from organizacion where usuario= '" + UserHelper.loggedUser(request).getId() + "'";
        Organizacion org = (Organizacion) EntityManagerHelper.getEntityManager().createQuery(query);

        return Integer.parseInt(request.params("id")) == org.getId();
    }
}
