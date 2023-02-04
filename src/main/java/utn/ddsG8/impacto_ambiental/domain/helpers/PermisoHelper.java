package utn.ddsG8.impacto_ambiental.domain.helpers;

import spark.Request;
import utn.ddsG8.impacto_ambiental.sessions.Permiso;

public class PermisoHelper {
    public static Boolean userHasPermissions(Request request, Permiso... permisos) {
        return UserHelper
                .loggedUser(request)
                .getRole()
                .tieneTodosLosPermisos(permisos);
    }
}
