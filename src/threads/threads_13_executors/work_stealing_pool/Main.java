package threads.threads_13_executors.work_stealing_pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Phaser phaser = new Phaser();
        phaser.register();

        new Thread( new TimeThread(phaser)).start();
        Thread.sleep(500);

        phaser.arriveAndAwaitAdvance();
        System.out.println("START");
        ExecutorService executorService = Executors.newWorkStealingPool(3);

        for (int i = 0; i < 20; i++) {
            executorService.submit(new MyThread(i));
        }

        Thread.sleep(5000);

        for (int i = 0; i < 20; i++) {
            executorService.submit(new MyThread(i));
        }


        Thread.sleep(2000);
        executorService.shutdown();

        phaser.arriveAndDeregister();

    }
}
