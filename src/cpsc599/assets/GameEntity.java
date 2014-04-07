package cpsc599.assets;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Class for representing interactive and dynamic entities within the game.
 */
public abstract class GameEntity {
    protected String identifier;
    protected String inspect;

    private Sprite objSprite;
    protected Vector2 position;

    public GameEntity() {
        this.identifier = "";
        this.inspect = "";
        this.objSprite = null;
        this.position = new Vector2(-1, -1);
    }

    public abstract boolean tick(float time);
    public abstract void render(SpriteBatch batch);
    public abstract void onUse();

    public Vector2 getPosition() { return this.position; }
    public void setPosition(Vector2 pos) { this.position = pos; }

    public String onInspect() {
        return inspect;
    }

    public String getIdentifier() {
        return identifier;
    }

    public boolean collides() {
        return false;
    }
}
