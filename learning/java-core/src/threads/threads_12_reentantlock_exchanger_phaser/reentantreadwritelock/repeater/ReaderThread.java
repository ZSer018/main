package threads.threads_12_reentantlock_exchanger_phaser.reentantreadwritelock.repeater;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReaderThread implements Runnable{

    private static final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private static final Lock readLock = readWriteLock.readLock();
    private static final Lock writeLock = readWriteLock.writeLock();
    private final SharedSource sharedSource;

    public ReaderThread(SharedSource sharedSource) {
        this.sharedSource = sharedSource;
    }

    @Override
    public void run() {

        while (!Thread.currentThread().isInterrupted()){

            try {
                readLock.lock();
                String s = sharedSource.getData();
                if (s != null) {
                    System.out.println(Thread.currentThread().getName() + ": "+ s);
                }
            } finally {
                readLock.unlock();
            }

        }

    }

}
