import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyAction extends KeyAdapter {
    GamePanel panel;

    KeyAction(GamePanel panel){
        this.panel = panel;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        switch(e.getKeyCode()){
            case KeyEvent.VK_UP:
               // panel.player.
        }
    }
}
