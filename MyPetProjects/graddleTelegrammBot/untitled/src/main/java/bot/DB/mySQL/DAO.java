package bot.DB.mySQL;

public interface DAO<T, X> {
    void create(T object);
    void update(T object);
    T read(X query);
    void delete(T object);

}
