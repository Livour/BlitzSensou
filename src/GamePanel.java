import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    Player player;

    public GamePanel(){
        player=new Player(400,400,100,100,100,this);
        player.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //setBackground(Color.orange);
        player.draw(g);
        g.drawImage((new ImageIcon ("touhou.png")).getImage(),400,400,100,100,null);
    }
}
