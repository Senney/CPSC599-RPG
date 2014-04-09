package cpsc599.assets.Enemies;

import cpsc599.assets.AnimatedSprite;
import cpsc599.assets.Enemy;

public class SniperEnemy extends Enemy{
    public SniperEnemy(AnimatedSprite sprite, int x, int y) {
        super(sprite, x, y, 6);

        int baseDamage = 5;
        super.maxHealth = 5;
        super.strength = 2;
        super.defence = 0;
        super.hit = 150;
        super.dodge = 10;
        super.range = 3;

        super.damage = baseDamage + super.strength;

        super.heal(-1);
    }
}
