package cpsc599.states.Prologue;

import com.badlogic.gdx.Input;
import cpsc599.OrbGame;
import cpsc599.assets.Level;
import cpsc599.controller.CameraController;
import cpsc599.states.CinematicState;

public class PrologueCinematicState extends CinematicState {
    public PrologueCinematicState(OrbGame game, Level level, CameraController cameraController) {
        super(game, level, cameraController);
    }

    @Override
    public void init(OrbGame game) {
        super.init(game);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void tick(Input input) {
        // Before AI actions.

        super.tick(input);

        // After AI actions.
    }

    @Override
    protected void loadCinematicActions() {
        // actions.add(CinematicAction.FUNCTION(PARAMS));

        super.loadCinematicActions();
    }
}
