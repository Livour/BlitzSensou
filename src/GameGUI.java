import javax.swing.*;

public class GameGUI extends JFrame {
    final int WIDTH=1050,HEIGHT=1000;
    GamePanel gp;

    public GameGUI(){
        setTitle("BlitzSensou");
        gp=new GamePanel();
        gp.setFocusable(true);
        add(gp);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setVisible(true);
    }
}
