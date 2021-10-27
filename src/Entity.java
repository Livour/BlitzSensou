import javax.swing.*;
import java.awt.*;

public abstract class Entity extends Thread {
    int x, y, hp,width,height;
    Image img;

    Entity(int x, int y, int width, int height, int hp, String imgURL){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.hp=hp;
        this.img= (new ImageIcon (imgURL)).getImage();
    }
    @Override
    public abstract void run();
    public abstract void draw(Graphics g);
}
