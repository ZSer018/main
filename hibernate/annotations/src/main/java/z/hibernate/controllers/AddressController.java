package z.hibernate.controllers;

import org.hibernate.SessionFactory;
import z.hibernate.dao.AddressDAO;
import z.hibernate.entities.Address;
import z.hibernate.utils.HibernateUtil;

public class AddressController implements DAOController<Address, String>{

   private final SessionFactory sessionFactory;

   public AddressController() {
      sessionFactory = HibernateUtil.getSessionFactory();
   }

   @Override
   public void create(Address address) {
        new AddressDAO(sessionFactory).create(address);
   }

   @Override
   public Address read(String query) {
        return new AddressDAO(sessionFactory).read(query);
   }

   @Override
   public void update(Address address) {
      new AddressDAO(sessionFactory).update(address);
   }

   @Override
   public void delete(Address address) {
      new AddressDAO(sessionFactory).delete(address);
   }
}
