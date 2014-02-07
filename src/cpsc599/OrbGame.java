package cpsc599;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.assets.AssetManager;

import cpsc599.managers.LevelManager;
import cpsc599.util.Logger;

import java.io.IOException;

class OrbGame implements ApplicationListener {
	private AssetManager assetManager;
    private LevelManager levelManager;

    private int width, height;
    private double scale;
	
	public OrbGame(int w, int h, double scale) {
		Logger.debug("OrbGame class constructed.");

        this.width = w;
        this.height = h;
        this.scale = scale;

		assetManager = new AssetManager();

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

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
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

        levelManager.setLevel(0);
	}
	
}
