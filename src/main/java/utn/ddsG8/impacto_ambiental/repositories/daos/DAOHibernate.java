package utn.ddsG8.impacto_ambiental.repositories.daos;

import utn.ddsG8.impacto_ambiental.db.EntityManagerHelper;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class DAOHibernate<T> implements DAO<T> {
    private Class<T> type;

    public DAOHibernate(Class<T> type){
        this.type = type;
    }

    @Override
    public List<T> buscarTodos() {
        CriteriaBuilder builder = EntityManagerHelper.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> critera = builder.createQuery(this.type);
        critera.from(type);
        List<T> entities = EntityManagerHelper.getEntityManager().createQuery(critera).getResultList();
        EntityManagerHelper.closeEntityManager();

        return entities;
    }

    @Override
    public T buscar(int id) {
        T t =  EntityManagerHelper.getEntityManager().find(type, id);
        EntityManagerHelper.closeEntityManager();
        return t;
    }

    @Override
    public void agregar(Object unObjeto) {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        EntityManagerHelper.getEntityManager().persist(unObjeto);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
        EntityManagerHelper.closeEntityManager();
    }

    @Override
    public void modificar(Object unObjeto) {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        EntityManagerHelper.getEntityManager().merge(unObjeto);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
        EntityManagerHelper.closeEntityManager();
    }

    @Override
    public void eliminar(Object unObjeto) {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        // esto lo hace porqu el objeto esta detached al parecer
        Object reattached = EntityManagerHelper.getEntityManager().merge(unObjeto);
        EntityManagerHelper.getEntityManager().remove(reattached);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
        EntityManagerHelper.closeEntityManager();
    }
}
