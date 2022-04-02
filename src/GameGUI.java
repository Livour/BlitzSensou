import javax.swing.*;

public class GameGUI extends JFrame {
    GamePanel gp;

    public GameGUI() {
        setTitle("BlitzSensou");
        gp = new GamePanel();
        gp.setFocusable(true);
        add(gp);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setVisible(true);
    }
}