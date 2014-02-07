package cpsc599;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.assets.AssetManager;

import cpsc599.managers.LevelManager;
import cpsc599.managers.StateManager;
import cpsc599.util.Logger;

import java.io.IOException;

public class OrbGame implements ApplicationListener {
	private AssetManager assetManager;
    private LevelManager levelManager;
    private StateManager stateManager;

    private int width, height;
    private double scale;

    // 60 logic ticks per second.
    final private float accum_time = 1.0f / 60.0f;
    private float accum;
	
	public OrbGame(int w, int h, double scale) {
		Logger.debug("OrbGame class constructed.");

        this.accum = 0.0f;
        this.width = w;
        this.height = h;
        this.scale = scale;

		assetManager = new AssetManager();
        stateManager = new StateManager();

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

    public void tick() {

    }

	@Override
	public void render() {
        // Clear the rendering surface.
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        accum += Gdx.graphics.getDeltaTime();
		while (accum >= accum_time) {
            this.tick();
            accum -= accum_time;
        }
		
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


        //levelManager.setLevel(0);
	}
	
}
