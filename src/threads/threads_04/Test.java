package ThreadsLessons.threads_04;

class Test{

    public static void main(String[] args) throws InterruptedException {
        TrafficLights trafficLights = new TrafficLights();
        trafficLights.start();

        for (int i = 0; i < 3; i++) {
            Thread.sleep(2000);
            trafficLights.setGreen(!trafficLights.isGreen());
        }

        trafficLights.interrupt();
    }
}
