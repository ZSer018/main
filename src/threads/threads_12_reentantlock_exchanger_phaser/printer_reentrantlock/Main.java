package threads.threads_12_reentantlock_exchanger_phaser.printer_reentrantlock;

public class Main {

    private final static Printer printer = new Printer();

    public static void main(String[] args) throws InterruptedException {
        new Thread(new PrintCharThread(printer, 'A', 'B')).start();
        new Thread(new PrintCharThread(printer, 'B', 'C')).start();
        new Thread(new PrintCharThread(printer, 'C', 'A')).start();
    }
}
