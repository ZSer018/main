package z.hibernate.controllers;

public interface DAOController<T, X> {
    void create(final T t);
    T read(X query);
    void update(final T t);
    void delete(final T t);
}
