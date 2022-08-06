package threads.threads_13_executors.work_stealing_pool;

import java.util.concurrent.Phaser;

public class TimeThread implements Runnable{

    private final Phaser phaser;

    public TimeThread(Phaser phaser) {
        this.phaser = phaser;
        phaser.register();
    }

    @Override
    public void run() {
        phaser.arriveAndAwaitAdvance();

        for (int i = 1; i < 10; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            System.out.println("------------- прошло: "+i+"000");
        }
        phaser.arriveAndDeregister();
    }
}
