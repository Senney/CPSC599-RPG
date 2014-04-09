package cpsc599.assets.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import cpsc599.assets.Actor;
import cpsc599.assets.GameEntity;
import cpsc599.states.State;

public class DoorGameEntity extends GameEntity {
    public static String IDENTIFIER = "Door";

    private boolean open;

    private Sprite openDoor, closedDoor;
    private String flagName;

    public DoorGameEntity(String switchFlag, Sprite closed, Sprite opened, int x, int y, boolean open) {
        this.open = open;
        this.openDoor = opened;
        this.closedDoor = closed;
        this.flagName = switchFlag;

        this.identifier = this.IDENTIFIER;
        setPosition(new Vector2(x, y));
        setDoorSprite();

        this.inspect = "A door, it looks like it's opened elsewhere!";
    }

    private void setDoorSprite() {
        if (open) this.objSprite = this.openDoor;
        else this.objSprite = this.closedDoor;
    }

    @Override
    public boolean tick(float time, State gameState) {
        Object status = gameState.getFlag(flagName);
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
    public boolean collides() {
        if (this.open) return false;
        return true;
    }

    @Override
    public String onUse(State gameState, Actor activator) {
        this.open = !open;
        gameState.setFlag(flagName, open);
        setDoorSprite();
        return "Used the door!";
    }

    public boolean isOpen() {
        return open;
    }
}
