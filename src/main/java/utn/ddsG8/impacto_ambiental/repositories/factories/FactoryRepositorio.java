package utn.ddsG8.impacto_ambiental.repositories.factories;


import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.daos.DAO;
import utn.ddsG8.impacto_ambiental.repositories.daos.DAOHibernate;

import java.util.HashMap;

public class FactoryRepositorio {
    private static HashMap<String, Repositorio> repos;

    static {
        repos = new HashMap<>();
    }

    public static <T> Repositorio<T> get(Class<T> type){
        Repositorio repo;
        if(repos.containsKey(type.getName())){
            repo = repos.get(type.getName());
        }
        else{
                DAO<T> dao = new DAOHibernate<>(type);
                repo = new Repositorio<>(dao);
            }
            repos.put(type.toString(), repo);
        return repo;
    }
}
