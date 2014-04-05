package cpsc599.assets;

import com.badlogic.gdx.math.Vector2;
import cpsc599.ai.AStarPathfinder;
import cpsc599.managers.CinematicActorManager;
import cpsc599.util.Logger;

public class CinematicAction {
    public static float DEFUALT_ACTION_TIME = 0.25f; // 1/4 second.

    public enum ActionType {
        MOVE, SPAWN, DESPAWN, STEP_DIALOGUE, WAIT
    };

    public ActionType actionType;
    public Actor actor;
    public Object obj;
    public float time;

    /**
     * Constructs a cinematic action with an available arbitrary object as a property.
     * @param type
     * @param a
     * @param o
     * @param time The time to take to do the action.
     */
    public CinematicAction(ActionType type, Actor a, Object o, float time) {
        this.actionType = type;
        this.actor = a;
        this.obj = o;
        this.time = time;
    }

    public static CinematicAction moveTo(Actor a, Vector2 destination) {
        return new CinematicAction(ActionType.MOVE, a, destination, DEFUALT_ACTION_TIME);
    }

    public static CinematicAction spawnActor(Actor a, Vector2 position) {
        return new CinematicAction(ActionType.SPAWN, a, position, 0.0f);
    }

    public static CinematicAction despawnActor(Actor a) {
        return new CinematicAction(ActionType.DESPAWN, a, null, 0.0f);
    }

    public static CinematicAction stepDialogue(Dialogue d) {
        return new CinematicAction(ActionType.STEP_DIALOGUE, null, d, 0.0f);
    }

    public static CinematicAction wait(float time) {
        return new CinematicAction(ActionType.WAIT, null, null, time);
    }

    public boolean act(CinematicActorManager manager, AStarPathfinder pathfinder, float deltaTime) {
        if (actionType == null) {
            Logger.error("actionType must be set before a CinematicAction can act.");
            return false;
        }

        switch (actionType) {
            case MOVE:

                break;
            case SPAWN:

                break;
            case DESPAWN:

                break;
            case STEP_DIALOGUE:

                break;
            case WAIT:

        }

        return false;
    }
}
