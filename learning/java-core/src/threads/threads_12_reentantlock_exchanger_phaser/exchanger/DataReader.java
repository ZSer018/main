package threads.threads_12_reentantlock_exchanger_phaser.exchanger;

import java.util.Arrays;
import java.util.concurrent.Exchanger;

public class DataReader implements Runnable{

    private final Exchanger<DataBuffer<Integer>> exchanger;

    public DataReader(Exchanger<DataBuffer<Integer>> exchanger) {
        this.exchanger = exchanger;
    }

    @Override
    public void run() {

        while (! Thread.currentThread().isInterrupted()) {
           DataBuffer<Integer> dataBuffer = new DataBuffer<>(1);

            try {
                dataBuffer = exchanger.exchange(dataBuffer);
                System.out.println("---------new data--------");
                Arrays.asList(dataBuffer.getBuff()).forEach(System.out::println);
            } catch (InterruptedException interruptedException) {
                break;
            }
        }

        System.out.println("Data reader offline");
    }
}