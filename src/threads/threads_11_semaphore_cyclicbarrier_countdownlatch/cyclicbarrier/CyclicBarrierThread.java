package threads.threads_11_semaphore_cyclicbarrier_countdownlatch.cyclicbarrier;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierThread implements Runnable{

    private final CyclicBarrier barrier;
    private final String name;

    public CyclicBarrierThread(CyclicBarrier barrier, String name) {
        this.barrier = barrier;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(new Random().nextInt(5000)+100);
            System.out.println(name + " нашелся!");
            barrier.await();


            System.out.println("го баффнемся");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
