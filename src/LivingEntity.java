public abstract class LivingEntity extends Entity {
    int hp;

    LivingEntity(GamePanel gamePanel, int x, int y, int width, int height, String imgURL, int hp) {
        super(gamePanel, x, y, width, height, imgURL);
        this.hp = hp;
    }
}
