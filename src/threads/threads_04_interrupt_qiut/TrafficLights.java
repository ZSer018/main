package threads.threads_04_interrupt_qiut;

public class TrafficLights extends Thread{
    private boolean green = false;

    public void setGreen(boolean green) {
        this.green = green;
    }

    public boolean isGreen() {
        return green;
    }

    @Override
    public void run() {
        super.run();

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

