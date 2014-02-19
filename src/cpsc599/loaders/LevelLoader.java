package cpsc599.loaders;

import com.badlogic.gdx.assets.AssetManager;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import cpsc599.assets.Level;
import cpsc599.util.Logger;

import java.io.File;

public class LevelLoader {
	private AssetManager assetManager;
    private TmxMapLoader tmxLoader;

	public LevelLoader(AssetManager assetManager) {
        Logger.debug("LevelLoader - Creating LevelLoader.");
		this.assetManager = assetManager;
        this.tmxLoader = new TmxMapLoader();
	}
	
	public boolean load(Level level) {
        Logger.info("Loading level: " + level.name);

        String mapName = "assets/maps/" + level.name + ".tmx";
        level.tiledMap = loadMap(mapName);
        if (level.tiledMap == null) {
            Logger.fatal("LevelLoader::load - Unable to load requested Tiled Map: " + mapName);
            return false;
        }

        if (level.tiledMap.getLayers().get("Collision") == null) {
            Logger.fatal("LevelLoader::loadMap - The map *must* have a layer named 'Collision'.");
            return false;
        }
        level.collisionLayer = (TiledMapTileLayer)level.tiledMap.getLayers().get("Collision");

		return true;
	}

    private TiledMap loadMap(String mapName) {
        Logger.info("LevelLoader::loadMap - Loading Tiled map: " + mapName);
        File file = new File(mapName);
        if (!file.exists()) {
            return null;
        }

        TiledMap map = tmxLoader.load(mapName);

        return map;
    }
}
