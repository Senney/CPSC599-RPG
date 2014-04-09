package cpsc599.assets.Enemies;

import cpsc599.assets.AnimatedSprite;
import cpsc599.assets.Enemy;

public class BasicRangedEnemy extends Enemy{
    public BasicRangedEnemy(AnimatedSprite sprite, int x, int y) {
        super(sprite, x, y, 4);

        int baseDamage = 2;
        super.maxHealth = 8;
        super.strength = 2;
        super.defence = 1;
        super.hit = 90;
        super.dodge = 50;
        super.range = 4;

        super.damage = baseDamage + super.strength;

        super.heal(-1);
    }
}
