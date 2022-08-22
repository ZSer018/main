package threads.threads_11_semaphore_cyclicbarrier_countdownlatch.countdownlath;

import java.util.concurrent.CountDownLatch;

public class Main {



    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(5);

        for (int i = 0; i < 5; i++) {
            new Thread( new CountDownThread(countDownLatch, i)).start();
        }
    }

}
