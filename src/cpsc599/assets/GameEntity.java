package cpsc599.assets;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Class for representing interactive and dynamic entities within the game.
 */
public abstract class GameEntity {
    public GameEntity() {

    }

    public abstract boolean tick(float time);
    public abstract void render(SpriteBatch batch);
    public abstract void onUse();
    public abstract String onInspect();

    public boolean collides() {
        return false;
    }
}
