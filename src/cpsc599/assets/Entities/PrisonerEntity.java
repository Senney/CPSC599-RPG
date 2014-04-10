package cpsc599.assets.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import cpsc599.assets.Actor;
import cpsc599.assets.GameEntity;
import cpsc599.states.State;
import cpsc599.util.SharedAssets;

public class PrisonerEntity extends GameEntity{

    private int def;
    private boolean used;

    public PrisonerEntity(int x, int y, int defense) {
        super();
        this.def = defense;
        this.used = false;

        super.objSprite = new Sprite(SharedAssets.prisonerSprite.getFrame(0));
        super.setPosition(x, y);
        super.inspect = "A Defective prisoner";
    }

    @Override
    public boolean tick(float time, State gameState) {
        return false;
    }

    @Override
    public String onUse(State gameState, Actor activator) {
        if (this.used) return "All power this remnant once had is long gone...";

        activator.defence = activator.defence + def;
        this.used = true;
        return "Player's defense has been increased by " + this.def + " !";
    }
    @Override
    public void render(SpriteBatch batch) {
        if(this.used) return;
        else
            super.render(batch);
    }

    public void setVisible(boolean isVisible) {
        if(!isVisible)
            this.used = true;
    }

    @Override
    public boolean collides() {
        if(this.used) return false;
        return true;
    }}
