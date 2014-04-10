package cpsc599.assets;

import com.badlogic.gdx.math.Vector2;
import cpsc599.util.Logger;

import java.util.Random;

/*
Actor class for defining actors in the world
 */
public class Actor {

    public int maxHealth;
    public int currentHealth;
    public int strength;
    public int defence;
    public int speed;  //hit
    public int evade;

    public int range;
    public int damage;
    public int hit;
    public int dodge;

    public int maxMove;
    public int curMove;
    public boolean turnOver;

    public int x, y;
    public int moveX, moveY;
    public boolean moving;

    public void tick() {
        // TODO: Find a way to animate this.
        if (moving) {
            this.x = moveX;
            this.y = moveY;
            moving = false;
        }
    }

    public Vector2 move(int x, int y, Level level) {
        if (moving) {
            Logger.warn("Actor is already in motion. Some bug in the controller..");
        }

        if (curMove == 0 && maxMove != 0) {
            Logger.debug("Actor has 0 movement spaces available.");
            return new Vector2(this.x, this.y);
        }

        if (level.collide(this.x + x, this.y + y)) {
            Logger.debug("Actor has collided with solid geometry.");
            return new Vector2(this.x, this.y);
        }

        moveX = this.x + x;
        moveY = this.y + y;
        moving = true;
        curMove--;

        return new Vector2(this.x, this.y);
    }
    public int attack(Actor enemy)
    {
        Boolean isDead = false;
        int dmg = (damage - enemy.defence);
        if(dmg < 0) dmg = 0;
        int hitChance = (hit - enemy.dodge);

        Random rand = new Random();
        int chance = rand.nextInt(101);
        if(chance <= hitChance) {
            Logger.debug("Attacking Actor for " + dmg + " damage.");
            enemy.currentHealth -= dmg;
            if(enemy.currentHealth <= 0)
                isDead = true;
            return dmg;
        }
        Logger.debug("You missed");
        return -1;
    }

    public boolean isDead() { return this.currentHealth <= 0; }

    public void heal(int amount)
    {
        if (amount == -1) {
            currentHealth = maxHealth;
            return;
        }

        currentHealth += amount;
        if(currentHealth > maxHealth)
            currentHealth = maxHealth;
    }

    public void endTurn() {
        this.turnOver = true;
    }

    public void resetMove() {
        this.turnOver = false;
        this.curMove = maxMove;
    }

    public boolean canMove() {
        return this.curMove != 0;
    }
}
