package threads.threads_05_interrupt_two;

import java.util.Random;

public class Worker2 implements Runnable {
    @Override
    public void run() {
        Random random = new Random();
        System.out.println("Рабочий готов");
        do {
            System.out.println("Работаю");

            try {
                System.out.println("Посплю, пожалуй");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                int coin = random.nextInt(100);
                if (coin >= 50) {
                    System.out.println("Проснулся, но буду работать дальше");
                } else {
                    System.out.println("Проснулся, и пожалуй закончу на сегодня");
                    return;
                }
            }
        } while (true);
    }
}
