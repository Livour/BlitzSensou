import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Projectile extends Entity {
    boolean isFriendly;
    int damage;
    double angle;
    String type;


    Projectile(GamePanel gamePanel, int x, int y, int width, int height, String imgURL, int damage, boolean isFriendly, double angle, String type) {
        super(gamePanel, x, y, width, height, imgURL);
        this.damage = damage;
        this.angle = angle;
        // this.directionX = directionX;
        // this.directionY = directionY;
        //line = new Line2(x,y,targetX,targetY);
        this.isFriendly = isFriendly;
        this.type = type;
    }

    private boolean isOutOfBoundaries() {
        int h = gamePanel.getHeight();
        int w = gamePanel.getWidth();

        if (h == 0 || w == 0)
            return false;
        if ((x + width > w) || (x < 0) || (y > h) || (y < -height)) {
            this.alive = false;
            return true;
        }
        return false;
    }

    public void move() {
        x += Math.cos(angle);
        y += Math.sin(angle);
    }

    @Override
    public void run() {
        while (alive) {
            move();
            if (isOutOfBoundaries()) break;
            try {
                TimeUnit.MILLISECONDS.sleep(isFriendly ? 1 : 2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void draw(Graphics g) {
        if (isFriendly)
            rotateImg(g);
        else {
            g.drawImage(img, (int) x, (int) y, (int) width, (int) height, null);
        }
    }

    public void rotateImg(Graphics g) {
        double degrees;
        Graphics2D g2 = (Graphics2D) g.create();
        Point center = new Point((int) (x + width / 2), (int) (y + height / 2));

        degrees = Math.toDegrees(Math.atan(-1));
        g2.rotate(Math.toRadians(degrees), center.x, center.y);
        g2.drawImage(img, (int) x, (int) y, (int) width, (int) height, null);
    }

}
