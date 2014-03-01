package cpsc599.controller;

import cpsc599.managers.EnemyManager;

/**
 * Infrastructure for when we implement an AI
 */

public class EnemyController {
    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    private cpsc599.managers.EnemyManager enemyManager;

    public EnemyController(EnemyManager enemyManager)
    {
        this.enemyManager = enemyManager;
    }
}
