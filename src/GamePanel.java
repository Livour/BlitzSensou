import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class GamePanel extends JPanel {
    Player player;
    Vector<Projectile> projectiles;
    Vector<LivingEntity> enemies;
    Timer refreshScreen;
    EnemySpawner enemySpawner;

    public GamePanel() {
        projectiles = new Vector<>();
        enemies = new Vector<>();
        enemySpawner = new EnemySpawner(this);
        enemySpawner.start();
        player = new Player(this, 700, 800, 80, 100);
        this.addKeyListener(new KeyAction(this));
        player.start();
        refreshScreen = new Timer(2, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        refreshScreen.setRepeats(true);
        refreshScreen.setCoalesce(true);
        refreshScreen.start();
    }

    public synchronized void paintComponent(Graphics g) {
        super.paintComponent(g);
        //setBackground(Color.orange);
        g.drawImage(new ImageIcon("Resources\\background.gif").getImage(), 0, 0, getWidth() + 20, getHeight() + 20, null);
        player.draw(g);
        for (Projectile p : projectiles)
            p.draw(g);
        for(LivingEntity e:enemies){
            e.draw(g);
        }
    }
}
