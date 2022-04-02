import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

public class Player extends Shooter {
    static final String playerBase = "Resources\\Player\\Player";
    int hitWidth, hitHeight;
    int dirX, dirY;
    Timer shootingCooldown;
    boolean isShooting;

    Player(GamePanel gamePanel, int x, int y, int size, int hp) {
        super(gamePanel, x, y, size * 2 / 3, size, playerBase + ".gif", hp);
        dirX = 0;
        dirY = 0;
        isShooting = false;
        shootingCooldown = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shoot();
            }
        });
        shootingCooldown.setRepeats(true);
        shootingCooldown.setCoalesce(true);
        shootingCooldown.start();
    }


    void playAnimation(int direction) {
        if (direction > 0)
            setImg(playerBase + "Right");
        else if (direction < 0)
            setImg(playerBase + "Left");
        else setImg(playerBase);
    }

    synchronized void move() {
        x += dirX;
        y += dirY;
        constraintBoundaries();
    }

    public synchronized void shoot() {
        if (!isShooting)
            return;
        Projectile playerBullet = new Projectile(gamePanel, x + 8, y - height / 2, 40, 40, "Resources\\Projectiles\\RedSword.png", 1, 0, -1, true);
        gamePanel.projectiles.add(playerBullet);
        playerBullet.start();
    }


    private void constraintBoundaries() {
        int h = gamePanel.getHeight();
        int w = gamePanel.getWidth();

        if (h == 0 || w == 0)
            return;
        if (x + width > w)
            x = w - width;
        if (x < 0)
            x = 0;
        if (y + height > h)
            y = h - height;
        if (y < 0)
            y = 0;

    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(img, x, y, width, height, null);
       // g2d.drawRect(x, y, width, height);
    }


    @Override
    public void run() {
        while (alive) {
            move();
            playAnimation(dirX);
            try {
                TimeUnit.MILLISECONDS.sleep(4);//Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        shootingCooldown.stop();
    }
}


