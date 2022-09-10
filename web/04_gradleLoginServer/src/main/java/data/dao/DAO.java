package data.dao;

public interface DAO<T, X> {
    T read(X query);
    void save(T object);
    void update(T object);
    void delete(T object);
}
