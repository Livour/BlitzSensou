import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Sigma extends Shooter {
    static final String sigmaAnimation = "Resources\\Enemies\\Sigma.png";
    int dirx;
    Timer shootingCooldown;
    int lane;

    Sigma(GamePanel gamePanel, int x, int y, int width, int height,int lane) {
        super(gamePanel, x, y, width, height, sigmaAnimation);
        dirx = Arrays.asList(-1, 1).get(new Random().nextInt(2));
        shootingCooldown = new Timer(1300, e -> shoot());
        shootingCooldown.setRepeats(true);
        shootingCooldown.setCoalesce(true);
        shootingCooldown.start();
        this.lane=lane;
    }

    @Override
    public void run() {
        while (alive&&gamePanel.gameState== GamePanel.state.PLAY) {
            if (isShot(false)) {
                break;
            }
            move();
            try {
                TimeUnit.MILLISECONDS.sleep(8);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(gamePanel.gameState== GamePanel.state.PLAY){
        ScoreDrop scoreDrop = new ScoreDrop(gamePanel, x, y, 70, 35, 2000);
        gamePanel.drops.add(scoreDrop);
        scoreDrop.start();
        }
        shootingCooldown.stop();
        gamePanel.enemies.remove(this);
    }

    synchronized void move() {
        if (y <= lane) {
            y++;
        }
        if (x <= 0){
            dirx = 1;
        }
        else if (x > gamePanel.getWidth() - width)
            dirx = -1;
        x += dirx;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(img, (int) x, (int) y, (int) width, (int) height, null);
    }

    @Override
    public void shoot() {
        double angle = Math.atan2(gamePanel.player.y - y, gamePanel.player.x - x - 150);
        double angle2 = Math.atan2(gamePanel.player.y - y, gamePanel.player.x - x + 150);
        //  double angle = Math.toRadians(90);//Math.atan2(90)
        Projectile monsterBullet1 = new Projectile(gamePanel, (int) (x + (width / 4)), (int) (y + height / 2), 35, 35, "Resources\\Projectiles\\SigmaShot.png", 1, false, angle, "Sigma");
        Projectile monsterBullet2 = new Projectile(gamePanel, (int) (x + (width / 4)), (int) (y + height / 2), 35, 35, "Resources\\Projectiles\\SigmaShot.png", 1, false, angle2, "Sigma");
        gamePanel.enemyProjectiles.add(monsterBullet1);
        gamePanel.enemyProjectiles.add(monsterBullet2);
        monsterBullet1.start();
        monsterBullet2.start();
    }
}
