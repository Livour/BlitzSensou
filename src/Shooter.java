public abstract class Shooter extends LivingEntity {

    Shooter(GamePanel gamePanel, int x, int y, int width, int height, String imgURL) {
        super(gamePanel, x, y, width, height, imgURL);
    }

    public abstract void shoot();
}
