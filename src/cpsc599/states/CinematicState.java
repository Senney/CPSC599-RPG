package cpsc599.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import cpsc599.OrbGame;
import cpsc599.ai.AStarPathfinder;
import cpsc599.assets.*;
import cpsc599.controller.CameraController;
import cpsc599.controller.EnemyController;
import cpsc599.controller.PlayerController;
import cpsc599.managers.EnemyManager;
import cpsc599.managers.LevelManager;
import cpsc599.managers.PlayerManager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Base class for storing state information for Cinematics. Handles all rendering and logic tics.
 */
public class CinematicState extends LevelState {
    private float runtime;

    protected boolean b_initialized;
    protected Queue<CinematicAction> actions;
    protected AStarPathfinder pathfinder;
    protected Dialogue dialogue;

    public CinematicState(OrbGame game, Level level, CameraController cameraController) {
        super(
                game,
                new PlayerController(new PlayerManager(), new EnemyManager()),
                cameraController,
                new EnemyController(new EnemyManager())
        );

        this.runtime = 0f;

        this.setLevel(level);
        this.pathfinder = new AStarPathfinder(this.currentLevel, playerController.getPlayerManager(),
                enemyController.getEnemyManager());

        actions = new LinkedList<CinematicAction>();
        b_initialized = false;
    }

    @Override
    public void render() {
        super.renderer.setView(this.camera);
        super.drawLevel();

        super.groundLayer.begin();
        super.groundLayer.setProjectionMatrix(this.camera.combined);
        for (Player p : playerController.getPlayerManager().getPlayers())
            p.render(super.groundLayer);
        super.groundLayer.end();
    }

    @Override
    public void tick(Input input) {
        this.runtime += Gdx.graphics.getDeltaTime();

        if (!b_initialized) {
            loadCinematicActions();
        }

        // Tick all of our players.
        for (Player player : playerController.getPlayerManager().getPlayers()) {
            player.tick();
        }

        CinematicAction action = actions.peek();
        if (action != null && action.act(playerController.getPlayerManager(), this.pathfinder, this.runtime)) {
            actions.remove();
        }
    }

    /**
     * An overrideable function that is used to load in the cinematic actions that are required to run the
     * cinematic. All actions are to be placed in here, and loaded into the LinkedList of cinematic actions.
     * (actions)
     */
    protected void loadCinematicActions() {
        b_initialized = true;
    }
}
