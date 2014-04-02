package cpsc599.assets;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import cpsc599.OrbGame;
import cpsc599.items.Inventory;
import cpsc599.util.CoordinateTranslator;
import cpsc599.util.Logger;

import java.util.Random;

/**
 * Base class for a Player controlled entity.
 */
public class Player extends Actor{
    private AnimatedSprite playerSprite;

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

    public Player(AnimatedSprite sprite, int x, int y, int moveDist, int hp, int str, int def, int spe, int ev) {
        //this.moveStart = 0f;
        this.playerSprite = sprite;
        this.x = x;
        this.y = y;
        this.maxMove = moveDist;

        this.playerInventory = new Inventory(this);
        this.playerHealthBar = new HealthBar();

        currentHealth = hp;
        maxHealth = hp;
        strength = str;
        defence = def;
        speed = spe;
        evade = ev;
    }

    public void updateStats()
    {
        damage = strength + playerInventory.getEquip(Inventory.RHAND_SLOT).damage;
        hit = speed - playerInventory.getEquip(Inventory.RHAND_SLOT).weight;
        dodge = evade - playerInventory.getEquip(Inventory.RHAND_SLOT).weight;
    }

    public void tick() {
        // TODO: Find a way to animate this.
        if (moving) {
            this.x = moveX;
            this.y = moveY;
            moving = false;
        }
    }

    public void endTurn() {
        this.turnOver = true;
    }

    public void resetMove() {
        this.turnOver = false;
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

    public Vector2 move(int x, int y, Level level) {
        if (moving) {
            Logger.warn("Player is already in motion. Some bug in the controller..");
        }

        if (curMove == 0 && maxMove != 0) {
            Logger.debug("Player has 0 movement spaces available.");
            return new Vector2(this.x, this.y);
        }

        if (level.collide(this.x + x, this.y + y)) {
            Logger.debug("Player has collided with solid geometry.");
            return new Vector2(this.x, this.y);
        }

        moveX = this.x + x;
        moveY = this.y + y;
        moving = true;
        curMove--;

        return new Vector2(this.x, this.y);
    }
    public boolean attack(Enemy enemy)
    {
        Boolean isDead = false;
        int dmg = (damage - enemy.defence);
        int hitChance = (hit - enemy.dodge);

        Random rand = new Random();

        int chance = rand.nextInt(101);
        Logger.debug("chance = " + chance);
        if(chance <= hit) {
            Logger.debug("Attacking enemy for " + damage + " damage.");
            enemy.currentHealth -= damage;
            if(enemy.currentHealth <= 0)
                isDead = true;
            return isDead;
        }
        Logger.debug("You missed");
        return false;
    }

    public void heal(int amount)
    {
        currentHealth += amount;
        if(currentHealth > maxHealth)
            currentHealth = maxHealth;
    }

    public Inventory getPlayerInventory() {
        return playerInventory;
    }

}
