package cpsc599.assets.Enemies;

import cpsc599.assets.AnimatedSprite;
import cpsc599.assets.Enemy;

public class BruiserEnemy extends Enemy{
    public BruiserEnemy(AnimatedSprite sprite, int x, int y) {
        super(sprite, x, y, 5);

        int baseDamage = 5;
        super.maxHealth = 13;
        super.strength = 3;
        super.defence = 3;
        super.hit = 90;
        super.dodge = 30;
        super.range = 1;

        super.damage = baseDamage + super.strength;

        super.heal(-1);
    }
}
