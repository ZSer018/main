package z.hibernate.controllers;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import z.hibernate.dao.PersonDAO;
import z.hibernate.entities.Person;
import z.hibernate.utils.HibernateUtil;

public class PersonController implements DAOController<Person, String>{

   private final SessionFactory sessionFactory;

   public PersonController() {
      sessionFactory = HibernateUtil.getSessionFactory();
   }

   @Override
   public void create(final Person person) {
        new PersonDAO(sessionFactory).create(person);
   }

   @Override
   public Person read(String query) {
        return new PersonDAO(sessionFactory).read(query);
   }

   @Override
   public void update(Person person) {
      new PersonDAO(sessionFactory).update(person);
   }

   @Override
   public void delete(Person person) {
      new PersonDAO(sessionFactory).delete(person);
   }
}
