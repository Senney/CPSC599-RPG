package cpsc599.assets.Enemies;

import cpsc599.assets.AnimatedSprite;
import cpsc599.assets.Enemy;

/**
 * Created by srheintz on 10/04/14.
 */
public class EtienBoss extends Enemy {
    public EtienBoss(int x, int y) {
        super(new AnimatedSprite("assets/tilesets/primary/Enemy/Boss/etienne2.png", 0, 0, 16, 16, 1, 0.1f),
                x, y, 6);

        int baseDamage = 4;
        super.maxHealth = 110;
        super.strength = 4;
        super.defence = 0;
        super.hit = 160;
        super.dodge = 20;
        super.range = 1;

        super.damage = baseDamage + super.strength;

        super.heal(-1);
    }
}
