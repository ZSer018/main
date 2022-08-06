package threads.threads_12_reentantlock_exchanger_phaser.exchanger;

import java.util.ArrayList;
import java.util.List;

public class DataBuffer<T> {

    private final ArrayList<T> buff;
    private final int buffLength;
    private int index = 0;

    public DataBuffer(int buffLength) {
        buff = new ArrayList<>(buffLength);
        this.buffLength = buffLength;
    }

    public void addData(T data){
        if (buff.size() < (index + 1) ){
            buff.add(data);
            index++;
        } else
        buff.set(index++, data);
    }

    public boolean buffIsFull(){
        return index == buffLength;
    }

    public List<T> getBuff() {
        index = 0;
        return buff;
    }

}
