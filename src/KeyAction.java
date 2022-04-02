import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class KeyAction extends KeyAdapter {
    GamePanel panel;
    final Set<Integer> pressedKeys = new HashSet<>();

    KeyAction(GamePanel panel) {
        this.panel = panel;
    }

    @Override
    public synchronized void keyPressed(KeyEvent e) {
        if (pressedKeys.contains(e.getKeyCode()))
            return;
        pressedKeys.add(e.getKeyCode());

        switch (e.getKeyCode()) {
            case KeyEvent.VK_I: {
                panel.player.dirY -= 1;
                break;
            }
            case KeyEvent.VK_K: {
                panel.player.dirY += 1;
                break;
            }
            case KeyEvent.VK_L: {
                panel.player.dirX += 1;
                break;
            }
            case KeyEvent.VK_J: {
                panel.player.dirX -= 1;
                break;
            }
            case KeyEvent.VK_SPACE: {
                panel.player.isShooting = true;
                break;
            }
            default:
                break;
        }

    }

    @Override
    public synchronized void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
        switch (e.getKeyCode()) {
            case KeyEvent.VK_I: {//up
                panel.player.dirY += 1;
                break;
            }
            case KeyEvent.VK_K: {//down
                panel.player.dirY -= 1;
                break;
            }
            case KeyEvent.VK_L: {//right
                panel.player.dirX -= 1;
                break;
            }
            case KeyEvent.VK_J: {//left
                panel.player.dirX += 1;
                break;
            }
            case KeyEvent.VK_SPACE: {
                panel.player.isShooting = false;
                break;
            }
            case KeyEvent.VK_ESCAPE: {
                System.exit(1);
                break;
            }
            default:
                break;
        }
    }
}
