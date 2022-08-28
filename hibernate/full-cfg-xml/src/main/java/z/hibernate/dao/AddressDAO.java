package z.hibernate.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import z.hibernate.entities.Address;

public class AddressDAO implements DAO<Address, String> {

    private final SessionFactory sessionFactory;

    public AddressDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Address object) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.persist(object);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Address object) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.merge(object);
            session.getTransaction().commit();
        }
    }

    @Override
    public Address read(String query) {
        try (Session session = sessionFactory.openSession()){
            final Address address = session.get(Address.class, query);
            return address == null? new Address() : address;
        }
    }

    @Override
    public void delete(Address object) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.remove(object);
            session.getTransaction().commit();
        }
    }
}
