import java.awt.*;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Mosquito extends Shooter {
    static final String mosquitoAnimation = "Resources\\Enemies\\Mosquito.gif";
    int dirx;

    Mosquito(GamePanel gamePanel, int x, int y, int width, int height, int hp) {
        super(gamePanel, x, y, width, height, mosquitoAnimation, hp);
        dirx = Arrays.asList(-1, 1).get(new Random().nextInt(2));
    }

    synchronized void move() {
        if (y <= 200) {
            y++;
        }
        if (x <= width)
            dirx = 1;
        else if (x > gamePanel.getWidth() - width)
            dirx = -1;
        x += dirx;
    }

    @Override
    public void run() {
        while (alive) {
            move();
            try {
                TimeUnit.MILLISECONDS.sleep(8);//Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(img, x, y, width, height, null);
    }

    @Override
    public void shoot() {
        /*Projectile playerBullet = new Projectile(gamePanel, x + 8, y - height / 2, 40, 40, "Resources\\Images\\Projectiles\\RedSword.png", 1, 0, -1, true);
        gamePanel.projectiles.add(playerBullet);
        playerBullet.start();*/
    }
}
