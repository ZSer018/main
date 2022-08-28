package z.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import z.hibernate.controllers.*;
import z.hibernate.entities.*;
import z.hibernate.utils.HibernateUtil;

import java.util.Calendar;
import java.util.TimeZone;

public class Main {

    public static void main(String[] args) {

        Main main = new Main();

        main.personQuery();
        //main.accountQuery();
        //main.addressQuery();
        //main.loanQuery();
        //main.guarantorQuery();
    }

    void personQuery(){
        final PersonController personController = new PersonController();
        Person person;

        //TODO Create
/*      person = new Person();
        person.setFirstName("Bennu");
        person.setLastName("Hill");
        personController.create(person);*/

        //TODO read
        person = personController.read("2");
        System.out.println(person.toString());
        person.getAccountList().forEach(System.out::println);
        person.getLoanList().forEach(System.out::println);
        person.getGuarantorList().forEach(System.out::println);

        //TODO update
     /*   person = personController.read("13");
        person.setId(13);
        person.setFirstName("Евгений");
        personController.update(person);*/

        //TODO delete
/*      person = new Person();
        person.setId(29);
        personController.delete(person);*/
    }

    void accountQuery(){
        final AccountController accountController = new AccountController();
        Account account;

        //TODO Create
/*        account = new Account();
        account.setAccountNumber("2738-2323-6542-7653");
        account.setMoneyAvailable(10000);
        account.setCurrencyType("Rub");
        account.setPerson(new PersonController().read("10"));
        accountController.create(account);*/

        //TODO read
        account = accountController.read("1628-1914-3805-3742");
        System.out.println(account);

        //TODO update
        //account = accountController.read(10);
        account.setMoney(account.getMoney() + 1000000);
        accountController.update(account);

        //TODO delete
        /*account = accountController.read(14);
        accountController.delete(account);*/
    }



    void addressQuery(){
        final AddressController addressController = new AddressController();
        Address address;

        //TODO Create
/*        address = new Address();
        address.setAddress("sdfsdfsdfsdf");
        address.setPersonId(1);
        addressController.create(address);*/

        //TODO read
        System.out.println(addressController.read("1"));


        //TODO update

        //TODO delete
    }



    void guarantorQuery(){



        //TODO Create
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()){
            System.out.println(session.get(Guarantor.class, "2"));
        }



        //TODO read


        //TODO update

        //TODO delete
    }


    void loanQuery(){
        final LoanController loanController = new LoanController();
        Loan loan;

        // Предположим что сейчас 2015-01-01 01:00:00 MSK
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
        calendar.setTimeInMillis(0);
        calendar.set(2015, Calendar.JANUARY, 1, 1, 0, 0);
        long now = calendar.getTimeInMillis();


        //TODO Create
/*        loan = new Loan();
        loan.setAmount(10000);
        loan.setPeriod(10);
        loan.setRate(8.4);
        loan.setRepaidAmount(1504);
        loan.setRepaid(false);
        loan.setDateOfAccepting(new Date(now));
        loan.setPersonId(new PersonDAO(HibernateUtil.getSessionFactory()).read("15"));
        loanController.create(loan);*/


        //TODO read
        loan = new Loan();

        ///loan.setPersonId(new PersonDAO(HibernateUtil.getSessionFactory()).read("15"));


        //TODO update

        //TODO delete

    }

}
