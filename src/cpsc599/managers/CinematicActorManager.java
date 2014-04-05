package cpsc599.managers;

import cpsc599.assets.Actor;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for managing actors in a cinematic environment.
 */
public class CinematicActorManager {
    List<Actor> actors;

    public CinematicActorManager() {
        actors = new ArrayList<Actor>();
    }

    public void addActor(Actor actor) {
        actors.add(actor);
    }

    public void removeActor(Actor actor) {
        actors.remove(actor);
    }

    public Actor[] getActors() { return actors.toArray(new Actor[actors.size()]); }

}
