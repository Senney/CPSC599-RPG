package cpsc599;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.assets.AssetManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import cpsc599.controller.CameraController;
import cpsc599.controller.EnemyController;
import cpsc599.controller.PlayerController;
import cpsc599.managers.EnemyManager;
import cpsc599.managers.LevelManager;
import cpsc599.managers.PlayerManager;
import cpsc599.managers.StateManager;
import cpsc599.states.CinematicState;
import cpsc599.states.IntroLevelState;
import cpsc599.states.MainMenuState;
import cpsc599.states.PrologueCinematicState;
import cpsc599.util.Logger;
import cpsc599.util.SharedAssets;

import java.io.IOException;

public class OrbGame implements ApplicationListener {
    public static float frameTime = 0f;

	private AssetManager assetManager;
    private LevelManager levelManager;
    private StateManager stateManager;
    private PlayerManager playerManager;
    private EnemyManager enemyManager;

    private CameraController cameraController;
    private PlayerController playerController;
    private EnemyController enemyController;

    public int width, height;
    private double scale;

    // 60 logic ticks per second.
    final private float accum_time = 1.0f / 60.0f;
    private float accumulator;
	
	public OrbGame(int w, int h, double scale) {
		Logger.debug("OrbGame class constructed.");

        this.accumulator = 0.0f;
        this.width = w;
        this.height = h;
        this.scale = scale;

	}

	@Override
	public void dispose() {
		Logger.debug("OrbGame class disposing.");
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

    public void setState(String stateName) {
        if (this.stateManager.setState(stateName) == null) {
            Logger.fatal("Unable to set state to " + stateName);
            System.exit(1);
        }
        this.stateManager.current.init(this);
    }

    public void tick() {
        frameTime += Gdx.graphics.getDeltaTime();

        this.stateManager.current.tick(Gdx.input);
    }

	@Override
	public void render() {
        // Clear the rendering surface.
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        accumulator += Gdx.graphics.getDeltaTime();
		while (accumulator >= accum_time) {
            this.tick();
            accumulator -= accum_time;
        }

        this.stateManager.current.render();
        this.cameraController.getCamera().apply(Gdx.graphics.getGL10());
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create() {
		Logger.debug("OrbGame class create method run.");
        Logger.debug("Window created with size: (" + this.width + ", " + this.height + "), " +
                "Scale: " + this.scale);

        assetManager = new AssetManager();
        stateManager = new StateManager();
        playerManager = new PlayerManager();
        enemyManager = new EnemyManager();

        enemyController = new EnemyController(enemyManager);
        playerController = new PlayerController(playerManager, enemyManager); //this might cause issues
        cameraController = new CameraController(new Vector2(0, 0), new Vector3(this.width, this.height, 1));

        levelManager = new LevelManager(assetManager);
        try {
            levelManager.addLevelDir("assets/tilesets/primary/Maps/Chapter1");
            levelManager.addLevelDir("assets/tilesets/primary/Maps/Chapter2");
            levelManager.addLevelDir("assets/tilesets/primary/Maps/Chapter3");
            levelManager.addLevelDir("assets/tilesets/primary/Maps/Chapter4");
            levelManager.addLevelDir("assets/tilesets/primary/Maps/Chapter5");
        } catch (IOException ex) {
            Logger.fatal("LevelManager creation failed... Exiting.");
            System.exit(1);
        }

        SharedAssets.load();

        // TODO: Fill this in with the proper state.
        stateManager.addState("MAIN_MENU", new MainMenuState());
        stateManager.addState("LEVEL0", new IntroLevelState(this, levelManager, playerController, cameraController, enemyController));
        stateManager.addState("PROLOGUE_CINEMATIC", new PrologueCinematicState(this, levelManager.setLevel("flower_field_map"), cameraController));

        setState("MAIN_MENU");
        //levelManager.setLevel(6);
	}
	
}
