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
import cpsc599.util.Controls;
import cpsc599.util.Logger;

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
    protected Level level;
    protected String nextState;

    public CinematicState(OrbGame game, Level level, CameraController cameraController, String nextState) {
        super(
                game,
                new PlayerController(new PlayerManager(), new EnemyManager()),
                cameraController,
                new EnemyController(new EnemyManager())
        );
        this.nextState = nextState;
        Logger.debug("Constructing base cinematic state.");

        this.level = level;
    }

    @Override
    public void init(OrbGame game) {
        Logger.debug("Initializing Cinematic State");

        super.init(game);
        this.runtime = 0f;

        this.setLevel(level);
        this.pathfinder = new AStarPathfinder(this.currentLevel, playerController.getPlayerManager(),
                enemyController.getEnemyManager(), this.gameEntityManager);

        actions = new LinkedList<CinematicAction>();

        dialogue = new Dialogue();

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

        this.dialogue.render(this.overlayLayer);
    }

    @Override
    public void tick(Input input) {
        this.runtime += Gdx.graphics.getDeltaTime();

        if (!b_initialized) {
            loadCinematicActions();
        }

        if (Controls.isKeyTapped(input, Controls.START)) {
            goToNextState();
        }

        if (this.dialogue.isVisible()) {
            if (Controls.isKeyTapped(input, Controls.A_BUTTON)) {
                if (this.dialogue.checkTextLeft() && this.dialogue.isVisible()) {
                    this.dialogue.loadTextRemains();
                }
                else {
                    this.dialogue.setVisibility(false);
                }
            }
            return;
        }


        // Tick all of our players.
        for (Player player : playerController.getPlayerManager().getPlayers()) {
            player.tick();
        }

        List<CinematicAction> actionList = getActions();
        for (CinematicAction action : actionList) {
            if (action != null && action.act(playerController.getPlayerManager(), this.pathfinder, this.runtime)) {
                Logger.debug("Removing completed action.");
                actions.remove(action);
            }
        }
    }

    protected List<CinematicAction> getActions() {
        List<CinematicAction> retActions = new ArrayList<CinematicAction>();
        for (CinematicAction c : this.actions) {
            if (!c.concurrent && retActions.isEmpty()) {
                retActions.add(c);
                break;
            } else {
                if (c.concurrent) retActions.add(c);
                else break;
            }
        }

        return retActions;
    }

    /**
     * An overrideable function that is used to load in the cinematic actions that are required to run the
     * cinematic. All actions are to be placed in here, and loaded into the LinkedList of cinematic actions.
     * (actions)
     */
    protected void loadCinematicActions() {
        Logger.debug("Initializing cinematic actions in super class.");
        b_initialized = true;
    }

    protected void goToNextState() {
        this.orb.setState(this.nextState);
    }
}
