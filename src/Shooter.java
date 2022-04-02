public abstract class Shooter extends LivingEntity {

    Shooter(GamePanel gamePanel, int x, int y, int width, int height, String imgURL, int hp) {
        super(gamePanel, x, y, width, height, imgURL, hp);
    }

    public abstract void shoot();
}
