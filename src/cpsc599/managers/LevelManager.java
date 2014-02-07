package cpsc599.managers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.assets.AssetManager;

import cpsc599.assets.Level;
import cpsc599.loaders.LevelLoader;
import cpsc599.util.Logger;

/**
 * Detects and loads levels from the file system into the game.
 */
public class LevelManager {
	private String levelDir;
	private LevelLoader loader;
	private AssetManager assetManager;
	private List<Level> levelList;

    private Level current;

    /**
     * Sets up the Level Manager with default parameters.
     * @param levelsDir The directory in which levels are stored.
     * @param assetManager Global asset manager
     * @throws IOException levelsDir did not exist, or was not a directory.
     */
	public LevelManager(String levelsDir, AssetManager assetManager) throws IOException {
        Logger.debug("Creating LevelManager.");

		this.assetManager = assetManager;
		this.loader = new LevelLoader(assetManager);
		this.levelList = new ArrayList<Level>();

        this.current = null;
		
		setLevelDir(levelsDir);
	}

    /**
     * Sets the level directory and updates the directory listing.
     * @param levelDir The new level directory.
     * @throws IOException levelDir did not exist, or was not a directory.
     */
	public void setLevelDir(String levelDir) throws IOException { this.levelDir = levelDir; update(); }

	/**
	 * Updates the listing of all levels.
	 * @throws IOException
	 */
	public void update() throws IOException {
		File levels = new File(this.levelDir);
		if (!levels.isDirectory()) {
			Logger.error("LevelManager::update : Expected " + levels.getAbsoluteFile().toString() + " to be a directory.");
			throw new IOException("Expected levelDir to be a directory.");
		}
		
		File[] levelList = levels.listFiles();
		if (levelList.length == 0) Logger.warn("LevelManager::update : No levels found in " + this.levelDir);
		
		for (File level : levelList) {
			Level l = new Level();
			l.file = level;
            l.name = level.getName().replaceFirst("[.][^.]+$", "");
			this.levelList.add(l);
		}
		
		Logger.info("Updated level listing with " + this.levelList.size() + " level(s).");
	}

    /** Returns the current level. **/
    public Level getCurrentLevel() { return current; }

    /**
     * Sets the current level to the specified level name, and returns the level object.
     * @param levelName The level name (without the file extension).
     * @return The specified level. null if the level does not exist.
     */
    public Level setLevel(String levelName) {
        for (Level level : this.levelList) {
            if (level.name == levelName) {
                this.loader.load(level);
                this.current = level;
                return level;
            }
        }
        return null;
    }

    /**
     * Sets the current level to the specified index.
     * @param index The index in the list of levels.
     * @return The selected level. null if the index does not exist.
     */
    public Level setLevel(int index) {
        int size = levelList.size();
        if (index < 0 || index > size)
            return null;
        this.current = levelList.get(index);
        this.loader.load(this.current);
        return this.current;
    }
}
