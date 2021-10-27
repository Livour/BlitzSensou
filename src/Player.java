import java.awt.*;

public class Player extends Shooter{
    static final String playerImage="Resources\\Images\\Player.png";

    GamePanel gamePanel;
    int hitWidth,hitHeight;

    Player(int x, int y, int width, int height, int hp, GamePanel gamePanel) {
        super(x, y, width, height, hp, playerImage);
        this.gamePanel=gamePanel;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(img,x,y,width,height,null);
        g2d.drawRect(x,y,width,height);
    }

    @Override
    public void run() {
        while(true){

            gamePanel.repaint();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
