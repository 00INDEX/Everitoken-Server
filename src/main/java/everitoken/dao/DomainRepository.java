package everitoken.dao;

import java.io.Serializable;
import java.util.List;

public interface DomainRepository<T, PK extends Serializable> {

    T load(PK id);

    T get(PK id);

    List<T> findAll();

    void persist(T entity);

    int add(T entity) throws Exception;

    void update(T entity);

    void delete(PK id);

    void flush();

}
