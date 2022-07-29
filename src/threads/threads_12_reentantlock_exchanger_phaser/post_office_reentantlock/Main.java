package threads.threads_12_reentantlock_exchanger_phaser.post_office_reentantlock;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        PostOffice postOffice = new PostOffice();
        Thread china = new Thread( new China(postOffice) );

        Thread customer1 = new Thread( new Customer(postOffice, "Anton "));
        Thread customer2 = new Thread( new Customer(postOffice, "Sergey "));
        Thread customer3 = new Thread( new Customer(postOffice, "Maria "));

        china.start();
        customer1.start();
        customer2.start();
        customer3.start();
        china.join();

        while(!postOffice.noMorePackages()){
             Thread.sleep(100);
        }

         customer1.interrupt();
         customer2.interrupt();
         customer3.interrupt();
    }
}
