package threads.threads_12_reentantlock_exchanger_phaser.post_office_reentantlock;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PostOffice {

    private LinkedList<String> packageQueue = new LinkedList<>();
    private final Lock lock;
    private final Condition lockObj;

    public PostOffice() {
        this.lock = new ReentrantLock(true);
        this.lockObj = lock.newCondition();
    }

    public String receivePostPackage(){
        return packageQueue.poll();
    }

    public void loadPostPackages(LinkedList<String> postPackeges){
        packageQueue.addAll(postPackeges);
        System.out.println(packageQueue.size());
    }

    public boolean noMorePackages(){
        return packageQueue.isEmpty();
    }

    public Lock getLock() {
        return lock;
    }

    public Condition getLockObj() {
        return lockObj;
    }
}
