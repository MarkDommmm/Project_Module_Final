package ra.security.service;

public interface IGenericMapper<T, K, V> {
    T toEntity(K k);

    V toResponse(T t);
}
