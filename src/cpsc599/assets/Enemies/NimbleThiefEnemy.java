package cpsc599.assets.Enemies;

import cpsc599.assets.AnimatedSprite;
import cpsc599.assets.Enemy;

public class NimbleThiefEnemy extends Enemy{
    public NimbleThiefEnemy(AnimatedSprite sprite, int x, int y) {
        super(sprite, x, y, 7);

        int baseDamage = 3;
        super.maxHealth = 7;
        super.strength = 1;
        super.defence = 1;
        super.hit = 130;
        super.dodge = 70;
        super.range = 1;

        super.damage = baseDamage + super.strength;

        super.heal(-1);
    }
}
