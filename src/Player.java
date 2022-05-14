import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

public class Player extends Shooter {
    static final String playerBase = "Resources\\Player\\Player";
    static final long INVINCIBILITY_TIME = 3000;
    int dirX, dirY;
    Timer shootingCooldown;
    boolean isShooting;
    boolean isInvincible;
    long lastInvi;
    Image shieldImg;

    Player(GamePanel gamePanel, int x, int y, int size) {
        super(gamePanel, x, y, size * 2 / 3, size, playerBase + ".gif");
        lastInvi = 0;
        dirX = 0;
        dirY = 0;
        isShooting = false;
        shootingCooldown = new Timer(200, e -> shoot());
        shootingCooldown.setRepeats(true);
        shootingCooldown.setCoalesce(true);
        shootingCooldown.start();
        shieldImg = new ImageIcon(playerBase + "Shield.png").getImage();
    }

    void castShield(){
        if(gamePanel.shields==0) return;
        gamePanel.shields--;
        lastInvi=System.currentTimeMillis();
        isInvincible=true;
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
        double angle = Math.toRadians(-90);
        Projectile playerBullet = new Projectile(gamePanel, (int) x + 8, (int) (y - height / 2), 40, 40, "Resources\\Projectiles\\RedSword.png", 1, true, angle, "Player");
        gamePanel.allyProjectiles.add(playerBullet);
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
        int maxHeight = 375;//Biggest Lane+lane size
        if (y < maxHeight)
            y = maxHeight;

    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        if (isInvincible)
            g2d.drawImage(shieldImg, (int) x - 5, (int) y, (int) width + 15, (int) height, null);
        g2d.drawImage(img, (int) x, (int) y, (int) width, (int) height, null);
        // g2d.drawRect(x, y, width, height);
    }


    @Override
    public void run() {
        while (gamePanel.health > 0 && gamePanel.gameState == GamePanel.state.PLAY) {
            long time = System.currentTimeMillis();
            isInvincible = !(time > lastInvi + INVINCIBILITY_TIME);
            move();
            if (isShot(true)) {
                if (!isInvincible) {
                    lastInvi = time;
                    gamePanel.health--;
                }
            }
            playAnimation(dirX);
            try {
                TimeUnit.MILLISECONDS.sleep(5 / 2);//Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        shootingCooldown.stop();
        gamePanel.changeState();


    }
}


