import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class KeyAction extends KeyAdapter {
    GamePanel panel;
    final Set<Integer> pressedKeys = new HashSet<>();
    KeyAction(GamePanel panel){
        this.panel = panel;
    }

    @Override
    public synchronized void keyPressed(KeyEvent e) {
        if(pressedKeys.contains(e.getKeyCode()))
            return;
        pressedKeys.add(e.getKeyCode());
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP: {
                    panel.player.dirY-=1;
                    break;
                }
                case KeyEvent.VK_DOWN: {
                    panel.player.dirY+=1;
                    break;
                }
                case KeyEvent.VK_RIGHT: {
                    panel.player.dirX+=1;
                    break;
                }
                case KeyEvent.VK_LEFT: {
                    panel.player.dirX-=1;
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
            case KeyEvent.VK_UP: {
                panel.player.dirY+=1;
                break;
            }
            case KeyEvent.VK_DOWN: {
                panel.player.dirY-=1;
                break;
            }
            case KeyEvent.VK_RIGHT: {
                panel.player.dirX-=1;
                break;
            }
            case KeyEvent.VK_LEFT: {
                panel.player.dirX+=1;
                break;
            }
            case KeyEvent.VK_ESCAPE:
                System.exit(1);
            default:
                break;
        }
    }
}
