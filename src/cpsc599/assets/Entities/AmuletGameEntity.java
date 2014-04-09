package cpsc599.assets.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import cpsc599.assets.Actor;
import cpsc599.assets.GameEntity;
import cpsc599.states.State;
import cpsc599.util.SharedAssets;

public class AmuletGameEntity extends GameEntity{
    private int hit;
    private boolean used;

    public AmuletGameEntity(int x, int y, int hit) {
        super();
        this.hit = hit;
        this.used = false;

        super.objSprite = new Sprite(SharedAssets.amulet);
        super.setPosition(x, y);
        super.inspect = "just seeing this amulet makes you feel like you could hit any target";
    }

    @Override
    public boolean tick(float time, State gameState) {
        return false;
    }

    @Override
    public String onUse(State gameState, Actor activator) {
        if (this.used) return "All power this remnant once had is long gone...";

        activator.hit = activator.hit + hit;
        activator.speed = activator.speed + hit;
        this.used = true;
        return "Player's accuracy has been increased by " + this.hit + " !";
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
