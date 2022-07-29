package threads.threads_12_reentantlock_exchanger_phaser.shipping_gateway;

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
