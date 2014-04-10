package cpsc599.ai;


import com.badlogic.gdx.math.Vector2;
import cpsc599.assets.Actor;
import cpsc599.assets.Player;
import cpsc599.managers.PlayerManager;

public class AssassinAI extends AIActor {
    public AssassinAI(PlayerManager playerManager, AStarPathfinder pathfinder, Actor actor) {
        super(playerManager, pathfinder, actor);
    }

    @Override
    public boolean decideTurn() {
        Player[] players = playerManager.getPlayers();
        Player s = null;

        Vector2 myPos = new Vector2(this.actor.x, this.actor.y);
        for (Player p : players) {
            Vector2 theirPos = new Vector2(p.x, p.y);
            if (s == null || (p.currentHealth < s.currentHealth && myPos.dst(theirPos) < this.actor.maxMove * 2)) {
                s = p;
            } else if (p.currentHealth == s.currentHealth && myPos.dst(theirPos) < this.actor.maxMove * 2) {
                Vector2 oldPos = new Vector2(s.x, s.y);
                if (myPos.dst(theirPos) < myPos.dst(oldPos)) {
                    s = p;
                }
            }
        }

        if (s == null) {
            return skipTurn();
        }

        attackTo(s.x, s.y);
        return true;
    }
}
