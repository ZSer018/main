package z.hibernate.controllers;

import org.hibernate.SessionFactory;
import z.hibernate.dao.LoanDAO;
import z.hibernate.entities.Loan;
import z.hibernate.utils.HibernateUtil;

public class LoanController implements DAOController<Loan, String>{

    private final SessionFactory sessionFactory;

    public LoanController() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public void create(Loan loan) {
        new LoanDAO(sessionFactory).create(loan);
    }

    @Override
    public Loan read(String query) {
        return new LoanDAO(sessionFactory).read(query);
    }

    @Override
    public void update(Loan loan) {
        new LoanDAO(sessionFactory).update(loan);
    }

    @Override
    public void delete(Loan loan) {
        new LoanDAO(sessionFactory).delete(loan);
    }
}
