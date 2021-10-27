import javax.swing.*;

public class GameGUI extends JFrame {
    final int WIDTH=1050,HEIGHT=700;
    GamePanel gp;

    public GameGUI(){
        setTitle("BlitzSensou");
        gp=new GamePanel();
        add(gp);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        setSize(WIDTH,HEIGHT);

    }
}
