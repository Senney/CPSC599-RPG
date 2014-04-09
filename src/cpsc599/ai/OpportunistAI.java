package cpsc599.ai;

import cpsc599.assets.Actor;
import cpsc599.managers.PlayerManager;

public class OpportunistAI extends AIActor {
    public OpportunistAI(PlayerManager playerManager, AStarPathfinder pathfinder, Actor actor) {
        super(playerManager, pathfinder, actor);
    }

    @Override
    public boolean decideTurn() {
        return false;
    }
}
