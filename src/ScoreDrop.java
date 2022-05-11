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
        while (true) {
            if (isIntersects(this.gamePanel.player)) {
                gamePanel.score += value;
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
