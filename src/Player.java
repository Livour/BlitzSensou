import java.awt.*;

public class Player extends Shooter{
    static final String playerBase="Resources\\GIFs\\Player";
    GamePanel gamePanel;
    int hitWidth,hitHeight;
    int dirX,dirY;

    Player(int x, int y, int size, int hp, GamePanel gamePanel) {
        super(x, y, size*2/3, size, hp, playerBase+".gif");
        this.gamePanel=gamePanel;
    }

    void playAnimation(int direction){
        if(direction>0)
            setImg(playerBase+"Right");
        else if(direction<0)
            setImg(playerBase+"Left");
        else setImg(playerBase);
    }
    void move()
    {
        x+=dirX;
        y+=dirY;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(img,x,y,width,height,null);
    }

    @Override
    public void run()
    {
        while(true)
        {
            move();
            playAnimation(dirX);
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            gamePanel.repaint();
        }
    }
}
