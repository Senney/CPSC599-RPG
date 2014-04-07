package cpsc599.assets.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import cpsc599.assets.GameEntity;
import cpsc599.states.State;

/**
 * General game entity for a switch.
 */
public class SwitchGameEntity extends GameEntity {
    private String flag_value;
    public static String IDENTIFIER = "Switch";

    public SwitchGameEntity(Sprite sprite, String id, int x, int y) {
        this.identifier = this.IDENTIFIER;
        this.flag_value = id;
        this.objSprite = sprite;
        setPosition(new Vector2(x, y));
    }

    @Override
    public boolean tick(float time, State gameState) {
        return false;
    }

    @Override
    public void onUse(State gameState) {
        boolean lastState = false;
        if (gameState.getFlag(flag_value) != null) {
            lastState = (Boolean)gameState.getFlag(flag_value);
        }

        gameState.setFlag(flag_value, !lastState);
    }
}
