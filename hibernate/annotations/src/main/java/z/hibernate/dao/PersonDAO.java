package z.hibernate.dao;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import z.hibernate.entities.Person;

public class PersonDAO implements DAO<Person, String>{

    private final SessionFactory sessionFactory;

    public PersonDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Person object) {
        try(final Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.persist(object);
            session.getTransaction().commit();
        }
    }

    @Override
    public Person read(String query) {
        try(Session session = sessionFactory.openSession()){
            Person person = session.get(Person.class, query);

            if (person != null) {
                Hibernate.initialize(person.getAccountList());
                Hibernate.initialize(person.getLoanList());
                Hibernate.initialize(person.getGuarantorList());
            }

            return person == null ? new Person() : person;
        }
    }

    @Override
    public void update(Person object) {
        try (final Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.merge(object);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Person object) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.remove(object);
            session.getTransaction().commit();
        }
    }
}
