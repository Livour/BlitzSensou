import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GameGUI extends JFrame {
    final String TITLE= "BlitzSensou";
    GamePanel gp;

    public GameGUI(String username) throws IOException, FontFormatException {
        setTitle(TITLE);
        gp = new GamePanel(TITLE,username);
        gp.setFocusable(true);
        add(gp);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setVisible(true);
    }
}