package z.hibernate.utils;


import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import z.hibernate.entities.*;


public class HibernateUtil {

    private static final SessionFactory sessionFactory;


    static {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(Person.class)
                .addAnnotatedClass(Address.class)
                .addAnnotatedClass(Account.class)
                .addAnnotatedClass(Loan.class)
                .addAnnotatedClass(Guarantor.class)
                .configure();

        final StandardServiceRegistryBuilder standardServiceRegistryBuilder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());

        sessionFactory = configuration.buildSessionFactory(standardServiceRegistryBuilder.build());
    }

    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }
}
