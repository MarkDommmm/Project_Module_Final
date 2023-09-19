package ra.security.service;

import ra.security.exception.CustomException;

public interface IGenericMapper<T, K, V> {
    T toEntity(K k);

    V toResponse(T t) throws CustomException;
}
