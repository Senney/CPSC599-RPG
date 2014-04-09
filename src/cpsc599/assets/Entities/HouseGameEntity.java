package cpsc599.assets.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import cpsc599.assets.GameEntity;
import cpsc599.states.State;

public class HouseGameEntity extends GameEntity {
    String flag;
    String use;

    public HouseGameEntity(Sprite texture, int x, int y, String flag, String inspect, String use) {
        this.objSprite = texture;
        this.setPosition(new Vector2(x, y));

        this.flag = flag;
        this.inspect = inspect;
        this.use = use;
    }

    @Override
    public boolean tick(float time, State gameState) {
        return false;
    }

    @Override
    public String onUse(State gameState) {
        gameState.setFlag(flag, true);
        return use;
    }
}
