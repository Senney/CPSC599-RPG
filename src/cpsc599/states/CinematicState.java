package cpsc599.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import cpsc599.OrbGame;
import cpsc599.controller.CameraController;
import cpsc599.controller.EnemyController;
import cpsc599.controller.PlayerController;

/**
 * Base class for storing state information for Cinematics. Handles all rendering and logic tics.
 */
public class CinematicState extends LevelState {
    private float runtime;

    public CinematicState(OrbGame game, PlayerController playerController, CameraController cameraController, EnemyController enemyController) {
        super(game, playerController, cameraController, enemyController);
        this.runtime = 0f;
    }

    @Override
    public void render() {
        super.renderer.setView(this.camera);
        super.drawLevel();


    }

    @Override
    public void tick(Input input) {
        this.runtime += Gdx.graphics.getDeltaTime();


    }
}
