package cpsc599.loaders;

import com.badlogic.gdx.assets.AssetManager;

import cpsc599.assets.Level;
import cpsc599.util.Logger;

public class LevelLoader {
	private AssetManager assetManager;

	public LevelLoader(AssetManager assetManager) {
        Logger.debug("Creating LevelLoader.");
		this.assetManager = assetManager;
	}
	
	public boolean load(Level level) {
		// TODO: Fill out stub.
        Logger.info("Loading level: " + level.name);
		
		return true;
	}
}
