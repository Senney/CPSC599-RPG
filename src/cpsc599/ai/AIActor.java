package cpsc599.ai;

import com.badlogic.gdx.math.Vector2;
import cpsc599.assets.Actor;
import cpsc599.assets.Dialogue;
import cpsc599.assets.Player;
import cpsc599.managers.PlayerManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class AIActor {
    public static float STEP_TIME = 0.5f; // 500 ms per step.

    protected static class AIAction {
        static String MOVE = "move";
        static String ATTACK = "attack";
        static String SAY = "say";
        static String SKIP = "skip";

        String action;
        Object obj;
        public boolean stopInRange;

        public AIAction(String action, Object obj) {
            this.action = action;
            this.obj = obj;
            this.stopInRange = false;
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

    public boolean step(float time, Dialogue dialogue) {
        if (actionList.size() == 0) return true;
        if (time >= nextStep) {
            AIAction action = actionList.get(0);
            if (action.action == AIAction.MOVE) {
                List<AStarMove> movementList = (List<AStarMove>)action.obj;
                if (movementList.size() <= 1) {
                    actionList.remove(action);
                } else {
                    AStarMove top = movementList.get(movementList.size() - 1);

                    if (!this.actor.canMove()) {
                        actionList.remove(action);
                    } else {
                        this.actor.move(top.x_move, top.y_move, this.pathfinder.getLevel());

                        // Check if we have to stop when we're in range.
                        if (action.stopInRange) {
                            if (top.position.dst(movementList.get(0).position) <= this.actor.range) {
                                actionList.remove(action);
                                return false;
                            }
                        }

                        movementList.remove(top);
                        action.obj = movementList;
                    }
                }
            } else if (action.action == AIAction.ATTACK) {
                Vector2 position = (Vector2)action.obj;

                // Range + 1 so that we can hit diagonal.
                if ((new Vector2(actor.x, actor.y)).dst(position) < (this.actor.range + 1)) {
                    Player target = playerManager.getNearest(position);

                    int dmg = this.actor.attack(target);
                    if (dmg < 0) {
                        dialogue.display("Enemy attacks " + target.getName() + ", but misses!");
                    } else {
                        if (this.actor.range > 2) {
                            dialogue.display("Enemy attacks " + target.getName() + " from range for "
                                    + dmg + " damage!");
                        } else {
                            dialogue.display("Enemy attacks " + target.getName() + " for " + dmg + " damage!");
                        }
                    }
                }
                actionList.remove(action);
            } else if (action.action == AIAction.SKIP) {
                actionList.remove(action);
            } else if (action.action == AIAction.SAY) {
                dialogue.display((String)action.obj);
                actionList.remove(action);
            }

            nextStep = time + STEP_TIME;
        }
        if (actionList.size() == 0) return true;

        return false;
    }

    public void moveTo(int x, int y) {
        Vector2 start = new Vector2(this.actor.x, this.actor.y),
                end = new Vector2(x, y);
        List<AStarMove> movements = pathfinder.getPath(start, end);
        if (movements == null) {
            actionList.add(new AIAction(AIAction.SKIP, null));
            return;
        }

        actionList.add(new AIAction(AIAction.MOVE, movements));
    }

    public void attackTo(int x, int y) {
        Vector2 start = new Vector2(this.actor.x, this.actor.y),
                end = new Vector2(x, y);
        List<AStarMove> movements = pathfinder.getPath(start, end);
        if (movements == null) {
            actionList.add(new AIAction(AIAction.SKIP, null));
            return;
        }

        AIAction moveAction = new AIAction(AIAction.MOVE, movements);
        moveAction.stopInRange = true;

        actionList.add(moveAction);
        actionList.add(new AIAction(AIAction.ATTACK, end));
    }

    public void attack(Player p) {
        actionList.add(new AIAction(AIAction.ATTACK, new Vector2(p.x, p.y)));
    }

    public boolean skipTurn() {
        actionList.add(new AIAction(AIAction.SKIP, null));
        return true;
    }

    /**
     * Determines what actions to take this turn.
     * @return <code>true</code> if the decision making process was successful.
     */
    public abstract boolean decideTurn();
}
