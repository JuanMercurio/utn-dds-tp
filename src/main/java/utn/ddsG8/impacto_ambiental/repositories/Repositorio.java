package utn.ddsG8.impacto_ambiental.repositories;


import utn.ddsG8.impacto_ambiental.db.EntityManagerHelper;
import utn.ddsG8.impacto_ambiental.domain.estructura.Organizacion;
import utn.ddsG8.impacto_ambiental.domain.estructura.Sector;
import utn.ddsG8.impacto_ambiental.repositories.daos.DAO;

import java.util.List;

public class Repositorio<T> {
    protected DAO<T> dao;

    public Repositorio(DAO<T> dao) {
        this.dao = dao;
    }

    public void setDao(DAO<T> dao) {
        this.dao = dao;
    }

    public void agregar(Object unObjeto){
        this.dao.agregar(unObjeto);
    }

    public void modificar(Object unObjeto){
        this.dao.modificar(unObjeto);
    }

    public void eliminar(Object unObjeto){
        this.dao.eliminar(unObjeto);
    }

    public List<T> buscarTodos(){
        return this.dao.buscarTodos();
    }

    public T buscar(int id){
        return this.dao.buscar(id);
    }

    public List<T> query(String query){
        List<T> lista =  EntityManagerHelper.getEntityManager().createQuery(query).getResultList();
        EntityManagerHelper.closeEntityManager();
        return lista;
    }
}
