package threads.threads_12_reentantlock_exchanger_phaser.reentantreadwritelock.repeater;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class WriterThread implements Runnable{

    private static final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private static final Lock readLock = readWriteLock.readLock();
    private static final Lock writeLock = readWriteLock.writeLock();
    private final SharedSource sharedSource;

    public WriterThread(SharedSource sharedSource) {
        this.sharedSource = sharedSource;
    }

    String[] someStrings = new String[]{
            "Alpha",
            "Beta",
            "Gamma",
            "Delta",
            "Epsilon",
            "Dzeta",
            "Kappa",
            "Heta",
            "Yota",
            "Kappa",
            "Lambda",
            "My",
            "Nu",
            "Ksi"};
    @Override
    public void run() {
        String[] strings = new String[10];
        Random random = new Random();

        while (!Thread.currentThread().isInterrupted()){
            for (int i = 0; i < strings.length; i++) {
                strings[i] = someStrings[random.nextInt(someStrings.length-1)];
            }

            try {
                writeLock.lock();
                sharedSource.setData(strings);
            } finally {
                writeLock.unlock();
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException interruptedException) {
                System.out.println(Thread.currentThread().getName() + " - offline");
            }

        }
    }
}
