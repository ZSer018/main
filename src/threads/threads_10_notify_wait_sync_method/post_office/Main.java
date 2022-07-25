package threads.threads_10_notify_wait_sync_method.post_office;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        PostOffice postOffice = new PostOffice();
        China china = new China(postOffice);

        Customer customer1 = new Customer(postOffice, "Anton ");
        Customer customer2 = new Customer(postOffice, "Sergey ");
        Customer customer3 = new Customer(postOffice, "Maria ");

        china.start();
        customer1.start();
        customer2.start();
        customer3.start();

        Thread.sleep(10000);
        china.interrupt();

        while(!postOffice.thereAreMorePackages()){
             Thread.sleep(100);
        }

         customer1.interrupt();
         customer2.interrupt();
         customer3.interrupt();
    }
}
