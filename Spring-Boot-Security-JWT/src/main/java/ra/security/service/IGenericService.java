package ra.security.service;

import ra.security.exception.*;

import java.util.List;

public interface IGenericService <T,K,E>{
    List<T> findAll();
    T findById(E e) throws CategoryException, ColorException, OrderException, DiscountException, OrderDetailException;
    T save(K k) throws CategoryException, BrandException, ColorException, DiscountException;
    T update(K k , E id);

    T delete(E e);

}
