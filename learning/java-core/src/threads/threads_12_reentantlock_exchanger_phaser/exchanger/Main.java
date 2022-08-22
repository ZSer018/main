package threads.threads_12_reentantlock_exchanger_phaser.exchanger;

import java.util.concurrent.Exchanger;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Exchanger<DataBuffer<Integer>> exchanger = new Exchanger<>();
        Thread producer = new Thread( new DataProducer(exchanger));
        Thread reader = new Thread( new DataReader(exchanger));
        producer.start();
        reader.start();

        Thread.sleep(5000);
        producer.interrupt();
        reader.interrupt();
    }
}
