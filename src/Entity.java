import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Vector;

public abstract class Entity extends Thread {
    GamePanel gamePanel;
    double x, y, width, height;
    boolean alive;
    Image img;

    Entity(GamePanel gamePanel, int x, int y, int width, int height, String imgURL) {
        this.gamePanel = gamePanel;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.img = (new ImageIcon(imgURL)).getImage();
        this.alive = true;
    }

    public void setImg(String path) {
        setImg(path, true);
    }

    public void setImg(String path, boolean isGIF) {
        path += isGIF ? ".gif" : ".png";
        this.img = (new ImageIcon(path)).getImage();
    }

    @Override
    public abstract void run();

    public abstract void draw(Graphics g);

    public synchronized boolean isShot(boolean isFriendly) {
        Vector<Projectile> projectiles = isFriendly ? gamePanel.enemyProjectiles : gamePanel.allyProjectiles;
        try {
            for (Projectile p : projectiles) {
                if (p.alive == false || p.isAlive() == false) continue;
                switch (p.type) {
                    case "Player":
                        if (isIntersects(p)) {
                            p.alive = false;
                            return true;
                        }
                        break;
                    case "Mosquito":
                    case "Sigma":
                        if (isIntersectsOval(p)) {
                            p.alive = false;
                            return true;
                        }
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    protected boolean isIntersects(Entity other) {
//        Rectangle r1 = new Rectangle((int) x, (int) y, (int) width, (int) height);
//        Rectangle r2= new Rectangle((int)other.x,(int)other.y,(int)other.width,(int)other.width);
//        return r1.intersects(r2);
        return (x < other.x + other.width && x + width > other.x) && (y < other.y + other.height && y + height > other.y);
    }

    protected boolean isIntersectsOval(Entity other) {
        double radius = other.width / 2;
        return (other.x > x - radius && other.x < x + width) && (other.y > y - radius && other.y < y + height + radius);
    }
}
