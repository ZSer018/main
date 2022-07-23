package threads.threads_10_notify_wait_sync_method.diff_between_notify_and_notifyall;

public class Printer{

    private char printChar = 'B';

    public char getPrintChar() {
        return printChar;
    }

    public void setCharAndPrint(char printChar) {
        System.out.println(printChar);
        this.printChar = printChar;
    }
}
