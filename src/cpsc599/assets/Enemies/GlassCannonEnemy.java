package cpsc599.assets.Enemies;

import cpsc599.assets.AnimatedSprite;
import cpsc599.assets.Enemy;

public class GlassCannonEnemy extends Enemy{
    public GlassCannonEnemy(AnimatedSprite sprite, int x, int y) {
        super(sprite, x, y, 5);

        int baseDamage = 10;
        super.maxHealth = 5;
        super.strength = 10;
        super.defence = 1;
        super.hit = 60;
        super.dodge = 30;
        super.range = 1;

        super.damage = baseDamage + super.strength;

        super.heal(-1);
    }
}
