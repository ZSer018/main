package threads.threads_12_reentantlock_exchanger_phaser.phaser;

import java.util.concurrent.Phaser;

public class KnifeThread implements Runnable{
    private final Phaser phaser;

    public KnifeThread(Phaser phaser) {
       this.phaser = phaser;
       phaser.register();
    }

    @Override
    public void run() {
        try {
            System.out.println("Нарезка мяса...");
            phaser.arriveAndAwaitAdvance();
            Thread.sleep(50);

            System.out.println("Нарезка овощей...");
            phaser.arriveAndAwaitAdvance();
            Thread.sleep(50);

            System.out.println("Нарезка лука...");
            phaser.arriveAndDeregister();
        } catch (InterruptedException ignored) {
        }
    }


}
