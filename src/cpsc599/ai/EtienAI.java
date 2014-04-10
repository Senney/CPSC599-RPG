package cpsc599.ai;

import com.badlogic.gdx.math.Vector2;
import cpsc599.assets.*;
import cpsc599.assets.Enemies.CowCubeEnemy;
import cpsc599.managers.EnemyManager;
import cpsc599.managers.PlayerManager;

import java.util.Random;

/**
 * Created by srheintz on 10/04/14.
 */
public class EtienAI extends AIActor {
    private Dialogue dialogue;
    private final EnemyManager enemyManager;

    private boolean p1_spawnCubesStart = false;
    private boolean b_phase1, b_phase2, b_phase3;
    private int turnCount;

    private Random rand;

    public EtienAI(Dialogue d, EnemyManager enemyManager, PlayerManager playerManager, AStarPathfinder pathfinder, Actor actor) {
        super(playerManager, pathfinder, actor);
        dialogue = d;
        this.enemyManager = enemyManager;

        this.b_phase1 = true;
        this.b_phase2 = false;
        this.b_phase3 = false;
        this.turnCount = 0;

        rand = new Random(System.currentTimeMillis());
    }

    @Override
    public boolean decideTurn() {
        turnCount++;
        if (b_phase1) {
            return decidePhase1();
        } else if (b_phase2) {
            return decidePhase2();
        } else if (b_phase3) {
            return decidePhase3();
        } else {
            return endBattle();
        }
    }

    private boolean endBattle() {
        return false;
    }

    private boolean decidePhase1() {
        if (!p1_spawnCubesStart) {
            this.dialogue.reset();
            this.dialogue.addDialogue("Go, my minions! Stop them!", "Etien");
            this.dialogue.setVisibility(true);
            int startx = 7;
            int starty = 2;
            for (int i = 0; i < 4; i++) {
                Enemy cube = new CowCubeEnemy(7, 2 + i);
                cube.damage = 10;
                cube.maxHealth = 16;
                cube.heal(-1);
                cube.setAiActor(new BasicWarriorAI(this.playerManager, this.pathfinder, cube));
                this.enemyManager.addEnemy(cube);
            }
            p1_spawnCubesStart = true;
            return skipTurn();
        }

        if (p1_spawnCubesStart && enemyManager.count() == 1) {
            this.dialogue.reset();
            this.dialogue.addDialogue("How dare you!", "Etien");
            this.dialogue.setVisibility(true);

            this.b_phase1 = false;
            this.b_phase2 = true;
            return decidePhase2();
        }

        return skipTurn();
    }

    private boolean decidePhase2() {
        if (this.actor.currentHealth <= 50) {
            this.dialogue.reset();
            this.dialogue.addDialogue("Agh! My powers are being weakend... Get over here!", "Etien");
            this.dialogue.setVisibility(true);

            this.b_phase2 = false;
            this.b_phase3 = true;
        }

        int chosen = rand.nextInt(playerManager.count());
        Player p = playerManager.getPlayer(chosen);
        int px = p.x, py = p.y;

        if (teleport(px, py)) {
            attack(p);
            Vector2 range = findLocationInRange(new Vector2(this.actor.x, this.actor.y), this.pathfinder.getLevel(), 8);
            if (range != null)
                moveTo((int)range.x, (int)range.y);
        } else {
            return skipTurn();
        }

        return true;
    }

    private boolean teleport(int px, int py) {
        if (canTeleport(px + 1, py)) {
            this.actor.x = px + 1;
            this.actor.y = py;
            return true;
        } else if (canTeleport(px - 1, py)) {
            this.actor.x = px - 1;
            this.actor.y = py;
            return true;
        } else if (canTeleport(px, py - 1)) {
            this.actor.x = px;
            this.actor.y = py - 1;
            return true;
        } else if (canTeleport(px, py + 1)) {
            this.actor.x = px;
            this.actor.y = py + 1;
            return true;
        }

        return false;
    }

    private boolean decidePhase3() {
        return skipTurn();
    }

    private boolean canTeleport(int x, int y) {
        Player p = playerManager.getPlayerAtPosition(x, y);
        Enemy e = enemyManager.getEnemyAtPosition(x, y);
        boolean collide = this.pathfinder.getLevel().collide(x, y);

        return (p == null && e == null && !collide);
    }

    private Vector2 findLocationInRange(Vector2 position, Level level, int range) {
        int iterations = 0;
        Random rand = new Random(System.currentTimeMillis());

        while (iterations < 10) {
            int xr = rand.nextInt(range) + 1, yr = rand.nextInt(range);
            int nx = (int)position.x + xr, ny = (int)position.y + yr;
            if (!level.collide(nx, ny)) {
                return new Vector2(nx, ny);
            }

            iterations++;
        }

        return null;
    }
}
