package SOLID.D;


public interface Repository {

    void create(Entity request);

    void delete(int request);

    void update(Entity request);

    Entity read(int request);
}
