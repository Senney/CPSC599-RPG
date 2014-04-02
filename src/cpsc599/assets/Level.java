package cpsc599.assets;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import cpsc599.util.Logger;

import java.io.File;

public class Level {
	public File file;
    public String name;

    public TiledMap tiledMap;
    public TiledMapTileLayer collisionLayer;
    public Vector2 player_spawn;

    public Vector2 tile_bounds;

    public Level() {
        this.player_spawn = new Vector2(0, 0);
    }

    public boolean collide(int x, int y) {
        if (collisionLayer == null) {
            Logger.error("Level has not yet been loaded, or collision layer was not assigned.");
            return false;
        }

        // Don't let the players leave the map.
        if (x < 0 || y < 0) return true;
        if (x >= tile_bounds.x || y >= tile_bounds.y) return true;

        boolean collides = collisionLayer.getCell(x, y) != null;
        return collides;
    }

    public Vector2 getMapDimensions() {
        if (tiledMap == null) {
            Logger.error("Level has not been set, unable to get dimensions.");
            return null;
        }

        if (tile_bounds == null) {
            MapProperties prop = tiledMap.getProperties();
            tile_bounds =  new Vector2((int)prop.get("width", Integer.class), (int)prop.get("height", Integer.class));
        }

        return tile_bounds;
    }
}
