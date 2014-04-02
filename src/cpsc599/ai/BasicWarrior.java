package cpsc599.ai;

import com.badlogic.gdx.math.Vector2;
import cpsc599.assets.Actor;
import cpsc599.assets.Player;
import cpsc599.managers.PlayerManager;
import cpsc599.util.Logger;

import java.util.List;

public class BasicWarrior extends AIActor {
    public BasicWarrior(PlayerManager playerManager, AStarPathfinder pathfinder, Actor actor) {
        super(playerManager, pathfinder, actor);
    }

    @Override
    public boolean step(float time) {
        if (time >= nextStep) {
            AIAction action = actionList.get(0);
            if (action.action == AIAction.MOVE) {
                List<AStarMove> movementList = (List<AStarMove>)action.obj;
                if (movementList.size() == 1) {
                    actionList.remove(action);
                } else {
                    AStarMove top = movementList.get(movementList.size() - 1);

                    //Logger.debug("Moving actor " + this.actor + " in direction (" + top.x_move + ", " + top.y_move + ")");
                    if (!this.actor.canMove()) {
                        actionList.remove(action);
                    } else {
                        this.actor.move(top.x_move, top.y_move, this.pathfinder.getLevel());

                        movementList.remove(top);
                        action.obj = movementList;
                    }
                }
            } else if (action.action == AIAction.ATTACK) {
                Vector2 position = (Vector2)action.obj;
                this.actor.attack(playerManager.getNearest(position));
                actionList.remove(action);
            }

            nextStep += STEP_TIME;
        }
        if (actionList.size() == 0) return true;

        return false;
    }

    @Override
    public void moveTo(int x, int y) {
        Vector2 start = new Vector2(this.actor.x, this.actor.y),
                end = new Vector2(x, y);
        List<AStarMove> movements = pathfinder.getPath(start, end);
        if (movements == null) {
            return;
        }

        actionList.add(new AIAction(AIAction.MOVE, movements));
    }

    @Override
    public void attackTo(int x, int y) {
        Vector2 start = new Vector2(this.actor.x, this.actor.y),
                end = new Vector2(x, y);
        List<AStarMove> movements = pathfinder.getPath(start, end);
        if (movements == null) {
            return;
        }

        actionList.add(new AIAction(AIAction.MOVE, movements));
        actionList.add(new AIAction(AIAction.ATTACK, end));
    }

    @Override
    public boolean decideTurn() {
        Logger.debug("Deciding turn for BasicWarrior: " + this.actor);

        this.nextStep = 0f;
        Vector2 position = new Vector2(this.actor.x, this.actor.y);
        Player nearest = playerManager.getNearest(position);

        // Move to the nearest player.
        this.moveTo(nearest.x, nearest.y);

        return true;
    }
}
