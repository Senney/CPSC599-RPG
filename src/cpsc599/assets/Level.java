package cpsc599.assets;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;

import java.io.File;

public class Level {
	public File file;
    public String name;

    public TiledMap tiledMap;
    public MapLayer collisionLayer;
}
