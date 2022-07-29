package threads.threads_12_reentantlock_exchanger_phaser.shipping_gateway;

import java.util.Random;

public class Ship implements Runnable{

    private final int capacity;
    private final String name;
    private final Port homePort;
    private final Port destinationPort;
    private final Gateway shippingGateway;
    private boolean empty = true;
    private boolean gatewayPassed = false;

    public Ship(int capacity, String name, Port homePort, Port destinationPort, Gateway shippingGateway) {
        this.capacity = capacity;
        this.name = name;
        this.homePort = homePort;
        this.destinationPort = destinationPort;
        this.shippingGateway = shippingGateway;
        new Thread(this).start();
        System.out.println("-----------------Ship "+name+" online-----------------");
    }

    public int getCapacity() {
        return capacity;
    }

    public String getName() {
        return name;
    }

    private void move() throws InterruptedException {
        if (empty){
            homePort.dock(this);
            empty = false;
        } else {
            destinationPort.dock(this);
            empty = true;
        }

        Thread.sleep(new Random().nextInt(10000)+100);
        gatewayPassed = false;
    }

    @Override
    public void run() {

        try {
            while (!Thread.currentThread().isInterrupted()){
                while (!gatewayPassed) {
                    synchronized (shippingGateway) {
                        while (!shippingGateway.enterGateway(this)) {
                            shippingGateway.wait();
                        }
                    }
                    gatewayPassed = true;
                }
                move();
            }
        } catch (InterruptedException e) {
            System.out.println("-----------------Ship "+name+" offline-----------------");
        }
    }

    public void stopCurrentShip(){
        Thread.currentThread().interrupt();
    }
}
