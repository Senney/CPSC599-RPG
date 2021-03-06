package cpsc599.assets.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import cpsc599.assets.Actor;
import cpsc599.assets.GameEntity;
import cpsc599.states.State;
import cpsc599.util.SharedAssets;

public class SwordGameEntity extends GameEntity{
    private int str;
    private boolean used;

    public SwordGameEntity(int x, int y, int attack) {
        super();
        this.str = attack;
        this.used = false;

        super.objSprite = new Sprite(SharedAssets.sword);
        super.setPosition(x, y);
        super.inspect = "A metallic shard glimmers and hums with power.";
    }

    @Override
    public boolean tick(float time, State gameState) {
        return false;
    }

    @Override
    public String onUse(State gameState, Actor activator) {
        if (this.used) return "All power this remnant once had is long gone...";

        activator.strength = activator.strength + str;
        activator.damage = activator.damage + str;
        this.used = true;
        return "Player's attack has been increased by " + this.str + " !";
    }

    @Override
    public void render(SpriteBatch batch) {
        if(this.used) return;
        else
            super.render(batch);
    }

    @Override
    public boolean collides() {
        if(this.used) return false;
        return true;
    }
}
