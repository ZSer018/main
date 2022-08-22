package threads.threads_12_reentantlock_exchanger_phaser.reentantreadwritelock.repeater;

import java.util.concurrent.atomic.AtomicInteger;

public class SharedSource {

    private final String[] data;
    private final AtomicInteger index;

    public SharedSource() {
        data = new String[10];
        index = new AtomicInteger(0);
    }

    public String getData() {
        if (index.get() < data.length) {
            return data[index.getAndIncrement()];
        } else return null;
    }

    public void setData(String[] newData){
        System.arraycopy(newData, 0, data, 0, data.length);
        index.set(0);
        System.out.println("data updated");
    }

    public void clearData(){
        System.out.println("data cleared");
    }

    public boolean endOfList(){
        return index.get() == data.length-1;
    }
}
