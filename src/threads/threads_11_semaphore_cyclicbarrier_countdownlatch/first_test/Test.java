package threads.threads_11_semaphore_cyclicbarrier_countdownlatch.first_test;

import java.util.concurrent.Semaphore;

public class Test {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(2);
        semaphore.release();

        for (int i = 0; i < 6; i++) {
            new Thread( new SemaphoreTestThread(semaphore, i)).start();
        }
    }
}
