package threads.threads_11_semaphore_cyclicbarrier_countdownlatch.first_test;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class SemaphoreTestThread implements Runnable{

    Semaphore semaphore;
    int i;

    public SemaphoreTestThread(Semaphore semaphore, int i) {
        this.semaphore = semaphore;
        this.i = i;
    }

    @Override
    public void run() {
        try {
            System.out.println("Поток "+ i + " ждет семафора");
            semaphore.acquire();
            System.out.println("Поток "+ i + " получил допуск ");
            Thread.sleep(new Random().nextInt(5000)+500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            System.out.println("Поток "+ i + " освободил семафор ");
            semaphore.release();
        }
    }
}
