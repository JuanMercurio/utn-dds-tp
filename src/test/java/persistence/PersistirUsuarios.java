package persistence;

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
        List<User> usuarios = new ArrayList<>();
        usuarios.addAll(crearUsuariosMiembro(5));
        usuarios.addAll(crearUsuariosOrg(3));
        usuarios.addAll(crearUsuariosAgente(1));

        usuarios.forEach(u -> usuariosRepo.agregar(u));
        usuarios.stream().filter(u -> u.getRole().getName().equals("miembro")).forEach(u -> persistirMiembro(u));
        usuarios.stream().filter(u -> u.getRole().getName().equals("organizacion")).forEach(u -> persistirOrganizacion(u));
        usuarios.stream().filter(u -> u.getRole().getName().equals("agente")).forEach(u -> persistirAgente(u));
    }

    // todo
    private void persistirRoles() {
        if (RoleHelper.getRole("organizacion") == null) persistirRol("organizacion");
        if (RoleHelper.getRole("admin") == null) persistirRol("admin");
        if (RoleHelper.getRole("agente") == null) persistirRol("agente");
        if (RoleHelper.getRole("miembro") == null) persistirRol("miembro");
    }

    private void persistirRol(String nombre) {
        Role role = new Role(nombre);
        roles.agregar(role);
    }

    // retorna una lista con un 3 usuarios de nombre miembro + numero, agente + numero, org + numero
    public List<User> crearUsuariosOrg(int n) {
        List<User> usuarios = new ArrayList<>();

        for (int i = 0; i<n; i++) {
            User org = new User(Random.nombreUsuarioOrg(), "");
            org.setRole(RoleHelper.getRole("organizacion"));
            org.setPassword(org.getUsername());
            usuarios.add(org);
        }

        return usuarios;
    }


    public List<User> crearUsuariosAgente(int n) {
        List<User> usuarios = new ArrayList<>();

        for (int i = 0; i<n; i++) {
            User agente = new User(Random.nombreUsuarioAgente(), "");
            agente.setRole(RoleHelper.getRole("agente"));
            agente.setPassword(agente.getUsername());
            usuarios.add(agente);
        }

        return usuarios;
    }

    public List<User> crearUsuariosMiembro(int n) {
        List<User> usuarios = new ArrayList<>();

        for (int i = 0; i<n; i++) {
            User miembro = new User(Random.nombreUsuarioMiembro(), "");
            miembro.setRole(RoleHelper.getRole("miembro"));
            miembro.setPassword(miembro.getUsername());
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
}
