package threads.threads_10_notify_wait_sync_method.post_office;

public class Customer extends Thread{

    private final PostOffice postOffice;
    private final String name;

    public Customer(PostOffice postOffice, String name) {
        this.postOffice = postOffice;
        this.name = name;
    }

    @Override
    public void run() {
        super.run();

        try {
            while (!isInterrupted()) {
                synchronized (postOffice) {
                    while (postOffice.thereAreMorePackages()) {
                        System.out.println(name + " wait...");
                        postOffice.wait(2000);
                    }

                    System.out.println(name+" receive: "+ postOffice.receivePostPackage());
                }
                sleep(500);
            }
        } catch (InterruptedException e) {
            System.out.println(name + " bb");
            return;
        }
    }
}
