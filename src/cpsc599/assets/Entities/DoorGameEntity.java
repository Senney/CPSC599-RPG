package cpsc599.assets.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import cpsc599.assets.Actor;
import cpsc599.assets.GameEntity;
import cpsc599.states.State;
import cpsc599.util.SharedAssets;

public class DoorGameEntity extends GameEntity {
    public static String IDENTIFIER = "Door";

    private boolean open;

    private Sprite openDoor, closedDoor;
    private String flagName;

    public DoorGameEntity(String switchFlag, int x, int y, boolean open) {
        this.open = open;
        this.openDoor = new Sprite(SharedAssets.doorOpen);
        this.closedDoor = new Sprite(SharedAssets.doorClosed);
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

        return false;
    }

    @Override
    public boolean collides() {
        if (this.open) return false;
        return true;
    }

    @Override
    public String onUse(State gameState, Actor activator) {
        // Assume the flag is a key.
        if (gameState.getFlagBoolean(this.flagName)) {
            gameState.setFlag(this.flagName, false);
            this.open = true;
            setDoorSprite();
            return "Used the door!";
        } else {
            return "The door appears to be locked. You need to find a key.";
        }
    }

    public boolean isOpen() {
        return open;
    }
}
