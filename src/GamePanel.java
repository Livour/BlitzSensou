import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class GamePanel extends JPanel {
    final int WIDTH = 1920;
    Player player;
    Vector<Projectile> allyProjectiles;
    Vector<Projectile> enemyProjectiles;
    Vector<LivingEntity> enemies;
    Vector<ScoreDrop> drops;
    Timer refreshScreen;
    Timer cleanup;
    EnemySpawner enemySpawner;
    int score;
    Image backgroundImage;
    int health;

    public GamePanel() {
        newGame();
    }

    public void newGame() {
        backgroundImage = new ImageIcon("Resources\\background.gif").getImage();
        score = 0;
        health = 5;
        drops = new Vector<ScoreDrop>();
        allyProjectiles = new Vector<>();
        enemyProjectiles = new Vector<>();
        enemies = new Vector<>();
        enemySpawner = new EnemySpawner(this);
        enemySpawner.start();
        player = new Player(this, 700, 800, 80);
        this.addKeyListener(new KeyAction(this));
        player.start();
        refreshScreen = new Timer(15, e -> repaint());
        refreshScreen.setRepeats(true);
        refreshScreen.setCoalesce(true);
        refreshScreen.start();

        cleanup = new Timer(100, e -> cleanup());
        cleanup.setRepeats(true);
        cleanup.setCoalesce(true);
        cleanup.start();
    }

    private synchronized void cleanup() {
        for (int i = 0; i < enemyProjectiles.size(); i++)
            if (!enemyProjectiles.get(i).isAlive())
                enemyProjectiles.remove(i);
        for (int i = 0; i < allyProjectiles.size(); i++)
            if (!allyProjectiles.get(i).isAlive())
                allyProjectiles.remove(i);
    }


    public synchronized void paintComponent(Graphics g) {
        super.paintComponent(g);
        //setBackground(Color.orange);
        //System.out.println(getWidth());
        g.drawImage(backgroundImage, 0, 0, getWidth() + 20, getHeight() + 20, null);
        player.draw(g);

        for (int i = 0; i < enemyProjectiles.size(); i++) {
            Projectile p = enemyProjectiles.get(i);
            if (p.isAlive())
                p.draw(g);
        }

        for (int i = 0; i < allyProjectiles.size(); i++) {
            Projectile p = allyProjectiles.get(i);
            if (p.isAlive())
                p.draw(g);
        }

        for (int i = 0; i < drops.size(); i++) {
            ScoreDrop s = drops.get(i);
            if (s.isAlive())
                s.draw(g);
        }
        for (int i = 0; i < enemies.size(); i++) {
            LivingEntity e = enemies.get(i);
            if (e.isAlive())
                e.draw(g);
        }
        g.setFont(new Font("Ariel", Font.BOLD, 28));
        g.setColor(Color.white);
        g.drawString("Score:" + score, 20, getHeight() - 30);
        g.drawString("HP:" + health, 20, getHeight() - 70);
    }
}
