package cpsc599.ai;

import com.badlogic.gdx.math.Vector2;
import cpsc599.assets.Actor;
import cpsc599.assets.Dialogue;
import cpsc599.managers.PlayerManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class AIActor {
    public static float STEP_TIME = 0.5f; // 500 ms per step.

    protected static class AIAction {
        static String MOVE = "move";
        static String ATTACK = "attack";

        String action;
        Object obj;

        public AIAction(String action, Object obj) {
            this.action = action;
            this.obj = obj;
        }
    }

    protected List<AIAction> actionList;

    /**
     * The time at which the next step should take place.
     */
    protected float nextStep;
    protected PlayerManager playerManager;
    protected AStarPathfinder pathfinder;
    protected Actor actor;

    public AIActor(PlayerManager playerManager, AStarPathfinder pathfinder, Actor actor) {
        this.playerManager = playerManager;
        this.pathfinder = pathfinder;
        this.actor = actor;
        this.actionList = new ArrayList<AIAction>();
    }

    public boolean inTurn() {
        return actionList.size() > 0;
    }

    public void showMessage(String text, Dialogue dialogue) {
        if (dialogue != null) {
            dialogue.setDialogueText(text);
            dialogue.setVisibility(true);
        }
    }

    /**
     * Attempt to step the actor through their turn.
     *
     * @param time
     * @param dialogue
     * @return <code>true</code> if the turn is over;
     *         <code>false</code> if the turn still has steps left to take.
     */
    public abstract boolean step(float time, Dialogue dialogue);

    /**
     * Sets up a move to a specified location on the level.
     * @param x
     * @param y
     */
    public abstract void moveTo(int x, int y);

    /**
     * Sets up an attack-move to a specified location.
     * @param x
     * @param y
     */
    public abstract void attackTo(int x, int y);

    /**
     * Determines what actions to take this turn.
     * @return <code>true</code> if the decision making process was successful.
     */
    public abstract boolean decideTurn();
}
