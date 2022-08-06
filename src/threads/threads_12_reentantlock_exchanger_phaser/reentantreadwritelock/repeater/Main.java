package threads.threads_12_reentantlock_exchanger_phaser.reentantreadwritelock.repeater;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        SharedSource sharedSource = new SharedSource();

        ArrayList<Thread> threadArrayList = new ArrayList<>();
        threadArrayList.add( new Thread( new WriterThread(sharedSource)));
        threadArrayList.add( new Thread( new CleanerThread(sharedSource)));

        for (int i = 0; i < 4; i++) {
            threadArrayList.add(new Thread( new ReaderThread(sharedSource)));
        }

        threadArrayList.forEach(Thread::start);

        Thread.sleep(10000);

        threadArrayList.forEach(Thread::interrupt);
    }
}
