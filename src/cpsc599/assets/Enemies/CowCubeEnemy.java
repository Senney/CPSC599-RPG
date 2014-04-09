package cpsc599.assets.Enemies;

import cpsc599.assets.AnimatedSprite;
import cpsc599.assets.Enemy;
import cpsc599.util.SharedAssets;

public class CowCubeEnemy extends Enemy {
    public CowCubeEnemy(int x, int y) {
        super(SharedAssets.cowCubeSprite, x, y, 5);

        this.maxHealth = this.currentHealth = 10;

        this.damage = 0;
        this.strength = 2;
        this.evade = 10;
        this.hit = 100;
        this.defence = 0;
    }
}
