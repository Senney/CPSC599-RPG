package cpsc599.assets.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import cpsc599.assets.Actor;
import cpsc599.assets.GameEntity;
import cpsc599.states.State;
import cpsc599.util.SharedAssets;

public class ShieldGameEntity extends GameEntity{
    private int agi;
    private boolean used;

    public ShieldGameEntity(int x, int y, int evasion) {
        super();
        this.agi = evasion;
        this.used = false;

        super.objSprite = new Sprite(SharedAssets.shield);
        super.setPosition(x, y);
        super.inspect = "That shield looks like it could deflect most blows.";
    }

    @Override
    public boolean tick(float time, State gameState) {
        return false;
    }

    @Override
    public String onUse(State gameState, Actor activator) {
        if (this.used) return "All power this remnant once had is long gone...";

        activator.dodge = activator.dodge + agi;
        activator.evade = activator.evade + agi;
        this.used = true;
        return "Player's evasion has been increased by " + this.agi + " !";
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
