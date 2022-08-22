package threads.threads_11_semaphore_cyclicbarrier_countdownlatch.countdownlath;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class CountDownThread implements Runnable{

    private final CountDownLatch countDownLatch;
    private final int name;

    public CountDownThread(CountDownLatch countDownLatch, int name) {
        this.countDownLatch = countDownLatch;
        this.name = name;
    }

    @Override
    public void run() {

        System.out.println("Thread " +name+ " working...");
        try {
            Thread.sleep(new Random().nextInt(5000)+100);
            countDownLatch.countDown();
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Thread " +name+ " done");

    }
}
