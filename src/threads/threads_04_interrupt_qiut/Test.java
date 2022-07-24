package threads.threads_04_interrupt_qiut;

class Test{

    public static void main(String[] args) throws InterruptedException {
        TrafficLights trafficLights = new TrafficLights();
        Thread thread = new Thread(trafficLights);
        thread.start();

        for (int i = 0; i < 3; i++) {
            Thread.sleep(4000);
            trafficLights.setGreen(!trafficLights.isGreen());
        }

        thread.interrupt();
    }
}
