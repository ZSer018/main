package threads.threads_12_reentantlock_exchanger_phaser.post_office_reentantlock;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class China implements Runnable{

    private final PostOffice postOffice;
    private final Lock lock;
    private final Condition lockObj;

    public China(PostOffice postOffice) {
        this.postOffice = postOffice;
        this.lock = postOffice.getLock();
        this.lockObj = postOffice.getLockObj();
    }

    private void sendAPackage(LinkedList<String> postPackeges){
        postOffice.loadPostPackages(postPackeges);
        postPackeges.clear();
    }

    @Override
    public void run() {
        LinkedList<String> mailOrder = new LinkedList<>();
        Random r = new Random();
        int count = 1;

        for (int k = 0; k < 10; k++) {
            try {
                int x = r.nextInt(15) + 1;
                for (int i = 0; i < x; i++) {
                    mailOrder.add("order № " + count);
                    count++;
                }
                lock.lock();
                sendAPackage(mailOrder);
            } finally {
                lockObj.signalAll();
                lock.unlock();
            }

             try {
                 Thread.sleep(2000);
             } catch (InterruptedException interruptedException) {
                 break;
             }
         }

        System.out.println("---      Aliexpress offline      ---");
    }
}
