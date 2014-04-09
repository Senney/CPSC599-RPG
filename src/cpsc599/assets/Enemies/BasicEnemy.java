package cpsc599.assets.Enemies;

import cpsc599.assets.AnimatedSprite;
import cpsc599.assets.Enemy;

public class BasicEnemy extends Enemy {
    public BasicEnemy(AnimatedSprite sprite, int x, int y) {
        super(sprite, x, y, 4);

        super.damage = 2;
        super.maxHealth = 4;
        super.defence = 0;
        super.hit = 100;
        super.dodge = 10;
        super.range = 1;

        super.heal(-1);
    }
}
