package threads.threads_06_race_condition;

public class RaceCondition_A {

    private int value = 0;

    private void increment(){
        value++;
    }

    public static void main(String[] args) throws InterruptedException {
        RaceCondition_A raceCondition = new RaceCondition_A();

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
            increment();
        }

    };
}


