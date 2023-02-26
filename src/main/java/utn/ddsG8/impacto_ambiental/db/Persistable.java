package utn.ddsG8.impacto_ambiental.db;

import lombok.Getter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Persistable {

    @Id
    @GeneratedValue
    @Getter
    int id;
}
