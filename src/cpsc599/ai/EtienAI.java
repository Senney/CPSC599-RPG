package cpsc599.ai;

import cpsc599.assets.Actor;
import cpsc599.assets.Dialogue;
import cpsc599.assets.Enemies.CowCubeEnemy;
import cpsc599.assets.Enemy;
import cpsc599.managers.EnemyManager;
import cpsc599.managers.PlayerManager;

/**
 * Created by srheintz on 10/04/14.
 */
public class EtienAI extends AIActor {
    private Dialogue dialogue;
    private final EnemyManager enemyManager;

    private boolean p1_spawnCubesStart = false;
    private boolean b_phase1, b_phase2, b_phase3;
    private int turnCount;

    public EtienAI(Dialogue d, EnemyManager enemyManager, PlayerManager playerManager, AStarPathfinder pathfinder, Actor actor) {
        super(playerManager, pathfinder, actor);
        dialogue = d;
        this.enemyManager = enemyManager;

        this.b_phase1 = true;
        this.b_phase2 = false;
        this.b_phase3 = false;
        this.turnCount = 0;
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
        if (this.actor.currentHealth <= 60) {
            this.b_phase1 = false;
            this.b_phase2 = true;
            return true;
        }

        if (!p1_spawnCubesStart) {
            this.dialogue.reset();
            this.dialogue.addDialogue("Go, my minions! Stop them!", "Etien");
            this.dialogue.setVisibility(true);
            int startx = 7;
            int starty = 2;
            for (int i = 0; i < 4; i++) {
                Enemy cube = new CowCubeEnemy(7, 2 + i);
                cube.setAiActor(new BasicWarriorAI(this.playerManager, this.pathfinder, cube));
                this.enemyManager.addEnemy(cube);
            }
            p1_spawnCubesStart = true;
            return skipTurn();
        }

        return skipTurn();
    }

    private boolean decidePhase2() {
        return false;
    }

    private boolean decidePhase3() {
        return false;
    }
}
