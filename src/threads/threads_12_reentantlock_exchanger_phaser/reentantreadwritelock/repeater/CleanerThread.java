package threads.threads_12_reentantlock_exchanger_phaser.reentantreadwritelock.repeater;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CleanerThread implements Runnable{

    private static final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private static final Lock writeLock = readWriteLock.writeLock();
    private final SharedSource sharedSource;

    public CleanerThread(SharedSource sharedSource) {
        this.sharedSource = sharedSource;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()){

            while (!sharedSource.endOfList()){
                try {
                    Thread.sleep(0);
                } catch (InterruptedException interruptedException) {
                    System.out.println(Thread.currentThread().getName() + " - offline");
                }
            }

            try {
                writeLock.lock();
                sharedSource.clearData();
            } finally {
                writeLock.unlock();
            }

        }
    }

}
