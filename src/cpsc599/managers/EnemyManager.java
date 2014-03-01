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
    private Enemy currentEnemy;

    public EnemyManager() {
        enemyList = new ArrayList<Enemy>();
    }

    public void addEnemy(Enemy e) {
        Logger.debug("EnemyManager::addEnemy - Adding Enemy '" + e + "'.");
        enemyList.add(e);
        //if (currentEnemy == null) setCurrent(0); // Commented to allow for cursor selection.
    }

    public Enemy[] getEnemies() {
        return enemyList.toArray(new Enemy[enemyList.size()]);
    }

    public Enemy getEnemy(int index) {
        if (enemyList.size() < index) {
            Logger.error("Enemy::setCurrent - index was out of range.");
            return null;
        }

        return enemyList.get(index);
    }

    public Enemy setCurrent(int index) {
        currentEnemy = this.getEnemy(index);
        Logger.debug("EnemyManager::setCurrent - Current enemy set to " + currentEnemy);
        return currentEnemy;
    }

    public Enemy getCurrent() {
        return currentEnemy;
    }

    public void setCurrent(Enemy e) {
        if (e != null && !this.enemyList.contains(e)) {
            Logger.error("EnemyManager::setCurrent - Attempted to set current enemy to enemy that did not exist.");
            return;
        }

        Logger.debug("EnemyManager::setCurrent - Current enemy set to " + e);
        this.currentEnemy = e;
    }

}
