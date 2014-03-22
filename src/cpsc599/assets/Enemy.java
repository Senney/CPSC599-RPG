package cpsc599.assets;

//The class for a CPU controlled entity

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import cpsc599.items.Inventory;
import cpsc599.util.CoordinateTranslator;
import cpsc599.util.Logger;

public class Enemy extends Actor{
    private AnimatedSprite enemySprite;

    private Inventory enemyInventory;

    // to be used for animation.
    //private float moveStart, moveEnd;

    public Enemy(AnimatedSprite sprite, int x, int y, int moveDist) {
        this.enemySprite = sprite;
        this.x = x;
        this.y = y;
        this.maxMove = moveDist;

        this.maxHealth = 10;
        this.currentHealth = 7;

        //this.moveStart = 0f;
    }

    public void tick() {

    }

    public boolean attack(Player player)
    {
        Boolean isDead = false;
        int damage = (strength + enemyInventory.getEquip(Inventory.RHAND_SLOT).damage) - (player.defence);
        player.currentHealth -= damage;
        if(player.currentHealth <= 0)
            isDead = true;
        return isDead;
    }

    public void heal(int amount)
    {
        currentHealth += amount;
        if(currentHealth > maxHealth)
            currentHealth = maxHealth;
    }

    /**
     * Requires that SpriteBatch.begin() has already been called.
     * @param batch The SpriteBatch.
     */
    public void render(SpriteBatch batch) {
        enemySprite.render(batch, CoordinateTranslator.translate(x), CoordinateTranslator.translate(y));
    }

    public void move(int x, int y) {

    }
}
