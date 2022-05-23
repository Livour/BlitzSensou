import java.awt.*;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class GrievousBomb extends Entity {

    int xTarget;
    int yTarget;
    boolean done;
    int ttl;//Time To Live
    public static final int MAX_TARGETS = 4;

    public GrievousBomb(GamePanel gamePanel, int x, int y, int x2, int y2, int width, int height) {
        super(gamePanel, x, y, width, height, "Resources\\Enemies\\boom.gif");
        xTarget = x2;
        yTarget = y2;
        ttl = 10;
        done = false;
    }

    @Override
    public void run() {
        while (gamePanel.gameState == GamePanel.state.PLAY && ttl > 0) {
            ttl--;
            try {
                TimeUnit.MILLISECONDS.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            TimeUnit.MILLISECONDS.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics g) {
        if (ttl > 0) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(4));
            g2.setColor(Color.RED);
            g2.drawLine((int) x, (int) y, xTarget, yTarget);
        } else {
            g.drawImage(img, xTarget, yTarget, (int) height, (int) height, gamePanel);
        }
    }

    private static Set<Integer> randomEnemySet(int numbersNeeded, GamePanel gp) {
        Random rng = new Random();

        Set<Integer> generated = new LinkedHashSet<Integer>();//ensures no duplicates
        while (generated.size() < numbersNeeded) {
            Integer next = rng.nextInt(gp.enemies.size());
            generated.add(next);
        }
        return generated;
    }

    public static void castBombs(GamePanel gp) {
        if (gp.specials == 0 || gp.enemies.size() == 0) return;
        gp.specials--;
        int numOfTargets = (gp.enemies.size() < MAX_TARGETS) ? gp.enemies.size() : MAX_TARGETS;
        Set<Integer> chosenEnemies = randomEnemySet(numOfTargets, gp);
        for (Integer i : chosenEnemies) {
            LivingEntity monster = null;
            try {
                monster = gp.enemies.get(i);
            } catch (Exception e) {
            }
            if (monster == null) continue;
            monster.alive = false;
            Player p = gp.player;
            GrievousBomb gb = new GrievousBomb(gp, (int) (p.x + p.width / 2), (int) p.y, (int) monster.x, (int) monster.y, (int) monster.width, (int) monster.height);
            gp.grievousBombs.add(gb);
            gb.start();

        }
    }
}

