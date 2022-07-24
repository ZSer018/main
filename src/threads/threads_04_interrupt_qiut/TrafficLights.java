package threads.threads_04_interrupt_qiut;

public class TrafficLights implements Runnable{
    private boolean green = false;

    public void setGreen(boolean green) {
        this.green = green;
    }

    public boolean isGreen() {
        return green;
    }

    @Override
    public void run() {
        do {
            if (!Thread.interrupted()) {
                if (green) {
                    System.out.println("Go!");
                } else {
                    System.out.println("Stay!");
                }
            } else return;

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                return;
            }
        } while (true);
    }
}

