package threads.threads_10_notify_wait_sync_method.shipping_gateway;

import java.util.Random;

public class Port{

    private final String name;

    public Port(String name) {
        this.name = name;
    }

    public boolean dock(Ship ship) throws InterruptedException {
        //System.out.println("Корабль "+ship.getName() +" причалил к порту "+ name);
        return true;
    }
}
