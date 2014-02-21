package cpsc599;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.assets.AssetManager;

import cpsc599.controller.PlayerController;
import cpsc599.managers.LevelManager;
import cpsc599.managers.PlayerManager;
import cpsc599.managers.StateManager;
import cpsc599.states.IntroLevelState;
import cpsc599.states.MainMenuState;
import cpsc599.util.Logger;

import java.io.IOException;

public class OrbGame implements ApplicationListener {
    public static float frameTime = 0f;

	private AssetManager assetManager;
    private LevelManager levelManager;
    private StateManager stateManager;
    private PlayerManager playerManager;

    private PlayerController playerController;

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

		assetManager = new AssetManager();
        stateManager = new StateManager();
        playerManager = new PlayerManager();
        playerController = new PlayerController(playerManager);

        try {
            levelManager = new LevelManager("assets/levels", assetManager);
        } catch (IOException ex) {
            Logger.fatal("LevelManager creation failed... Exiting.");
            System.exit(1);
        }
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
            Logger.fatal("OrbGame::setState - Unable to set state to " + stateName);
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

        // TODO: Fill this in with the proper state.
        stateManager.addState("MAIN_MENU", new MainMenuState());
        stateManager.addState("LEVEL0", new IntroLevelState(this, levelManager, playerController));

        setState("LEVEL0");
        //levelManager.setLevel(0);
	}
	
}
