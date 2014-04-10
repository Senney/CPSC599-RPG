package cpsc599.assets.Enemies;

import cpsc599.assets.AnimatedSprite;
import cpsc599.assets.Enemy;

public class AssassinEnemy extends Enemy{
    public AssassinEnemy(AnimatedSprite sprite, int x, int y) {
        super(sprite, x, y, 8);

        int baseDamage = 7;
        super.maxHealth = 8;
        super.strength = 4;
        super.defence = 1;
        super.hit = 140;
        super.dodge = 55;
        super.range = 1;

        super.damage = baseDamage + super.strength;

        super.heal(-1);
    }
}
