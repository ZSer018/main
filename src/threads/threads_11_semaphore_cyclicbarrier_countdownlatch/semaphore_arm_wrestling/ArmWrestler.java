package threads.threads_11_semaphore_cyclicbarrier_countdownlatch.semaphore_arm_wrestling;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeoutException;

public class ArmWrestler implements Runnable {

    private Semaphore championshipSemaphore;
    private Championship championship;
    private int championshipIndex;
    private boolean out = false;
    private boolean winner = false;

    public ArmWrestler(Championship championship, int championshipIndex) {
        this.championship = championship;
        this.championshipIndex = championshipIndex;
        championshipSemaphore = championship.getSemaphore();
    }

    @Override
    public void run() {
        try {
            while (winner | !out) {
                championshipSemaphore.acquire();
                championship.competitionRequest(this);
                championshipSemaphore.release();
           }

        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {

            System.out.println("\n---  Участник " + championshipIndex + " победил в сорвеновании!  ---");
        }


    }

    public void setOut(boolean out) {
        this.out = out;
    }

    public int getChampionshipIndex() {
        return championshipIndex;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }
}
