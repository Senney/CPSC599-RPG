package cpsc599.assets;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import cpsc599.ai.AStarMove;
import cpsc599.ai.AStarPathfinder;
import cpsc599.controller.CameraController;
import cpsc599.managers.PlayerManager;
import cpsc599.states.CinematicState;
import cpsc599.util.Logger;

import java.util.List;

public class CinematicAction {
    public static float DEFUALT_ACTION_TIME = 0.25f; // 1/4 second.

    public enum ActionType {
        MOVE, SPAWN, DESPAWN, STEP_DIALOGUE, WAIT, PAN_CAMERA, FOCUS_CAMERA
    };

    public ActionType actionType;
    public Actor actor;
    public Object obj;
    public float time;
    public boolean concurrent;

    private float nextTime;

    /**
     * Constructs a cinematic action with an available arbitrary object as a property.
     * @param type
     * @param a
     * @param o
     * @param time The time to take to do the action.
     */
    public CinematicAction(ActionType type, Actor a, Object o, float time) {
        this(type, a, o, time, false);
    }

    public CinematicAction(ActionType type, Actor a, Object o, float time, boolean concurrent) {
        this.actionType = type;
        this.actor = a;
        this.obj = o;
        this.time = time;
        this.nextTime = 0f;
        this.concurrent = concurrent;
    }

    public static CinematicAction moveToConcurrent(Actor a, Vector2 destination) {
        return new CinematicAction(ActionType.MOVE, a, destination, DEFUALT_ACTION_TIME, true);
    }

    public static CinematicAction moveToConcurrent(Actor a, Vector2 destination, float time) {
        return new CinematicAction(ActionType.MOVE, a, destination, time, true);
    }


    public static CinematicAction moveTo(Actor a, Vector2 destination) {
        return new CinematicAction(ActionType.MOVE, a, destination, DEFUALT_ACTION_TIME);
    }

    public static CinematicAction moveTo(Actor a, Vector2 destination, float stepTime) {
        return new CinematicAction(ActionType.MOVE, a, destination, stepTime);
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

    public static CinematicAction focusCamera(Actor target, CameraController controller) {
        return new CinematicAction(ActionType.FOCUS_CAMERA, target, controller, 0.0f);
    }

    public static CinematicAction panCamera(Vector2 start, Vector2 target, CameraController controller) {
        Actor dummy = new Actor();
        dummy.x = (int)start.x;
        dummy.y = (int)start.y;
        dummy.moveX = (int)target.x;
        dummy.moveY = (int)target.y;
        return new CinematicAction(ActionType.PAN_CAMERA, dummy, controller, 0.10f);
    }

    public boolean act(PlayerManager manager, AStarPathfinder pathfinder, float deltaTime) {
        if (actionType == null) {
            Logger.error("actionType must be set before a CinematicAction can act.");
            return false;
        }

        if (this.nextTime > deltaTime) {
            return false;
        }

        switch (actionType) {
            case MOVE:
                if (this.obj instanceof Vector2) {
                    Vector2 dst = (Vector2)this.obj;
                    this.obj = pathfinder.getPath(new Vector2(this.actor.x, this.actor.y), dst);
                    if (this.obj == null) {
                        Logger.error("Unable to generate path for cinematic movement.");
                        return true;
                    }
                } else {
                    List<AStarMove> moves = (List<AStarMove>)this.obj;
                    if (moves.size() == 0) return true;

                    AStarMove top = moves.get(moves.size() - 1);

                    this.actor.move(top.x_move, top.y_move, pathfinder.getLevel());

                    moves.remove(top);
                    this.obj = moves;
                }

                break;
            case SPAWN:
                Vector2 pos = (Vector2)obj;
                this.actor.x = (int)pos.x;
                this.actor.y = (int)pos.y;
                manager.addPlayer((Player)this.actor);
                return true;
            case DESPAWN:
                manager.removePlayer((Player)this.actor);
                return true;
            case STEP_DIALOGUE:
                Dialogue d = (Dialogue)this.obj;
                d.stepDialogue();
                d.setVisibility(true);

                return true;
            case WAIT:
                if (nextTime == 0f) nextTime = deltaTime + time;
                if (deltaTime > nextTime) return true;
                return false; // We return false here to maintain the nextTime counter which is automatically
                              // set at the final return point of the function.
            case FOCUS_CAMERA:
                ((CameraController)obj).set(actor.x, actor.y);
                return true;
            case PAN_CAMERA:
                if (this.actor.x == this.actor.moveX && this.actor.y == this.actor.moveY) {
                    return true;
                } else {
                    // Loop until the camera actually moves.
                    while (!((CameraController)obj).set(this.actor.x, this.actor.y)) {
                        if (this.actor.x == this.actor.moveX && this.actor.y == this.actor.moveY) break;
                        this.actor.x += (this.actor.x < this.actor.moveX) ? 1 : -1;
                        this.actor.y += (this.actor.y < this.actor.moveY) ? 1 : -1;
                    }

                    Logger.debug("Moving camera to: " + this.actor.x + ", " + this.actor.y + " :: " + this.nextTime);
                }
        }
        this.nextTime = deltaTime + this.time;

        return false;
    }
}
