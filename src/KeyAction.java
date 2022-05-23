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

        if(e.getKeyCode()==KeyEvent.VK_P){
            if(panel.mp.isPlaying)
                panel.mp.stop();
            else{
                panel.mp.start();
            }
        }
        if (panel.gameState == GamePanel.state.LEADERBOARD) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_L: {//right
                    panel.page++;
                    if (panel.page > 1)
                        panel.page = 0;
                    break;
                }
                case KeyEvent.VK_J: {//left
                    panel.page--;
                    if (panel.page < 0)
                        panel.page = 1;
                    break;
                }
                default:
                    break;
            }
        }
        if (panel.gameState == GamePanel.state.MENU) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_I: {//up
                    panel.commandNum--;
                    if (panel.commandNum < 0)
                        panel.commandNum = 2;
                    break;
                }
                case KeyEvent.VK_K: {//down
                    panel.commandNum++;
                    if (panel.commandNum > 2)
                        panel.commandNum = 0;
                    break;
                }
                case KeyEvent.VK_ENTER: {
                    panel.executeCommand();
                    break;
                }
                default:
                    break;
            }
        }

        if (panel.gameState == GamePanel.state.OVER)
            switch (e.getKeyCode()) {
                case KeyEvent.VK_I: {//up
                    panel.commandNum--;
                    if (panel.commandNum < 0)
                        panel.commandNum = 2;
                    break;
                }
                case KeyEvent.VK_K: {//down
                    panel.commandNum++;
                    if (panel.commandNum > 2)
                        panel.commandNum = 0;
                    break;
                }
                case KeyEvent.VK_ENTER: {
                    panel.executeCommand();
                    break;
                }
                default:
                    break;
            }

        if (panel.gameState == GamePanel.state.PLAY)
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
                case KeyEvent.VK_X: {
                    panel.player.castShield();
                    break;
                }
                case KeyEvent.VK_Z:{
                    GrievousBomb.castBombs(panel);
                }
                default:
                    break;
            }
    }

    @Override
    public synchronized void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
        if (panel.gameState == GamePanel.state.PLAY)
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
        else if (panel.gameState == GamePanel.state.MENU || panel.gameState == GamePanel.state.OVER) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_ESCAPE: {
                    System.exit(1);
                    break;
                }
                default:
                    break;
            }
        } else if (panel.gameState == GamePanel.state.LEADERBOARD) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_ESCAPE: {
                    System.exit(1);
                    break;
                }
                case KeyEvent.VK_SPACE: {
                    panel.changeState();
                    break;
                }
                default:
                    break;
            }
        }

    }
}
