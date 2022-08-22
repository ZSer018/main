package threads.threads_12_reentantlock_exchanger_phaser.phaser;

import java.util.concurrent.Phaser;

public class PanThread implements Runnable{

    private final Phaser phaser;

    public PanThread(Phaser phaser) {
        this.phaser = phaser;
        phaser.register();
    }

    @Override
    public void run() {
        try {
            System.out.println("Мясо добавлено в кастрюлю.  Варка...");
            phaser.arriveAndAwaitAdvance();
            Thread.sleep(100);

            System.out.println("Овощи добавлены в кастрюлю.  Варка...");
            phaser.arriveAndAwaitAdvance();
            Thread.sleep(1000);

            phaser.arriveAndAwaitAdvance();
            System.out.println("Обжареный лук добавлен в кастрюлю.  Варка...");
            phaser.arriveAndDeregister();
        } catch (InterruptedException ignored) {
        }
    }

}
