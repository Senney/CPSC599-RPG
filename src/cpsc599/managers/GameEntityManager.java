package cpsc599.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import cpsc599.assets.GameEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to manage game entities.
 */
public class GameEntityManager {
    private final int width;
    private final int height;
    private List<GameEntity> entities;
    private GameEntity[][] entityMap;

    public GameEntityManager(int width, int height) {
        this.width = width;
        this.height = height;
        this.entities = new ArrayList<GameEntity>();
        this.entityMap = new GameEntity[height][width];
    }

    public GameEntity getEntity(int i) {
        return entities.get(i);
    }

    public List<GameEntity> getEntities() { return entities; }

    public GameEntity getEntityAtPosition(int x, int y) {
        if (x >= width || y >= height || x < 0 || y < 0) {
            return null;
        }

        return entityMap[y][x];
    }

    public void addEntity(GameEntity entity) {
        Vector2 position = entity.getPosition();
        if (position.x < 0 || position.x >= width || position.y < 0 || position.y >= height) {
            return;
        }

        entities.add(entity);
        entityMap[(int)position.y][(int)position.x] = entity;
    }

    public void tick(float gameTime) {
        for (GameEntity e : entities) {
            e.tick(gameTime);
        }
    }

    public void render(SpriteBatch batch) {
        for (GameEntity e : entities) {
            e.render(batch);
        }
    }
}
