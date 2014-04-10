package cpsc599.assets.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import cpsc599.assets.Actor;
import cpsc599.assets.Dialogue;
import cpsc599.assets.GameEntity;
import cpsc599.controller.PlayerController;
import cpsc599.states.State;
import cpsc599.util.SharedAssets;

public class PrisonerEntity extends GameEntity{

    private int def;
    private boolean used;
    private Dialogue d;

    public PrisonerEntity(int x, int y) {
        super();
        this.used = false;

        this.d = d;

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

        gameState.setFlag("getPrisoner", true);
        this.used = true;

        return "Prisoner: Thanks for rescuing me! I'll help you on your quest.";
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
