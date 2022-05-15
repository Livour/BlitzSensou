import java.util.concurrent.TimeUnit;

public class GameTimer extends Thread {
    long startTime;
    long time;
    GamePanel gamePanel;

    GameTimer(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.startTime = System.currentTimeMillis();
    }

    public double getSeconds() {
        return toSeconds(time);
    }

    public static double toSeconds(long time) {
        return ((double) time / 1000);
    }

    @Override
    public void run() {
        while (gamePanel.gameState == GamePanel.state.PLAY) {
            time = System.currentTimeMillis() - startTime;
            try {
                TimeUnit.MILLISECONDS.sleep(8);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
