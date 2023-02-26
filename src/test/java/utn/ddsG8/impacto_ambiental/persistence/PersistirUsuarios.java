package utn.ddsG8.impacto_ambiental.persistence;

import org.junit.jupiter.api.Test;
import utn.ddsG8.impacto_ambiental.domain.estructura.*;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.AgenteSectorial;
import utn.ddsG8.impacto_ambiental.domain.helpers.RoleHelper;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;
import utn.ddsG8.impacto_ambiental.sessions.Role;
import utn.ddsG8.impacto_ambiental.sessions.User;

import java.util.ArrayList;
import java.util.List;

public class PersistirUsuarios {

    private final Repositorio<User> usuariosRepo = FactoryRepositorio.get(User.class);
    private final Repositorio<Miembro> miembros = FactoryRepositorio.get(Miembro.class);
    private final Repositorio<AgenteSectorial> agentes = FactoryRepositorio.get(AgenteSectorial.class);
    private final Repositorio<Organizacion> orgs = FactoryRepositorio.get(Organizacion.class);
    private final Repositorio<Role> roles = FactoryRepositorio.get(Role.class);

    @Test
    public void persistirUsuarios() {
        persistirRoles();

        User miembro = new User("miembro", "miembro");
        miembro.setRole(RoleHelper.getRole("miembro"));
        User agente = new User("agente", "agente");
        agente.setRole(RoleHelper.getRole("agente"));
        User org = new User("org", "org");
        org.setRole(RoleHelper.getRole("organizacion"));

        List<User> usuarios = new ArrayList<>();
        usuarios.add(miembro);
        usuarios.add(agente);
        usuarios.add(org);
        usuarios.addAll(crearUsuariosMiembro(5));
        usuarios.addAll(crearUsuariosOrg(3));
        usuarios.addAll(crearUsuariosAgente(1));
        usuarios.add(crearAdmin());

        usuarios.forEach(u -> usuariosRepo.agregar(u));
        usuarios.stream().filter(u -> u.getRole().getName().equals("organizacion")).forEach(u -> persistirOrganizacion(u));
        usuarios.stream().filter(u -> u.getRole().getName().equals("miembro")).forEach(u -> persistirMiembro(u));
        usuarios.stream().filter(u -> u.getRole().getName().equals("agente")).forEach(u -> persistirAgente(u));

    }


    private void persistirRoles() {
        persistirRol("organizacion");
        persistirRol("admin");
        persistirRol("agente");
        persistirRol("miembro");
    }

    private void persistirRol(String nombre) {
        Role role = new Role(nombre);
        roles.agregar(role);
    }


    public User crearAdmin() {
        User user = new User("admin", "admin");
        user.setRole(RoleHelper.getRole("admin"));
        return user;
    }

    // retorna una lista con un 3 usuarios de nombre miembro + numero, agente + numero, org + numero
    public List<User> crearUsuariosOrg(int n) {
        List<User> usuarios = new ArrayList<>();

        for (int i = 0; i<n; i++) {
            String nombre = Random.nombreUsuarioOrg();
            User org = new User(nombre, nombre);
            org.setRole(RoleHelper.getRole("organizacion"));
            usuarios.add(org);
        }

        return usuarios;
    }


    public List<User> crearUsuariosAgente(int n) {
        List<User> usuarios = new ArrayList<>();

        for (int i = 0; i<n; i++) {
            String nombre = Random.nombreUsuarioAgente();
            User agente = new User(nombre, nombre);
            agente.setRole(RoleHelper.getRole("agente"));
            usuarios.add(agente);
        }

        return usuarios;
    }

    public List<User> crearUsuariosMiembro(int n) {
        List<User> usuarios = new ArrayList<>();

        for (int i = 0; i<n; i++) {
            String nombre = Random.nombreUsuarioMiembro();
            User miembro = new User(nombre, nombre);
            miembro.setRole(RoleHelper.getRole("miembro"));
            usuarios.add(miembro);
        }

        return usuarios;
    }

    public void persistirMiembro(User user) {
        Miembro miembro = new Miembro(user.getUsername(),
                "apellido",
                TipoDoc.values()[Random.intBetween(0, TipoDoc.values().length)],
                String.valueOf(Random.numeroCalle()));
        miembro.setId(user.getId());

        miembros.agregar(miembro);
    }

    public void persistirOrganizacion(User user) {
        Organizacion org = new Organizacion(
                user.getUsername(),
                OrgTipo.values()[Random.intBetween(0, OrgTipo.values().length)],
                Clasificacion.values()[Random.intBetween(0, Clasificacion.values().length)],
                Random.getRandomDireccion()
        );
        org.setId(user.getId());

        orgs.agregar(org);
    }

    public void persistirAgente(User user) {
        AgenteSectorial agente = new AgenteSectorial(user.getUsername());
        agente.setId(user.getId());
        agente.setSectorTerritorial(Random.getRandomSectorTerritorial());
        agentes.agregar(agente);
    }

    @Test
    public void test() {
        Direccion dir = Random.getRandomDireccion();
        System.out.println(dir.getCalle());
    }
}
