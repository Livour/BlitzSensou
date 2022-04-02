import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Projectile extends Entity {
    boolean isFriendly;
    int damage;
    int directionX;
    int directionY;

    Projectile(GamePanel gamePanel, int x, int y, int width, int height, String imgURL, int damage, int directionX, int directionY, boolean isFriendly) {
        super(gamePanel, x, y, width, height, imgURL);
        this.damage = damage;
        this.directionX = directionX;
        this.directionY = directionY;
        this.isFriendly = isFriendly;
    }

    private void isOutOfBoundaries() {
        int h = gamePanel.getHeight();
        int w = gamePanel.getWidth();

        if (h == 0 || w == 0)
            return;
        if ((x + width > w) || (x < 0) || (y > h) || (y < -height)) {
            this.alive = false;
        }

    }

    @Override
    public void run() {
        while (alive) {
            y += directionY;
            x += directionX;
            isOutOfBoundaries();
            try {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        gamePanel.projectiles.remove(this);
    }

    @Override
    public void draw(Graphics g) {
        rotateImg(g);
    }

    public void rotateImg(Graphics g) {
        double degrees;
        Graphics2D g2 = (Graphics2D) g.create();
        Point center = new Point(x + width / 2, y + height / 2);

        //int cursorX = MouseInfo.getPointerInfo().getLocation().x - panel.getLocationOnScreen().x;
        //int cursorY = MouseInfo.getPointerInfo().getLocation().y - panel.getLocationOnScreen().y;
        //double dx = cursorX - center.getX();
        //double dy = cursorY - center.getY();

        //degrees = Math.toDegrees(Math.atan2(dy,dx));

        degrees = Math.toDegrees(Math.atan(-1));
        g2.rotate(Math.toRadians(degrees), center.x, center.y);
        g2.drawImage(img, x, y, width, height, null);
    }
}
