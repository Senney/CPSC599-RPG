package cpsc599.assets;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import cpsc599.states.State;
import cpsc599.util.CoordinateTranslator;

/**
 * Class for representing interactive and dynamic entities within the game.
 */
public abstract class GameEntity {
    protected String identifier;
    protected String inspect;

    protected Sprite objSprite;
    protected Vector2 position;

    public GameEntity() {
        this.identifier = "";
        this.inspect = "";
        this.objSprite = null;
        this.position = new Vector2(-1, -1);
    }

    public abstract boolean tick(float time, State gameState);
    public abstract String onUse(State gameState, Actor activator);
    public void render(SpriteBatch batch) {
        if (objSprite != null) {
            batch.draw(objSprite, CoordinateTranslator.translate((int)position.x),
                    CoordinateTranslator.translate((int)position.y));
        }
    }

    public Vector2 getPosition() { return this.position; }
    public void setPosition(int x, int y) { this.setPosition(new Vector2(x, y)); }
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
