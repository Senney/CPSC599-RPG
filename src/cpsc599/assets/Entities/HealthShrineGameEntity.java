package cpsc599.assets.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import cpsc599.assets.Actor;
import cpsc599.assets.GameEntity;
import cpsc599.states.State;
import cpsc599.util.SharedAssets;

public class HealthShrineGameEntity extends GameEntity {
    private int heal;
    private boolean used;

    public HealthShrineGameEntity(int x, int y, int heal) {
        super();
        this.heal = heal;
        this.used = false;

        super.objSprite = new Sprite(SharedAssets.healthShrine);
        super.setPosition(x, y);
        super.inspect = "It appears to be a shrine to an ancient goddess of health!";
    }

    @Override
    public boolean tick(float time, State gameState) {
        return false;
    }

    @Override
    public String onUse(State gameState, Actor activator) {
        if (this.used) return "The shrine has been drained of power.";

        activator.heal(this.heal);
        this.used = true;
        return "Player was healed for " + this.heal + " health!";
    }

    @Override
    public boolean collides() {
        return true;
    }
}
