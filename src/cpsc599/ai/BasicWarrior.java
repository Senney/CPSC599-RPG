package cpsc599.ai;

import com.badlogic.gdx.math.Vector2;
import cpsc599.assets.Actor;
import cpsc599.assets.Dialogue;
import cpsc599.assets.Player;
import cpsc599.managers.PlayerManager;
import cpsc599.util.Logger;

import java.util.List;
import java.util.Random;

public class BasicWarrior extends AIActor {
    public BasicWarrior(PlayerManager playerManager, AStarPathfinder pathfinder, Actor actor) {
        super(playerManager, pathfinder, actor);
    }

    @Override
    public boolean decideTurn() {
        Logger.debug("Deciding turn for BasicWarrior: " + this.actor);

        this.nextStep = 0f;
        Vector2 position = new Vector2(this.actor.x, this.actor.y);
        Player nearest = playerManager.getNearest(position);
        if (nearest == null) {
            return skipTurn();
        }

        Vector2 ppos = new Vector2(nearest.x, nearest.y);
        if (position.dst(ppos) > (this.actor.maxMove * 2)) {
            return skipTurn();
        }

        // Move to the nearest player.
        this.attackTo(nearest.x, nearest.y);

        return true;
    }

}
