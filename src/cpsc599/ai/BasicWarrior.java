package cpsc599.ai;

import com.badlogic.gdx.math.Vector2;
import cpsc599.assets.Actor;
import cpsc599.assets.Player;
import cpsc599.managers.PlayerManager;

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
                AStarMove top = movementList.get(movementList.size() - 1);

                movementList.remove(top);
                action.obj = movementList;
            } else if (action.action == AIAction.ATTACK) {
                Vector2 position = (Vector2)action.obj;
                // this.actor.attack(playerManager.getNearest(position));
            }

            nextStep += STEP_TIME;
        }

        return true;
    }

    @Override
    public void moveTo(int x, int y) {

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
    public boolean decideTurn(Vector2 position) {
        Player nearest = playerManager.getNearest(position);

        // Move to the nearest player.
        this.attackTo(nearest.x, nearest.y);

        return true;
    }
}
