package cpsc599.ai;

import com.badlogic.gdx.math.Vector2;
import cpsc599.assets.Actor;
import cpsc599.assets.Level;
import cpsc599.assets.Player;
import cpsc599.managers.PlayerManager;

import java.util.Random;

public class HitAndRunAI extends AIActor {
    public HitAndRunAI(PlayerManager playerManager, AStarPathfinder pathfinder, Actor actor) {
        super(playerManager, pathfinder, actor);
    }

    private Vector2 findLocationInRange(Vector2 position, Level level, int range) {
        int iterations = 0;
        Random rand = new Random(System.currentTimeMillis());

        while (iterations < 10) {
            int xr = rand.nextInt(range) + 1, yr = rand.nextInt(range);
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
        Vector2 myPos = new Vector2(this.actor.x, this.actor.y);
        Player closest = playerManager.getNearest(myPos);
        if (closest == null) return skipTurn();

        // If they're within striking range, we do a hit and run attack.
        Vector2 theirPos = new Vector2(closest.x, closest.y);
        if (myPos.dst(theirPos) < (this.actor.range + 1)) {
            attack(closest);
            Vector2 pos = findLocationInRange(myPos, pathfinder.getLevel(), this.actor.maxMove);
            if (pos != null) {
                moveTo((int)pos.x, (int)pos.y);
                return true;
            } else {
                return skipTurn();
            }
        } else {
            attackTo(closest.x, closest.y);
        }

        return true;
    }
}
