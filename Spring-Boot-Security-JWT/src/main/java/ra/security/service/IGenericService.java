package ra.security.service;

import ra.security.exception.*;

import java.util.List;

public interface IGenericService <T,K,E>{
    List<T> findAll();
    T findById(E e) throws CustomException;
    T save(K k) throws CustomException;
    T update(K k , E id) throws CustomException;

    T delete(E e) throws  CustomException;

}
