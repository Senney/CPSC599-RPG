package cpsc599.ai;

import com.badlogic.gdx.math.Vector2;
import cpsc599.assets.Actor;
import cpsc599.assets.Level;
import cpsc599.assets.Player;
import cpsc599.managers.PlayerManager;
import cpsc599.util.Logger;

import java.util.Random;

/**
 * Created by srheintz on 09/04/14.
 */
public class WanderingAI extends AIActor {
    public WanderingAI(PlayerManager playerManager, AStarPathfinder pathfinder, Actor actor) {
        super(playerManager, pathfinder, actor);
    }

    private Vector2 findLocationInRange(Vector2 position, Level level, int range) {
        int iterations = 0;
        Random rand = new Random(System.currentTimeMillis());

        while (iterations < 10) {
            int xr = (rand.nextInt(range) + 1) - (range/2), yr = (rand.nextInt(range) + 1) - (range / 2);
            int nx = (int)position.x + xr, ny = (int)position.y + yr;
            if (!level.collide(nx, ny)) {
                return new Vector2(nx, ny);
            }

            iterations++;
        }

        return null;
    }

    @Override
    public boolean decideTurn() {
        Logger.debug("Deciding turn for WanderingAI: " + this.actor);

        this.nextStep = 0f;
        Vector2 position = new Vector2(this.actor.x, this.actor.y);
        Player nearest = playerManager.getNearest(position);
        if (nearest == null) {
            return skipTurn();
        }

        Vector2 ppos = new Vector2(nearest.x, nearest.y);
        // Wait until the player is within movement range.
        if (position.dst(ppos) > (this.actor.maxMove)) {
            Vector2 randomLocation = findLocationInRange(position, this.pathfinder.getLevel(), this.actor.maxMove);
            if (randomLocation != null) {
                this.moveTo((int)randomLocation.x, (int)randomLocation.y);
                return true;
            }
            return skipTurn();
        }

        // Move to the nearest player.
        this.attackTo(nearest.x, nearest.y);

        return true;
    }
}
