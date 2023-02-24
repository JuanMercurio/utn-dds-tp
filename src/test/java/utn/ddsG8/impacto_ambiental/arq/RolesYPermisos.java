package utn.ddsG8.impacto_ambiental.arq;

import org.junit.jupiter.api.Test;
import utn.ddsG8.impacto_ambiental.db.EntityManagerHelper;
import utn.ddsG8.impacto_ambiental.sessions.Permiso;
import utn.ddsG8.impacto_ambiental.sessions.Role;

import java.util.ArrayList;
import java.util.List;

// @Deprecated
public class RolesYPermisos {
    private List<Role> roles;

    public void crearRoles() {

        Role administrador = new Role("admin");
        administrador.agregarPermiso(Permiso.ABM_FE,
                Permiso.ABM_ACTIVIDADES,
                Permiso.ABM_MIEMBROS,
                Permiso.ABM_TRANPORTE,
                Permiso.ABM_TRAYECTOS);

        Role organizacion = new Role("org");
        organizacion.agregarPermiso(Permiso.ABM_MIEMBROS, Permiso.ABM_ACTIVIDADES);

        Role miembro = new Role("miembro");
        miembro.agregarPermiso(Permiso.ABM_MIEMBROS, Permiso.ABM_TRAYECTOS);

        this.roles = new ArrayList<Role>();
        this.roles.add(administrador);
        this.roles.add(organizacion);
        this.roles.add(miembro);

    }

    @Test
    public void subirRoles() {
        crearRoles();

        EntityManagerHelper.beginTransaction();
        this.roles.forEach(r -> EntityManagerHelper.getEntityManager().persist(r));
        EntityManagerHelper.commit();
    }

    @Test
    public void subirPermisos() {

    }

}
