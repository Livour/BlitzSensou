import javax.swing.*;
import java.awt.*;

public abstract class Entity extends Thread {
    static final String UP = "UP", DOWN = "DOWN", RIGHT = "RIGHT", LEFT = "LEFT";
    GamePanel gamePanel;
    int x, y, width, height;
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
}
