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
    private String name;
    private AnimatedSprite playerSprite;

    private Inventory playerInventory;

    private HealthBar playerHealthBar;

    // to be used for animation.
    //private float moveStart, moveEnd;

    public Player(String name, AnimatedSprite sprite) {
        this(name, sprite, 0, 0, 0);
    }

    public Player(String name, AnimatedSprite sprite, int x, int y, int moveDist) {
        this.name = name;
        //this.moveStart = 0f;
        this.playerSprite = sprite;
        this.x = x;
        this.y = y;
        this.maxMove = moveDist;

        this.playerInventory = new Inventory(this);
        this.playerHealthBar = new HealthBar();
        this.range = 1;
    }

    public Player(String name, AnimatedSprite sprite, int x, int y, int moveDist, int hp, int str, int def, int spe, int ev) {
        this.name = name;
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
        range = playerInventory.getEquip(Inventory.RHAND_SLOT).range;
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

    public Inventory getPlayerInventory() {
        return playerInventory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
