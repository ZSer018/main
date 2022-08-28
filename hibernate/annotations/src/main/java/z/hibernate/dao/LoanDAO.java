package z.hibernate.dao;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import z.hibernate.entities.Loan;

public class LoanDAO implements DAO<Loan, String>{

    private final SessionFactory sessionFactory;

    public LoanDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Loan object) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(object);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Loan object) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(object);
            session.getTransaction().commit();
        }
    }

    @Override
    public Loan read(String query) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            return session.get(Loan.class, query);
        }
    }

    @Override
    public void delete(Loan object) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(object);
            session.getTransaction().commit();
        }
    }
}
