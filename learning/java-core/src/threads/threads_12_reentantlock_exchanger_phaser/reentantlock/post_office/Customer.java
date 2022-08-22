package threads.threads_12_reentantlock_exchanger_phaser.reentantlock.post_office;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Customer implements Runnable{

    private final PostOffice postOffice;
    private final String name;
    private final Lock lock;
    private final Condition lockObj;

    public Customer(PostOffice postOffice, String name) {
        this.postOffice = postOffice;
        this.name = name;
        this.lock = postOffice.getLock();
        this.lockObj = postOffice.getLockObj();
    }

    @Override
    public void run() {

        while (!Thread.currentThread().isInterrupted()) {
            try {
                lock.lock();

                while (postOffice.noMorePackages()) {
                    lockObj.await();
                }
                System.out.println(name + " receive: " + postOffice.receivePostPackage());

            } catch (InterruptedException e) {
                break;
            }finally {
                lock.unlock();
            }
        }

        System.out.println("---    Customer "+name+" Offline     ---");
    }


}
