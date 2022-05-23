import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Mosquito extends Shooter {
    static final String mosquitoAnimation = "Resources\\Enemies\\Mosquito.gif";
    int dirx;
    Timer shootingCooldown;
    int lane;


    Mosquito(GamePanel gamePanel, int x, int y, int width, int height, int lane) {
        super(gamePanel, x, y, width, height, mosquitoAnimation);
        dirx = Arrays.asList(-1, 1).get(new Random().nextInt(2));
        shootingCooldown = new Timer(900, e -> shoot());
        shootingCooldown.setRepeats(true);
        shootingCooldown.setCoalesce(true);
        shootingCooldown.start();
        this.lane = lane;
    }

    synchronized void move() {
        if (y <= lane) {
            y++;
        }
        if (x <= 0)
            dirx = 1;
        else if (x > gamePanel.getWidth() - width)
            dirx = -1;
        x += dirx;
    }

    @Override
    public void run() {
        while (gamePanel.gameState== GamePanel.state.PLAY&&alive) {
            if (isShot(false)) {
                break;
            }
            move();
            try {
                TimeUnit.MILLISECONDS.sleep(8);//Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(gamePanel.gameState== GamePanel.state.PLAY){
        ScoreDrop scoreDrop = new ScoreDrop(gamePanel, x, y, 70, 35, 1000);
        gamePanel.drops.add(scoreDrop);
        scoreDrop.start();
        }
        shootingCooldown.stop();
        gamePanel.enemies.remove(this);
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(img, (int) x, (int) y, (int) width, (int) height, null);
    }

    @Override
    public void shoot() {
        double angle = Math.atan2(gamePanel.player.y - y, gamePanel.player.x - x);
        //  double angle = Math.toRadians(90);//Math.atan2(90)
        Projectile MonsterBullet = new Projectile(gamePanel, (int) (x + (width / 4)), (int) (y + height / 2), 40, 40, "Resources\\Projectiles\\MosquitoShot.png", 1, false, angle, "Mosquito");
        gamePanel.enemyProjectiles.add(MonsterBullet);
        MonsterBullet.start();
    }
}
