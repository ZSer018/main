package data.dao;

import data.entity.User;
import data.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class UserDao implements DAO<User, String>{

    private final SessionFactory sessionFactory;

    public UserDao() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public User read(String query) {
        try (Session session = sessionFactory.openSession()){
            return session.get(User.class, query);
        }
    }

    @Override
    public void save(User object) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.persist(object);
            session.getTransaction().commit();
        }
    }


    public void saveList(List<User> object) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            for (User u: object) {
                session.persist(u);
            }

            session.getTransaction().commit();
        }
    }

    @Override
    public void update(User object) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.merge(object);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(User object) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.remove(object);
            session.getTransaction().commit();
        }
    }
}
