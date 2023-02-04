package utn.ddsG8.impacto_ambiental.domain.helpers;

import utn.ddsG8.impacto_ambiental.db.EntityManagerHelper;
import utn.ddsG8.impacto_ambiental.sessions.Role;

public class RoleHelper {
    public static Role getRole(String nombre) {
        return (Role) EntityManagerHelper.getEntityManager().createQuery("from Role where name = '" + nombre + "'" ).getSingleResult();
    }
}
