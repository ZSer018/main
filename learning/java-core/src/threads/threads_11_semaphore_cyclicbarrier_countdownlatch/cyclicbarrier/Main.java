package threads.threads_11_semaphore_cyclicbarrier_countdownlatch.cyclicbarrier;

import java.util.concurrent.CyclicBarrier;

public class Main {

    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(5, new TeleportEvent());

        new Thread( new CyclicBarrierThread(barrier, "Дамагер")).start();
        new Thread( new CyclicBarrierThread(barrier, "Дамагер")).start();
        new Thread( new CyclicBarrierThread(barrier, "Дамагер")).start();
        new Thread( new CyclicBarrierThread(barrier, "Хил")).start();
        new Thread( new CyclicBarrierThread(barrier, "Танк")).start();
    }

    static class TeleportEvent implements Runnable{
        @Override
        public void run() {
            System.out.println("Телепортируемся в данж...");
        }
    }
}


