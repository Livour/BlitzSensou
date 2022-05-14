import java.awt.*;
import java.util.concurrent.TimeUnit;

public class ScoreDrop extends Entity {
    int value;
    static final String scorerBase = "Resources\\Drops\\Score-";

    ScoreDrop(GamePanel gamePanel, double x, double y, int width, int height, int value) {
        super(gamePanel, (int) x, (int) y, width, height, scorerBase + value + ".png");
        this.value = value;
    }

    @Override
    public void run() {
        while (gamePanel.gameState == GamePanel.state.PLAY) {
            if (isIntersects(this.gamePanel.player)) {
                try {
                    gamePanel.sem.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                gamePanel.score += value;//Shared variable
                gamePanel.sem.release();
                break;
            }
            if (y > gamePanel.getHeight())
                break;
            y++;

            try {
                TimeUnit.MILLISECONDS.sleep(8);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(img, (int) x, (int) y, (int) width, (int) height, null);
    }
}
