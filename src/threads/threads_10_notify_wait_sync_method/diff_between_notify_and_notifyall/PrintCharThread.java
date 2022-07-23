package threads.threads_10_notify_wait_sync_method.diff_between_notify_and_notifyall;

public class PrintCharThread extends Thread{

    private final Printer printer;
    private final char requestChar;
    private final char printedChar;

    public PrintCharThread(Printer printer, char requestChar, char printedChar) {
        this.printer = printer;
        this.requestChar = requestChar;
        this.printedChar = printedChar;
    }

    @Override
    public void run() {
        super.run();

        for (int i = 0; i < 5; i++) {
            try {
                synchronized (printer) {
                    while (printer.getPrintChar() != requestChar) {
                        printer.wait();
                    }
                    printer.setCharAndPrint(printedChar);
                    printer.notify();

                    //TODO need to be used notifyAll method or wait time limit
                    //printer.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}
