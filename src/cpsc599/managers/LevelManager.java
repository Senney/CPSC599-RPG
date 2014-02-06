package cpsc599.managers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.assets.AssetManager;

import cpsc599.assets.Level;
import cpsc599.loaders.LevelLoader;
import cpsc599.util.Logger;

public class LevelManager {
	private String levelDir;
	private LevelLoader loader;
	private AssetManager assetManager;
	private List<Level> levelList;
	
	public LevelManager(String levelsDir, AssetManager assetManager) throws IOException {
        Logger.debug("Creating LevelManager.");

		this.assetManager = assetManager;
		this.loader = new LevelLoader(assetManager);
		this.levelList = new ArrayList<Level>();
		
		setLevelDir(levelsDir);
	}
	
	public void setLevelDir(String levelDir) throws IOException { this.levelDir = levelDir; update(); }
	
	/**
	 * Updates the listing of all levels.
	 * @throws IOException
	 */
	public void update() throws IOException {
		File levels = new File(this.levelDir);
		if (!levels.isDirectory()) {
			Logger.error("LevelManager::update : Expected levelDir to be a directory.");
			throw new IOException("Expected levelDir to be a directory.");
		}
		
		File[] levelList = levels.listFiles();
		if (levelList.length == 0) Logger.warn("LevelManager::update : No levels found in " + this.levelDir);
		
		for (File level : levelList) {
			Level l = new Level();
			l.path = level.getAbsolutePath();
			this.levelList.add(l);
		}
		
		Logger.info("Updated level listing with " + this.levelList.size() + " elements.");
	}
}
