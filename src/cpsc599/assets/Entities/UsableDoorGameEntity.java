package cpsc599.assets.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import cpsc599.assets.Actor;
import cpsc599.assets.GameEntity;
import cpsc599.states.State;
import cpsc599.util.SharedAssets;

public class UsableDoorGameEntity extends GameEntity {
    private final String flag;
    private boolean open;

    private Sprite openDoor, closedDoor;

    public UsableDoorGameEntity(String flag, int x, int y, boolean open) {
        setPosition(x, y);
        this.flag = flag;
        this.open = open;

        this.openDoor = new Sprite(SharedAssets.doorOpen);
        this.closedDoor = new Sprite(SharedAssets.doorClosed);
        setDoorSprite();

        this.inspect = "This door looks like it could be opened easily.";
    }

    private void setDoorSprite() {
        if (open) this.objSprite = this.openDoor;
        else this.objSprite = this.closedDoor;
    }

    @Override
    public boolean tick(float time, State gameState) {
        Object status = gameState.getFlag(flag);
        if (status != null) {
            boolean bstatus = (Boolean)status;
            if ((bstatus && !open) || (!bstatus && open)) {
                this.open = bstatus;
                setDoorSprite();
                return true;
            }
        }

        return false;
    }

    @Override
    public String onUse(State gameState, Actor activator) {
        this.open = !this.open;
        setDoorSprite();
        gameState.setFlag(this.flag, this.open);
        return this.open ? "Opened the door." : "Closed the door";
    }

    @Override
    public boolean collides() {
        return !this.open;
    }
}
