package cpsc599.assets.Enemies;

import cpsc599.assets.AnimatedSprite;
import cpsc599.assets.Enemy;

public class BasicEnemy extends Enemy {
    public BasicEnemy(AnimatedSprite sprite, int x, int y) {
        super(sprite, x, y, 6);

        int baseDamage = 3;
        super.maxHealth = 7;
        super.strength = 2;
        super.defence = 0;
        super.hit = 100;
        super.dodge = 10;
        super.range = 1;

        super.damage = baseDamage + super.strength;

        super.heal(-1);
    }
}
