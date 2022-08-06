package threads.threads_12_reentantlock_exchanger_phaser.phaser;

import java.util.concurrent.Phaser;

public class Chef {

    private final Phaser phaser;

    public Chef() {
        phaser = new Phaser();
        phaser.register();
    }

    private void makeADish(){

        System.out.println("Фаза "+ phaser.getPhase()+ ": ");
        new Thread(new KnifeThread(phaser)).start();
        System.out.println("ШЕФ: Нарезаем мясо и кладем его в кастрюлю... ");
        new Thread( new PanThread(phaser)).start();
        sleep(100);
        phaser.arriveAndAwaitAdvance();
        System.out.println();


        System.out.println("Фаза "+ phaser.getPhase()+ ": ");
        System.out.println("ШЕФ: Добавляем туда же нарезанные овощи... ");
        sleep(1000);
        phaser.arriveAndAwaitAdvance();
        System.out.println();


        System.out.println("Фаза "+ phaser.getPhase()+ ": ");
        System.out.println("ШЕФ: Нарезаем лук и слегка поджариваем его... ");
        new Thread( new SkilletThread(phaser)).start();
        phaser.arriveAndAwaitAdvance();
        sleep(3000);
        System.out.println();


        System.out.println("ШЕФ: Серверуем блюдо... ");
        Dish dish = new Dish();
        System.out.println("ШЕФ: подаем. ");
    }

    private boolean sleep(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException interruptedException) {
            return false;
        }
        return true;
    }


    public static void main(String[] args) {
        Chef chef = new Chef();
        chef.makeADish();
    }
}
