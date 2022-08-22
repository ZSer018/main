package threads.threads_12_reentantlock_exchanger_phaser.exchanger;

import java.util.Random;
import java.util.concurrent.Exchanger;

public class DataProducer implements Runnable{

    private final Exchanger<DataBuffer<Integer>> exchanger;

    public DataProducer(Exchanger<DataBuffer<Integer>> exchanger) {
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (! Thread.currentThread().isInterrupted()) {
            int buffSize = random.nextInt(18)+2;
            DataBuffer<Integer> dataBuffer = new DataBuffer<>(buffSize);

            for (int i = 0; i < buffSize; i++) {
                if (!dataBuffer.buffIsFull()) {
                    dataBuffer.addData(i);
                } else break;
            }
            try {
                exchanger.exchange(dataBuffer);
                Thread.sleep(1000);
            } catch (InterruptedException interruptedException) {
                break;
            }
            //dataBuffer.getBuff().forEach(System.out::println);
        }

        System.out.println("Data producer offline");
    }
}
