package threads.threads_12_reentantlock_exchanger_phaser.phaser;

import java.util.concurrent.Phaser;

public class SkilletThread implements Runnable{

    private final Phaser phaser;

    public SkilletThread(Phaser phaser) {
        this.phaser = phaser;
        phaser.register();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(500);
            System.out.println("Обжариваем лук...");
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }

        phaser.arriveAndDeregister();
    }
}
