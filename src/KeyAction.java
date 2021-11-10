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
       // pressedKeys.add(e.getKeyCode());
        //for(int key :pressedKeys) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP: {
                    panel.player.directions.put(panel.player.UP,true);
                    break;
                }
                case KeyEvent.VK_DOWN: {
                    panel.player.directions.put(panel.player.DOWN,true);
                    break;
                }
                case KeyEvent.VK_RIGHT: {
                    panel.player.directions.put(panel.player.RIGHT,true);
                    break;
                }
                case KeyEvent.VK_LEFT: {
                    panel.player.directions.put(panel.player.LEFT,true);
                    break;
                }
                default:
                    break;
            }

    }

    @Override
    public synchronized void keyReleased(KeyEvent e) {
        //pressedKeys.remove(e.getKeyCode());

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP: {
                panel.player.directions.put(panel.player.UP, false);
                break;
            }
            case KeyEvent.VK_DOWN: {
                panel.player.directions.put(panel.player.DOWN, false);
                break;
            }
            case KeyEvent.VK_RIGHT: {
                panel.player.directions.put(panel.player.RIGHT, false);
                break;
            }
            case KeyEvent.VK_LEFT: {
                panel.player.directions.put(panel.player.LEFT, false);
                break;
            }
            default:
                break;
        }
    }
}
