import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    Player player;

    public GamePanel(){
        player=new Player(400,400,80,100,this);
        this.addKeyListener(new KeyAction(this));
        player.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //setBackground(Color.orange);
        player.draw(g);
    }
}
