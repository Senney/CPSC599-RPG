package cpsc599.assets.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
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
    }

    @Override
    public void onUse(State gameState) {

    }

    public boolean isOpen() {
        return open;
    }
}
