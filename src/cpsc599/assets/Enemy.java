package cpsc599.assets;

//The class for a CPU controlled entity

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import cpsc599.util.CoordinateTranslator;
import cpsc599.util.Logger;

public class Enemy {
    private AnimatedSprite enemySprite;

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

    public Enemy(AnimatedSprite sprite) {
        this.enemySprite = sprite;
        //this.moveStart = 0f;
    }

    public void tick() {

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
