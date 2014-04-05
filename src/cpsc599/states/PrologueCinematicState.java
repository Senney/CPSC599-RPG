package cpsc599.states;

import com.badlogic.gdx.Input;
import cpsc599.OrbGame;
import cpsc599.assets.Level;
import cpsc599.controller.CameraController;

public class PrologueCinematicState extends CinematicState {
    public PrologueCinematicState(OrbGame game, Level level, CameraController cameraController) {
        super(game, level, cameraController);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void tick(Input input) {
        super.tick(input);
    }

    @Override
    protected void loadCinematicActions() {


        super.loadCinematicActions();
    }
}

