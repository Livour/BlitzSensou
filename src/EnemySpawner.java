import java.util.Vector;

public class EnemySpawner extends Thread {
    GamePanel panel;

    public EnemySpawner(GamePanel panel) {
        this.panel = panel;
    }

    @Override
    public void run() {
        while (true) {
            if (panel.enemies.size() == 0) {
                LivingEntity enemy = new Mosquito(panel, 500, -200, 77, 69, 100);
                panel.enemies.add(enemy);
                enemy.start();
            }
        }
    }
}
