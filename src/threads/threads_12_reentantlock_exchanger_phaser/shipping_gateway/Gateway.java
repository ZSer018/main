package threads.threads_12_reentantlock_exchanger_phaser.shipping_gateway;

public class Gateway {

    private final int maximumCapacity;
    private int loadedCapacity = 0;
    private long countdownStart,
                 countdownEnd;
    private boolean locked = false;

    public Gateway(int maximumCapacity) {
        this.maximumCapacity = maximumCapacity;
        new Thread(gatewayLocker).start();
        dropCountdown();
    }

    private Runnable gatewayLocker = () -> {
        try {
            while (! Thread.currentThread().isInterrupted()) {
                while (System.currentTimeMillis() < countdownEnd) {
                    Thread.sleep(1);
                }

                if (loadedCapacity != 0) {
                    System.out.println("Шлюз закрылся");
                    Thread.sleep(1000);
                    release();
                    dropCountdown();
                }
            }
        } catch (InterruptedException e) {
            return;
        }
    };

    public boolean enterGateway(Ship ship){
        if (loadedCapacity + ship.getCapacity() < maximumCapacity){
            loadedCapacity += ship.getCapacity();
            System.out.println("Корабль: "+ship.getName() + " вместимостью "+ship.getCapacity()+" входит в шлюз.  (Осталось "+ (maximumCapacity - loadedCapacity) +" свободного объема)");
            dropCountdown();
            return true;
        }
        return false;
    }

    private void dropCountdown(){
        countdownStart = System.currentTimeMillis();
        countdownEnd = countdownStart + 2000;
    }

    private void release(){
        System.out.println("Шлюз, вместимостью "+maximumCapacity+" открылся");
        locked = false;
        loadedCapacity = 0;
        synchronized (this) {
            this.notifyAll();
        }
    }
}
