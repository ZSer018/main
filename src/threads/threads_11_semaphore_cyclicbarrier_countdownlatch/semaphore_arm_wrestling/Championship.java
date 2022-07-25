package threads.threads_11_semaphore_cyclicbarrier_countdownlatch.semaphore_arm_wrestling;

import java.util.Random;
import java.util.concurrent.*;

public class Championship {

    private CyclicBarrier startCompetitionRound;
    private Semaphore semaphore;

    private int armWrestlersOnCompetition = 0;
    private ArmWrestler[] armWrestlers = new ArmWrestler[2];
    private Random random = new Random();

    public Championship(){
        startCompetitionRound = new CyclicBarrier(2, this::beginCompetition);
        semaphore = new Semaphore(2);
    }

    private void beginCompetition(){
        int looser = random.nextInt(2);
        armWrestlers[ looser ].setOut(true);

        System.out.println("--------Новый раунд--------");
        System.out.print("Бороться будут два претендента: ");
        for (int i = 0; i < armWrestlers.length; i++) {
            System.out.print(" участник # "+armWrestlers[i].getChampionshipIndex());
        }
        System.out.println();

        if (looser == 0) {
            System.out.println("Участник "+ armWrestlers[ 1 ].getChampionshipIndex() +" победил в состязании");
        } else {
            System.out.println("Участник " + armWrestlers[ 0 ].getChampionshipIndex() + " победил в состязании");
        }

        System.out.println("Участник " + armWrestlers[ looser ].getChampionshipIndex() + " выбыл");

        armWrestlersOnCompetition = 0;
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startCompetitionRound = new CyclicBarrier(2, this::beginCompetition);
    }

    public void competitionRequest(ArmWrestler armWrestler) throws InterruptedException, BrokenBarrierException, TimeoutException {
        armWrestlers[armWrestlersOnCompetition++] = armWrestler;
        startCompetitionRound.await(1, TimeUnit.SECONDS);
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }
}
