import javax.swing.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class EnemySpawner extends Thread {
    static final int[] lanes = {10, 135, 250};
    static final int NUM_OF_ENEMIES=2;
    GamePanel panel;
    Random rng;
    long spawnRate;
    Timer increaseDifficultyCooldown;

    public EnemySpawner(GamePanel panel) {
        spawnRate = 1500;
        this.panel = panel;
        this.rng = new Random();
        increaseDifficultyCooldown = new Timer(5000, e -> increaseDifficulty());
        increaseDifficultyCooldown.setRepeats(true);
        increaseDifficultyCooldown.setCoalesce(true);
        increaseDifficultyCooldown.start();
    }

    private int randomPosition() {
        return rng.nextInt(this.panel.getWidth());
    }

    private void increaseDifficulty() {
        if (spawnRate > 50)
            spawnRate -= 50;
    }

    private LivingEntity newRandomEnemy() {
        int randomNum = rng.nextInt(NUM_OF_ENEMIES);
        int randomLane = lanes[rng.nextInt(lanes.length)];
        switch (randomNum) {
            case 1:
                return new Sigma(panel, rng.nextInt(panel.WIDTH), -200, 128, 92,randomLane);
            default://case 0
                return new Mosquito(panel, rng.nextInt(panel.WIDTH), -200, 77, 69,randomLane);
        }
    }

    @Override
    public void run() {

        while (true) {
            LivingEntity enemy = newRandomEnemy();
            panel.enemies.add(enemy);
            enemy.start();
            try {
                TimeUnit.MILLISECONDS.sleep(spawnRate);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
