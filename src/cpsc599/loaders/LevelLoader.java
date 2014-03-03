package cpsc599.loaders;

import com.badlogic.gdx.assets.AssetManager;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import cpsc599.assets.Level;
import cpsc599.util.Logger;

import java.io.File;
import java.util.Iterator;

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
        level.collisionLayer.setOpacity(0.0f); // Make the collision layer invisible.

        // Load the spawn point of the user
        MapProperties properties = level.tiledMap.getProperties();
        if (!getPlayerSpawn(level, properties)) return false;

        // Loop over each cell and find cells of interest (dialogue, quest objectives, spawn points, etc.)
        MapObjects objects = level.tiledMap.getLayers().get(0).getObjects();
        Iterator<MapObject> iter = objects.iterator();
        while (iter.hasNext()) {
            MapObject obj = iter.next();
            MapProperties cellProperties = obj.getProperties();

            // Handle tile-specific properties.
        }

		return true;
	}

    private boolean getPlayerSpawn(Level level, MapProperties properties) {
        if (properties.containsKey("player_spawn")) {
            String val = (String)properties.get("player_spawn");
            try {
                level.player_spawn.x = Integer.parseInt(val.split(" ")[0]);
                level.player_spawn.y = Integer.parseInt(val.split(" ")[1]);
                Logger.debug("LevelLoader::loadMap - Set player spawn point to (" + level.player_spawn.x + ", " +
                    level.player_spawn.y + ").");
            } catch (NumberFormatException ex) {
                Logger.fatal("LevelLoader::loadMap - Player spawn point contained invalid values.");
                return false;
            }
        }
        return true;
    }

    private TiledMap loadMap(String mapName) {
        Logger.info("LevelLoader::loadMap - Loading Tiled map: " + mapName);
        File file = new File(mapName);
        if (!file.exists()) {
            return null;
        }

        TmxMapLoader.Parameters params = new TmxMapLoader.Parameters();
        params.yUp = false;
        TiledMap map = tmxLoader.load(mapName, params);

        return map;
    }
}
