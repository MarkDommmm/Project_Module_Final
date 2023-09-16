package ra.security.service;

import java.util.List;

public interface IGenericService <T,K,E>{
    List<T> findAll();
    T findById(E e);
    T save(K k);
    T update(K k , E id);

    T delete(E e);

}
