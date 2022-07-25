package threads.threads_11_semaphore_cyclicbarrier_countdownlatch.semaphore_arm_wrestling;

public class Test {

    public static void main(String[] args) {
        Championship championship = new Championship();

        for (int i = 0; i < 15; i++) {
            new Thread( new ArmWrestler(championship, i) ).start();
        }
    }
}
