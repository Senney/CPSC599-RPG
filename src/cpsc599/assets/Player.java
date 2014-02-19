package cpsc599.assets;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Base class for a Player controlled entity.
 */
public class Player {
    private AnimatedSprite playerSprite;

    public int maxHealth;
    public int currentHealth;
    public int strength;
    public int defence;
    public int speed;

    public int x, y;

    private int moveX, moveY;

    public Player(AnimatedSprite sprite) {
        this.playerSprite = sprite;
    }

    private static final float MOVE_TICK_TIME = 0.4f;
    public void tick() {

    }

    /**
     * Requires that SpriteBatch.begin() has already been called.
     * @param batch The spritebatch.
     */
    public void render(SpriteBatch batch) {
        playerSprite.render(batch, x * 16, y * 16);
    }

    public void moveTo(int x, int y) {
        
    }
}
