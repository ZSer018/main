package data.dao;

import data.entities.Person;
import data.dataService.HibernateSessionFactoryService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO implements DAO<Person, String>{

    private final SessionFactory sessionFactory;

    public PersonDAO() {
        sessionFactory = HibernateSessionFactoryService.getSessionFactory();
    }

    @Override
    public void create(Person object) {
        try(final Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.persist(object);
            session.getTransaction().commit();
        }
    }

    public List<Person> readQuery(String field, String query){
        try(Session session = sessionFactory.openSession()) {

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Person> cr = cb.createQuery(Person.class);
            Root<Person> root = cr.from(Person.class);
            cr.select(root).where(cb.like(root.get(field), query));

            Query<Person> q = session.createQuery(cr);
            return q.getResultList();
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
                Hibernate.initialize(person.getPersonData());
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
