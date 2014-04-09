package cpsc599.ai;

import com.badlogic.gdx.math.Vector2;
import cpsc599.assets.Actor;
import cpsc599.assets.Player;
import cpsc599.managers.PlayerManager;
import cpsc599.util.Logger;

public class OpportunistAI extends AIActor {
    public OpportunistAI(PlayerManager playerManager, AStarPathfinder pathfinder, Actor actor) {
        super(playerManager, pathfinder, actor);
    }

    @Override
    public boolean decideTurn() {
        Logger.debug("Deciding turn for OpportunistAI: " + this.actor);

        this.nextStep = 0f;
        Vector2 position = new Vector2(this.actor.x, this.actor.y);
        Player nearest = playerManager.getNearest(position);
        if (nearest == null) {
            return skipTurn();
        }

        Vector2 ppos = new Vector2(nearest.x, nearest.y);

        // Wait until the player is within movement range.
        if (position.dst(ppos) > (this.actor.maxMove)) {
            return skipTurn();
        }

        // Move to the nearest player.
        this.attackTo(nearest.x, nearest.y);

        return true;
    }
}
