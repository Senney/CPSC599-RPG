package cpsc599.states;

import com.badlogic.gdx.Input;
import cpsc599.OrbGame;
import cpsc599.assets.Level;
import cpsc599.controller.CameraController;

public class TemplateCinematicState extends CinematicState {
    public TemplateCinematicState(OrbGame game, Level level, CameraController cameraController, String nextState) {
        super(game, level, cameraController, nextState);
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
