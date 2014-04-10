package cpsc599.ai;

import com.badlogic.gdx.math.Vector2;
import cpsc599.assets.*;
import cpsc599.assets.Enemies.BasicRangedEnemy;
import cpsc599.assets.Enemies.CowCubeEnemy;
import cpsc599.assets.Enemies.SniperEnemy;
import cpsc599.managers.EnemyManager;
import cpsc599.managers.PlayerManager;
import cpsc599.util.SharedAssets;

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

    private int chaseStart = 0, chaseTurns = 3;
    private Player chasePlayer;

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
            this.dialogue.addDialogue("Go, my minions! Stop them!", "Almighty");
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
            this.dialogue.addDialogue("How dare you destroy my beautiful cubes!", "Almighty");
            this.dialogue.setVisibility(true);

            this.b_phase1 = false;
            this.b_phase2 = true;
            return decidePhase2();
        }

        return skipTurn();
    }

    private boolean decidePhase2() {
        if (this.actor.currentHealth <= 40) {
            this.dialogue.reset();
            this.dialogue.addDialogue("You think you're so strong, just because you have the orb? Foolish girl! I am more powerful than any single item, or any single person!!", "Almighty");
            this.dialogue.addDialogue("I made a promise to Ren that I would always protect him. No matter how strong you are, the strength of that promise will always be stronger", "Hikari");
            this.dialogue.addDialogue("DIE!!!!", "Almighty");
            this.dialogue.addDialogue("The avatar seems to pulsate with power. He looks like he's going to chase someone! Get away!", "System");
            this.dialogue.setVisibility(true);

            this.b_phase2 = false;
            this.b_phase3 = true;

            return skipTurn();
        }

        int chosen = rand.nextInt(playerManager.count());
        Player p = playerManager.getPlayer(chosen);
        int px = p.x, py = p.y;

        if (rand.nextFloat() <= 0.40) {
            Vector2 spawn = randomLocation();
            Enemy ranged = new SniperEnemy(SharedAssets.sniperSprite, (int)spawn.x, (int)spawn.y);
            ranged.setAiActor(new HitAndRunAI(playerManager, pathfinder, ranged));
            this.enemyManager.addEnemy(ranged);
        }

        if (teleport(px, py)) {
            attack(p);
        } else {
            return skipTurn();
        }

        return true;
    }

    private boolean teleport(int px, int py) {
        if (canTeleport(px - 1, py)) {
            this.actor.x = px - 1;
            this.actor.y = py;
            return true;
        } else if (canTeleport(px + 1, py)) {
            this.actor.x = px + 1;
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
        if (this.actor.currentHealth <= 10) {
            return skipTurn();
        }

        this.actor.maxMove = 6;
        this.actor.damage = 12;
        this.actor.defence = 4;

        // Find a random player to chase.
        Player p = null;
        if (turnCount > chaseStart + chaseTurns || chasePlayer == null) {
            int chosen = rand.nextInt(playerManager.count());
            p = playerManager.getPlayer(chosen);
            chasePlayer = p;
            this.dialogue.reset();
            this.dialogue.addDialogue("I'm coming for you, " + p.getName() + "!", "Almighty");
            this.dialogue.setVisibility(true);

            this.chaseStart = turnCount;

            this.actor.maxMove = 3;
        } else {
            p = chasePlayer;
        }
        int px = p.x, py = p.y;

        attackTo(px, py);
        return true;
    }

    private boolean canTeleport(int x, int y) {
        Player p = playerManager.getPlayerAtPosition(x, y);
        Enemy e = enemyManager.getEnemyAtPosition(x, y);
        boolean collide = this.pathfinder.getLevel().collide(x, y);

        return (p == null && e == null && !collide);
    }

    private Vector2 randomLocation() {
        Vector2 pos = new Vector2(0, 0);
        pos.x = rand.nextInt(14) + 1;
        pos.y = rand.nextInt(6) + 1;
        return pos;
    }

    private Vector2 findLocationInRange(Vector2 position, Level level, int range) {
        int iterations = 0;

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
