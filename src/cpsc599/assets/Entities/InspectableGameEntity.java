package cpsc599.assets.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import cpsc599.assets.Actor;
import cpsc599.assets.GameEntity;
import cpsc599.states.State;

public class InspectableGameEntity extends GameEntity{
    private boolean collides;

    public InspectableGameEntity(Sprite sprite, int x, int y, String inspect, boolean collides) {
        super();

        this.collides = collides;

        super.setPosition(x, y);
        super.inspect = inspect;
    }

    @Override
    public boolean collides() {
        return this.collides;
    }

    @Override
    public boolean tick(float time, State gameState) {
        return false;
    }

    @Override
    public String onUse(State gameState, Actor activator) {
        return "Nothing happens.";
    }
}
