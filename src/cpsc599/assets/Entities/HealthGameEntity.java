package cpsc599.assets.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import cpsc599.assets.Actor;
import cpsc599.assets.GameEntity;
import cpsc599.states.State;
import cpsc599.util.SharedAssets;

public class HealthGameEntity extends GameEntity {
    private final int hp;

    public HealthGameEntity(int x, int y, int hp) {
        this.setPosition(x, y);
        this.objSprite = new Sprite(SharedAssets.healthPotion);
        this.hp = hp;
    }

    @Override
    public boolean tick(float time, State gameState) {
        return false;
    }

    @Override
    public String onUse(State gameState, Actor activator) {
        activator.heal(this.hp);
        activator.maxHealth += this.hp;

        return "Added " + this.hp + " health to the player's maximum health.";
    }
}
