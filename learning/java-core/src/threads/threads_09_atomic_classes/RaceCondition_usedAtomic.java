package threads.threads_09_atomic_classes;

import java.util.concurrent.atomic.AtomicInteger;

public class RaceCondition_usedAtomic {

    private AtomicInteger value = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        RaceCondition_usedAtomic raceCondition = new RaceCondition_usedAtomic();

        Thread thread_a = new Thread(raceCondition.incrementer);
        Thread thread_b = new Thread(raceCondition.incrementer);
        Thread thread_c = new Thread(raceCondition.incrementer);
        Thread thread_d = new Thread(raceCondition.incrementer);

        long a = System.currentTimeMillis();
        thread_a.start();
        thread_b.start();
        thread_c.start();
        thread_d.start();

        thread_a.join();
        thread_b.join();
        thread_c.join();
        thread_d.join();

        System.out.println("Ожидается: 200000000");
        System.out.println("Имеем:     "+ raceCondition.value);
        System.out.println("Затрачено времени: "+ (System.currentTimeMillis() - a) + "мс");
    }

    Runnable incrementer = () -> {
            for (int i = 0; i < 50_000_000; i++) {
               value.incrementAndGet();
            }
    };
}


