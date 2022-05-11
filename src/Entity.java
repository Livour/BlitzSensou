import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Vector;

public abstract class Entity extends Thread {
    static final String UP = "UP", DOWN = "DOWN", RIGHT = "RIGHT", LEFT = "LEFT";
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

    protected boolean checkIfOutsideBoundaries() {
        int h = gamePanel.getHeight();
        int w = gamePanel.getWidth();

        if (h == 0 || w == 0)
            return false;
        return (x + width > w) | (x < 0) | (y + height > h) | (y < 0);
    }

    public void setImg(String path, boolean isGIF) {
        path += isGIF ? ".gif" : ".png";
        this.img = (new ImageIcon(path)).getImage();
    }

    @Override
    public abstract void run();

    public abstract void draw(Graphics g);

//    public boolean isShotEnemy() {
//        Rectangle hitbox = new Rectangle(x, y, width, height);
//        for (Projectile p : this.gamePanel.projectiles) {
//            if (hitbox.contains(p.x, p.y, p.width, p.height)) {
//                this.gamePanel.projectiles.remove(p);
//                return true;
//            }
//        }
//        return false;
//    }

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
            System.out.println();
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
