import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Player extends Shooter{
    static final String playerImage="Resources\\Images\\Player.png";
    GamePanel gamePanel;
    int hitWidth,hitHeight;
    HashMap<String,Boolean> directions=new HashMap<>();

    Player(int x, int y, int width, int height, int hp, GamePanel gamePanel) {
        super(x, y, width, height, hp, playerImage);
        this.gamePanel=gamePanel;
        directions.put(UP,false);
        directions.put(DOWN,false);
        directions.put(LEFT,false);
        directions.put(RIGHT,false);
    }

    boolean isMoving(){
        for(Map.Entry<String,Boolean> pair :directions.entrySet()){
            if(pair.getValue()) return true;
        }
        return false;
    }

    void move()
    {
        if(isMoving()){
            if(directions.get(LEFT)){
                x-=1;
            }
            if(directions.get(RIGHT)){
                x+=1;
            }
            if(directions.get(UP)){
                y-=1;
            }
            if(directions.get(DOWN)){
                y+=1;
            }
        }
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
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            gamePanel.repaint();
        }
    }
}
