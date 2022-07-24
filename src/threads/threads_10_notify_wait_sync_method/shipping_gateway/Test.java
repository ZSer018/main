package threads.threads_10_notify_wait_sync_method.shipping_gateway;

import java.util.ArrayList;
import java.util.Random;

public class Test {

    public static void main(String[] args) throws InterruptedException {
        Gateway gateway = new Gateway(10);

        ArrayList<Port> ports = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            ports.add(new Port("Порт_"+i));
        }

        ArrayList<Ship> ships = new ArrayList<>(5);
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            ships.add(new Ship(random.nextInt(8)+1, (char)(random.nextInt(25)+65)+"", ports.get(random.nextInt(ports.size()-1)), ports.get(ports.size()-1), gateway));
        }

        Thread.sleep(1000);
        ships.forEach(Ship::stopCurrentShip);
    }
}
