package threads.threads_11_semaphore_cyclicbarrier_countdownlatch.semaphore;

import java.util.concurrent.Semaphore;

public class Main {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(2);
        semaphore.release();

        for (int i = 0; i < 6; i++) {
            new Thread( new SemaphoreTestThread(semaphore, i)).start();
        }
    }
}
