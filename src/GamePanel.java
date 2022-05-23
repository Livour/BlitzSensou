import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class GamePanel extends JPanel {
    //Config
    final int USERNAME_SIZE = 10;
    final int WIDTH = 1920;
    final int ROW = WIDTH / 20;
    final int HEIGHT = 1080;
    final int COL = HEIGHT / 20;
    String title;
    Image characterImg;
    Font defaultFont, arrowFont;
    String username;
    GameSocket socket;
    Leaderboard lb;
    User user;
    boolean isBest;
    MusicPlayer mp;
    Image muted;
    Image unmuted;

    //Entities
    Player player;
    Vector<Projectile> allyProjectiles;
    Vector<Projectile> enemyProjectiles;
    Vector<LivingEntity> enemies;
    Vector<ScoreDrop> drops;
    Vector<GrievousBomb> grievousBombs;

    //Management
    Semaphore sem;
    Timer refreshScreen;
    Timer cleanup;
    EnemySpawner enemySpawner;
    GameTimer timer;
    int score;
    int health;
    int shields;
    int specials;
    Image backgroundImage;

    //Game State
    enum state {PLAY, OVER, MENU, LEADERBOARD}

    state gameState;
    state prevState;
    //Menu
    int commandNum = 0;
    int page = 0;

    public GamePanel(String title, String username) throws IOException, FontFormatException {
        muted = new ImageIcon("Resources\\audio\\mute_icon.png").getImage();
        unmuted = new ImageIcon("Resources\\audio\\unmute_icon.png").getImage();
        mp = new MusicPlayer();
        mp.start();
        socket = new GameSocket();
        socket.startConnection();
        if (socket.status)
            user = socket.getUser(username);
        else user = null;
        this.username = username;
        InputStream is = getClass().getResourceAsStream("\\fonts\\pcsenior.ttf");
        defaultFont = Font.createFont(Font.TRUETYPE_FONT, is);
        arrowFont = new Font("Ariel", Font.BOLD, 80);
        characterImg = new ImageIcon("Resources\\character.gif").getImage();
        this.title = title;
        backgroundImage = new ImageIcon("Resources\\menu.jpg").getImage();
        gameState = state.MENU;
        this.addKeyListener(new KeyAction(this));
    }

    public void changeState() {
        switch (gameState) {
            case PLAY -> {
                gameState = state.OVER;
                backgroundImage = new ImageIcon("Resources\\OverMenu.jpg").getImage();
                break;
            }
            case OVER, MENU -> {
                gameState = state.PLAY;
                break;
            }
            case LEADERBOARD -> {
                gameState = prevState;
                if (prevState == state.OVER) backgroundImage = new ImageIcon("Resources\\OverMenu.jpg").getImage();
                else if (prevState == state.MENU) backgroundImage = new ImageIcon("Resources\\menu.jpg").getImage();
            }
        }
    }

    public void changeToLeaderboardState() {
        if (!socket.status)
            return;
        try {
            lb = socket.getLeaderboard();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        backgroundImage = new ImageIcon("Resources\\Leaderboard.jpg").getImage();
        prevState = gameState;
        gameState = state.LEADERBOARD;
    }

    public void newGame() {
        isBest = false;
        backgroundImage = new ImageIcon("Resources\\background.gif").getImage();
        score = 0;
        gameState = state.PLAY;
        health = 3;
        shields = 3;
        specials = 3;
        commandNum = 0;
        sem = new Semaphore(1);
        timer = new GameTimer(this);
        timer.start();
        drops = new Vector<>();
        allyProjectiles = new Vector<>();
        enemyProjectiles = new Vector<>();
        grievousBombs = new Vector<>();
        enemies = new Vector<>();
        enemySpawner = new EnemySpawner(this);
        enemySpawner.start();
        player = new Player(this, 700, 800, 80);
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

    public void executeCommand() {
        switch (commandNum) {
            case 0:
                newGame();
                break;
            case 1:
                changeToLeaderboardState();
                break;
            case 2:
                System.exit(1);
                break;
            default:
                break;
        }
    }

    private synchronized void cleanup() {
        for (int i = 0; i < enemyProjectiles.size(); i++)
            if (!enemyProjectiles.get(i).isAlive())
                enemyProjectiles.remove(i);
        for (int i = 0; i < allyProjectiles.size(); i++)
            if (!allyProjectiles.get(i).isAlive())
                allyProjectiles.remove(i);

        for (int i = 0; i < grievousBombs.size(); i++)
            if (!grievousBombs.get(i).isAlive())
                grievousBombs.remove(i);
    }

    public synchronized void paintComponent(Graphics g) {
        g.setFont(defaultFont);
        super.paintComponent(g);
        switch (gameState) {
            case PLAY -> {
                paintGame(g);
                break;
            }
            case MENU -> {
                paintMenu(g);
                break;
            }
            case OVER -> {
                paintGameOver(g);
                break;
            }
            case LEADERBOARD -> {
                paintLeaderboard(g);
                break;
            }
        }
    }

    private synchronized void paintGameOver(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(backgroundImage, 0, 0, getWidth() + 20, getHeight() + 20, null);

        String print = "GAME OVER";
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 128));
        int y = ROW * 4;
        int x = getCenteredX("GAME OVER", g2d);
        printMenuText(g2d, print, x, y);

        print = "FINAL SCORE:" + score;
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 50));
        x = getCenteredX(print, g2d);
        y += ROW;
        printMenuText(g2d, print, x, y);


        //display time in a seconds.millis format
        print = "TIME:" + timer.getSeconds();
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 50));
        x = getCenteredX(print, g2d);
        y += ROW;
        printMenuText(g2d, print, x, y);

        if (isBest) {
            print = "NEW PERSONAL BEST!";
            g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 50));
            x = getCenteredX(print, g2d);
            y += ROW;
            printMenuText(g2d, print, x, y);
        }

        print = "RETRY";
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 64));
        x = getCenteredX(print, g2d);
        y += isBest ? ROW : ROW * 2;
        printMenuText(g2d, print, x, y, 64, 0);

        print = "VIEW LEADERBOARD";
        x = getCenteredX(print, g2d);
        y += ROW * 1;
        printMenuText(g2d, print, x, y, 64, 1);

        print = "QUIT";
        x = getCenteredX(print, g2d);
        y += ROW * 1;
        printMenuText(g2d, print, x, y, 64, 2);

        printAudioIcon(g2d);
    }

    private void paintLeaderboard(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.drawImage(backgroundImage, 0, 0, null);
        if (page == 0) paintTopScore(g2d);
        if (page == 1) paintTopTime(g2d);
    }

    private void paintTopScore(Graphics2D g2d) {
        String print = "SCORE LEADERBOARD";
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 80F));
        int x = getCenteredX(print, g2d);
        int y = ROW;
        printMenuText(g2d, print, x, y);
        ArrayList<UserScore> topScore = this.lb.topScore;
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 50F));
        if (topScore.size() == 0) {
            g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 60F));
            print = "No Records";
            x = getCenteredX(print, g2d);
            y += ROW * 3;
            printMenuText(g2d, print, x, y);
        }
        x = COL / 2;
        UserScore us;
        for (int i = 0; i < topScore.size(); i++) {
            y += ROW;
            us = topScore.get(i);
            if (i != 9)
                print = String.format("No.%d  %s %s Score:%d", i + 1, us.username, " ".repeat(USERNAME_SIZE - us.username.length() + 1), us.score);
            else
                print = String.format("No.%d %s %s Score:%d", i + 1, us.username, " ".repeat(USERNAME_SIZE - us.username.length() + 1), us.score);
            printMenuText(g2d, print, x, y);
        }
    }

    private void paintTopTime(Graphics2D g2d) {
        String print = "TIME LEADERBOARD";
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 80F));
        int x = getCenteredX(print, g2d);
        int y = ROW;
        printMenuText(g2d, print, x, y);
        ArrayList<UserTime> topTime = this.lb.topTime;
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 50F));
        if (topTime.size() == 0) {
            g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 60F));
            print = "No Records";
            x = getCenteredX(print, g2d);
            y += ROW * 3;
            printMenuText(g2d, print, x, y);
        }
        x = COL / 2;
        UserTime us;
        for (int i = 0; i < topTime.size(); i++) {
            y += ROW;
            us = topTime.get(i);
            if (i != 9)
                print = String.format("No.%d  %s %s Time:%f", i + 1, us.username, " ".repeat(USERNAME_SIZE - us.username.length() + 1), GameTimer.toSeconds(us.time));
            else
                print = String.format("No.%d %s %s Time:%f", i + 1, us.username, " ".repeat(USERNAME_SIZE - us.username.length() + 1), GameTimer.toSeconds(us.time));
            printMenuText(g2d, print, x, y);
        }

    }

    private synchronized void paintGame(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, getWidth() + 20, getHeight() + 20, this);
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
        for (int i = 0; i < grievousBombs.size(); i++) {
            Entity e = grievousBombs.get(i);
            if (e.isAlive()){
                e.draw(g);
            }
        }
        g.setFont(g.getFont().deriveFont(Font.BOLD, 20F));
        g.setColor(Color.white);
        g.drawString("Score:" + score, 20, getHeight() - 30);
        g.drawString("Specials:" + specials, 20, getHeight() - 60);
        g.drawString("Shields:" + shields, 20, getHeight() - 90);
        g.drawString("HP:" + health, 20, getHeight() - 120);
    }

    private synchronized void paintMenu(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.drawImage(backgroundImage, 0, 0, null);
        //Title
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 124F));
        int x = getCenteredX(title, g2d);
        int y = ROW * 2;
        printMenuText(g2d, title, x, y);
        //draw character
        x = this.WIDTH / 2 - COL * 3;
        y += ROW;
        g2d.drawImage(characterImg, x, y, ROW * 3, ROW * 3, this);
        //menu
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 80F));
        String print = "NEW GAME";
        x = getCenteredX(print, g2d);
        y += ROW * 4;
        printMenuText(g2d, print, x, y, 80, 0);

        print = "VIEW LEADERBOARD";
        x = getCenteredX(print, g2d);
        y += ROW * 1;
        printMenuText(g2d, print, x, y, 80, 1);

        print = "QUIT";
        x = getCenteredX(print, g2d);
        y += ROW * 1;
        printMenuText(g2d, print, x, y, 80, 2);

        g.setFont(g.getFont().deriveFont(Font.BOLD, 20F));
        g.setColor(Color.white);
        if (user == null)
            g.drawString("Welcome " + username + "!", 20, getHeight() - 30);
        else
            g.drawString(String.format("Welcome %s!       Perosnal Best - Score:%d    Time:%f", username, user.score, GameTimer.toSeconds(user.time)), 20, getHeight() - 30);
        g.drawString("Status:", getWidth() - 180, getHeight() - 30);
        if (socket.status) g.setColor(Color.green);
        else g.setColor(Color.red);
        g.fillOval(getWidth() - 30, getHeight() - 50, 25, 25);
        printAudioIcon(g2d);
    }

    private void printMenuText(Graphics2D g2d, String text, int x, int y, int size, int index) {
        printMenuText(g2d, text, x, y);
        if (commandNum == index) {
            printArrow(g2d, x, y, size);
        }
    }

    private void printAudioIcon(Graphics2D g2d) {
        Image toPrint = mp.isPlaying ? unmuted : muted;
        g2d.drawImage(toPrint, 0, 0, ROW * 1, ROW * 1, this);
    }

    private void printMenuText(Graphics2D g2d, String text, int x, int y) {
        g2d.setColor(Color.DARK_GRAY);
        g2d.drawString(text, x + 5, y + 5);
        g2d.setColor(Color.WHITE);
        g2d.drawString(text, x, y);
    }

    private void printArrow(Graphics2D g2d, int x, int y, int size) {
        g2d.setFont(arrowFont);
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, size));
        g2d.setColor(Color.DARK_GRAY);
        g2d.drawString("➤", x - COL * 2 + 5, y + 5);
        g2d.setColor(Color.WHITE);
        g2d.drawString("➤", x - COL * 2, y);
        g2d.setFont(defaultFont);
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, size));
    }

    private int getCenteredX(String text, Graphics2D g2d) {
        int length = (int) g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();
        int x = getWidth() / 2 - length / 2;
        return x;
    }
}
