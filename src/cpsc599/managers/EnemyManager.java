package cpsc599.managers;

import cpsc599.assets.Enemy;
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

    public Enemy getEnemy(int index) {
        if (enemyList.size() < index) {
            Logger.error("index was out of range.");
            return null;
        }

        return enemyList.get(index);
    }
}
