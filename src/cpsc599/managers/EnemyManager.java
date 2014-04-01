package cpsc599.managers;

import com.badlogic.gdx.math.Vector2;
import cpsc599.assets.Enemy;
import cpsc599.util.Logger;

import java.util.ArrayList;

/**
 * Manages the computer controlled units
 */

public class EnemyManager {

    private ArrayList<Enemy> enemyList;

    public EnemyManager() {
        enemyList = new ArrayList<Enemy>();
    }

    public void addEnemy(Enemy e) {
        Logger.debug("Adding Enemy '" + e + "'.");
        enemyList.add(e);
    }

    public Enemy[] getEnemies() {
        return enemyList.toArray(new Enemy[enemyList.size()]);
    }

    public ArrayList<Enemy> getEnemiesInRange(int xpos, int ypos, int range) {
        ArrayList<Enemy> enemies = new ArrayList<Enemy>();

        for (Enemy e : enemyList) {
            Vector2 userPos = new Vector2(xpos, ypos);
            Vector2 enemyPos = new Vector2(e.x, e.y);

            float dst = userPos.dst(enemyPos);
            if (dst <= range) {
                enemies.add(e);
            }
        }

        return enemies;
    }

    public Enemy getEnemy(int index) {
        if (enemyList.size() < index) {
            Logger.error("index was out of range.");
            return null;
        }

        return enemyList.get(index);
    }

    public Enemy getEnemyAtPosition(int x, int y) {
        for (Enemy e : enemyList) {
            if (e.x == x && e.y == y) return e;
        }

        return null;
    }
}
