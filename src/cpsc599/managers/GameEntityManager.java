package cpsc599.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import cpsc599.assets.GameEntity;
import cpsc599.states.State;

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

    /**
     * Constructs the GameEntityManager. Takes in width and height of the map for quick lookup for interactions.
     * @param width Map height (in tiles)
     * @param height Map width (in tiles)
     */
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
    public List<GameEntity> getEntitiesByIdentifier(String id) {
        List<GameEntity> idEntities = new ArrayList<GameEntity>();
        for (GameEntity e : entities) {
            if (e.getIdentifier().equals(entities)) idEntities.add(e);
        }
        return idEntities;
    }
    public List<GameEntity> getEntitiesInRange(int x, int y) {
        List<GameEntity> entities = new ArrayList<GameEntity>();
        for (GameEntity e : entities) {
            Vector2 player = new Vector2(x, y);
            // Check if entities are around 1 square away.
            if (player.dst(e.getPosition()) <= 1.5f) {
                entities.add(e);
            }
        }

        return entities;
    }

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

    public void tick(float gameTime, State gameState) {
        for (GameEntity e : entities) {
            e.tick(gameTime, gameState);
        }
    }

    public void render(SpriteBatch batch) {
        for (GameEntity e : entities) {
            e.render(batch);
        }
    }
}
