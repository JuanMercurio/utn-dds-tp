package utn.ddsG8.impacto_ambiental.middleware;

import spark.Request;
import spark.Response;
import utn.ddsG8.impacto_ambiental.db.EntityManagerHelper;
import utn.ddsG8.impacto_ambiental.domain.estructura.Organizacion;
import utn.ddsG8.impacto_ambiental.domain.helpers.UserHelper;

public class Redirector {

    public static Response aOrg(Request request, Response response) {
        String query = "from organizacion where usuario = ' " + UserHelper.loggedUser(request).getId() + "'";
        Organizacion org = (Organizacion) EntityManagerHelper.getEntityManager().createQuery(query);
        response.redirect("/" + org.getId());
        return null;
    }
}
