package utn.ddsG8.impacto_ambiental.arq;

import org.junit.jupiter.api.Test;
import utn.ddsG8.impacto_ambiental.db.EntityManagerHelper;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;
import utn.ddsG8.impacto_ambiental.sessions.Permiso;
import utn.ddsG8.impacto_ambiental.sessions.Role;
import utn.ddsG8.impacto_ambiental.sessions.User;


public class admin {

    public Role crearAdminYpersistir() {

        Role admin = new Role("admin");
        admin.agregarPermiso(Permiso.ABM_MIEMBROS,
                Permiso.ABM_ACTIVIDADES,
                Permiso.ABM_TRAYECTOS,
                Permiso.ABM_TRANPORTE,
                Permiso.ABM_FE);

        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(admin);
        EntityManagerHelper.commit();

        return admin;
    }

    @Test
    public void createAdminUser() {
        User admin = new User("admin", "admin");
        Repositorio<User> usuarios = FactoryRepositorio.get(User.class);

        Role adminRol = (Role) EntityManagerHelper.getEntityManager().createQuery("from Role where name = 'admin'").getSingleResult();

        if (adminRol == null) {
             adminRol = crearAdminYpersistir();
        }

        admin.setRole(adminRol);

        usuarios.agregar(admin);
    }
}
