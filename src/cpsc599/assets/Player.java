package cpsc599.assets;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import cpsc599.OrbGame;
import cpsc599.items.Inventory;
import cpsc599.util.CoordinateTranslator;
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

    public int maxMove;
    public int curMove;

    public int x, y;
    private int moveX, moveY;
    private boolean moving;

    private Inventory playerInventory;

    private HealthBar playerHealthBar;

    // to be used for animation.
    //private float moveStart, moveEnd;

    public Player(AnimatedSprite sprite) {
        this(sprite, 0, 0, 0);
    }

    public Player(AnimatedSprite sprite, int x, int y, int moveDist) {
        //this.moveStart = 0f;
        this.playerSprite = sprite;
        this.x = x;
        this.y = y;
        this.maxMove = moveDist;

        this.playerInventory = new Inventory(this);
        this.playerHealthBar = new HealthBar();
    }

    public void tick() {
        // TODO: Find a way to animate this.
        if (moving) {
            this.x = moveX;
            this.y = moveY;
            moving = false;
        }
    }

    public void resetMove() {
        this.curMove = maxMove;
    }

    public HealthBar getPlayerHealthBar() {
        return playerHealthBar;
    }

    /**
     * Requires that SpriteBatch.begin() has already been called.
     * @param batch The SpriteBatch.
     */
    public void render(SpriteBatch batch) {
        playerSprite.render(batch, CoordinateTranslator.translate(x), CoordinateTranslator.translate(y));
    }

    public void move(int x, int y) {
        if (moving) {
            Logger.warn("Player::move - Player is already in motion. Some bug in the controller..");
        }

        if (curMove == 0 && maxMove != 0) {
            Logger.debug("Player::move - Player has 0 movement spaces available.");
            return;
        }

        moveX = this.x + x;
        moveY = this.y + y;
        moving = true;
        curMove--;
    }

    public Inventory getPlayerInventory() {
        return playerInventory;
    }

}
