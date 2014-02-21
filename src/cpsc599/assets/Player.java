package cpsc599.assets;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import cpsc599.OrbGame;
import cpsc599.util.Logger;

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
    private boolean moving;

    // to be used for animation.
    //private float moveStart, moveEnd;

    public Player(AnimatedSprite sprite) {
        this.playerSprite = sprite;
        //this.moveStart = 0f;
    }

    public void tick() {
        // TODO: Find a way to animate this.
        if (moving) {
            this.x = moveX;
            this.y = moveY;
            moving = false;
        }
    }

    /**
     * Requires that SpriteBatch.begin() has already been called.
     * @param batch The SpriteBatch.
     */
    public void render(SpriteBatch batch) {
        playerSprite.render(batch, x * 16, y * 16);
    }

    public void move(int x, int y) {
        if (moving) {
            Logger.warn("Player::move - Player is already in motion. Some bug in the controller..");
        }

        moveX = this.x + x;
        moveY = this.y + y;
        moving = true;
    }
}
