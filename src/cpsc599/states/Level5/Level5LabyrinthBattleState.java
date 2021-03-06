package cpsc599.states.Level5;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import cpsc599.OrbGame;
import cpsc599.ai.*;
import cpsc599.assets.Dialogue;
import cpsc599.assets.Enemies.*;
import cpsc599.assets.Enemy;
import cpsc599.assets.Entities.*;
import cpsc599.assets.Player;
import cpsc599.controller.CameraController;
import cpsc599.controller.EnemyController;
import cpsc599.controller.PlayerController;
import cpsc599.managers.LevelManager;
import cpsc599.states.LevelState;
import cpsc599.util.Controls;
import cpsc599.util.Logger;
import cpsc599.util.SharedAssets;

public class Level5LabyrinthBattleState extends LevelState {
    int currentEnemy = 0;
    float currentTime = 0f;
    AStarPathfinder pathfinder;

    private boolean enemyStartTurn;
    private LevelManager levelManager;

    private boolean b_firstWave;
    private boolean b_secondWave;

    private Enemy etien;

    public int turnNum;
    public boolean isShown;

    public boolean seenCastle;
    private boolean b_etienDialogue;

    public Level5LabyrinthBattleState(OrbGame game, LevelManager manager, PlayerController playerController,
                                         CameraController cameraController, EnemyController enemyController) {
        super(game, playerController, cameraController, enemyController);
        this.levelManager = manager;
    }

    @Override
    public void init(OrbGame game) {
        super.init(game);
        super.setLevel(levelManager.setLevel("labyrinth"));

        playerController.healAll();
        playerController.resetTurn();
        gameEntityManager.getEntities().clear();

        cameraController.set(6, 23);

        turnNum = 0;
        isShown = false;
        seenCastle = false;

        //playerController.getPlayerManager().reset();
        enemyController.getEnemyManager().reset();
        this.initialPlayerList = playerController.getPlayerManager().getPlayers();

        // Reset the player positions
        Player[] currentPlayers = playerController.getPlayerManager().getPlayers();
        Logger.debug("Initializing level with " + currentPlayers.length + " players.");
        for (int i = 0; i < currentPlayers.length; i++) {
            currentPlayers[i].x = 14 - i;
            currentPlayers[i].y = 13;
        }

        // Set up the pathfinder for this level.
        pathfinder = new AStarPathfinder(this.currentLevel, playerController.getPlayerManager(),
                enemyController.getEnemyManager(), this.gameEntityManager);

        // TODO: Make this not stupid.
        playerController.setupCursor();
        playerController.getCursor().x = 2;
        playerController.getCursor().y = 7;

        playerController.setupMenus();

        this.etien = new EtienBoss(2, 3);
        this.enemyController.getEnemyManager().addEnemy(etien);

        dialogue = new Dialogue();
        this.dialogue.mapPortrait("Hikari", SharedAssets.hikariPortrait);
        this.dialogue.mapPortrait("Sean", SharedAssets.seanPortrait);
        this.dialogue.mapPortrait("Sasha", SharedAssets.sashaPortrait);
        this.dialogue.mapPortrait("Ren", SharedAssets.princePortrait);
        dialogue.mapPortrait("Jack", SharedAssets.jackPortrait);
        dialogue.addDialogue("What happened!?", "Hikari");
        dialogue.addDialogue("HahaHHahHah!! I have teleported you fools to my labyrinth. Prepare to die.", "Etien");
        dialogue.addDialogue("Prepare to fight, guys!", "Sean");
        dialogue.setVisibility(true);

        gameEntityManager.addEntity(new HealthShrineGameEntity(1, 12, 8));
        gameEntityManager.addEntity(new HealthShrineGameEntity(1, 6, 10));
        gameEntityManager.addEntity(new HealthShrineGameEntity(1, 1, 10));

        this.enemyStartTurn = true;

        this.b_firstWave = true;
        this.b_secondWave = true;
        this.b_etienDialogue = true;
    }

    @Override
    public void render() {
        super.renderer.setView((OrthographicCamera)this.cameraController.getCamera());
        super.drawLevel();

        super.groundLayer.begin();
        super.groundLayer.setProjectionMatrix(this.camera.combined);

        renderGroundLayer();
        renderAttackables();
        renderUsables();

        super.groundLayer.end();

        Player current = playerController.getPlayerManager().getCurrent();
        // These have to be rendered outside of the overlay batch.
        this.playerController.getActMenu().render(this.overlayLayer);
        this.playerController.getInventoryMenu().render(super.overlayLayer);
        this.playerController.getStatsMenu().render(this.overlayLayer);
        this.playerController.getGlobalMenu().render(this.overlayLayer);
        this.playerController.getDynamicDialogue().render(this.overlayLayer);
        this.dialogue.render(this.overlayLayer);
    }

    public void transition(float time) {

    }

    @Override
    public void tick(Input input) {
        currentTime += Gdx.graphics.getDeltaTime();

        if (this.dialogue.isVisible()) {
            if (Controls.isKeyTapped(input, Controls.A_BUTTON)) {
                if (this.dialogue.checkTextLeft() && this.dialogue.isVisible()) {
                    this.dialogue.loadTextRemains();
                }
                else {
                    if (!this.dialogue.stepDialogue())
                        this.dialogue.toggleVisibility();
                }
            }
            return;
        }

        if (this.etien.isDead() || this.etien.currentHealth == 1) {
            if (!this.getFlagBoolean("b_etienDeadSpeech")) {
                this.etien.currentHealth = 1;
                this.etien.setAiActor(null);
                enemyController.getEnemyManager().addEnemy(this.etien);

                this.dialogue.reset();
                this.dialogue.addDialogue("Impossible! How... how could this be! I'm a god!!", "Almighty");
                this.dialogue.addDialogue("AGHHHHHH!", "Almighty");
                this.dialogue.addDialogue("Quick! Let's go rescue Ren!", "Hikari");
                this.dialogue.setVisibility(true);

                this.setFlag("b_etienDeadSpeech", true);
                return;
            } else {
                this.orb.setState("LEVEL5_FINALE");
            }
        }

        //you will probably hate me for this...
        for(int i =0; i<playerController.getPlayerManager().getPlayers().length; i++)
        {
            Player p = playerController.getPlayerManager().getPlayer(i);
            if(p.isDead())
            {
                //add cool death scene here!
                this.dialogue.display(p.getName() + " was slain in combat!");
                playerController.getPlayerManager().removePlayer(p);
                return;
            }
        }

        if (b_firstWave) {
            Logger.debug("Spawning first wave of enemies.");
            spawnFirstWave();
            this.b_firstWave = false;
            return;
        } else if (b_secondWave && enemyController.getEnemyManager().count() == 1) {
            Logger.debug("Spawning second wave of enemies.");

            this.playerController.resetTurn();
            Player[] currentPlayers = playerController.getPlayerManager().getPlayers();
            for (int i = 0; i < currentPlayers.length; i++) {
                currentPlayers[i].x = 6 - i;
                currentPlayers[i].y = 13;
            }
            this.dialogue.reset();
            this.dialogue.addDialogue("What!? You're stronger than I thought.", "Etien");
            this.dialogue.addDialogue("Let's try making it a little harder...", "Etien");
            this.dialogue.setVisibility(true);
            spawnSecondWave();

            this.b_secondWave = false;
            return;
        } else if (!b_secondWave && enemyController.getEnemyManager().count() == 1 && this.b_etienDialogue) {
            Player[] currentPlayers = playerController.getPlayerManager().getPlayers();
            for (int i = 0; i < currentPlayers.length; i++) {
                currentPlayers[i].x = 13;
                currentPlayers[i].y = 1 + i;
            }

            this.dialogue.reset();
            this.dialogue.addDialogue("Alright, fools... Prepare for the real battle. PREPARE FOR THE MIGHT OF THE CUBE!", "Etien");
            this.dialogue.setVisibility(true);
            this.b_etienDialogue = false;

            this.playerController.endTurn();
            this.etien.setAiActor(new EtienAI(this.dialogue, this.enemyController.getEnemyManager(),
                    this.playerController.getPlayerManager(), pathfinder, this.etien));

            return;
        }


        if(playerController.getPlayerManager().getPlayers().length == 0)
        {
            //Game over!
            //add game over state
            dialogue.addDialogue("All your characters have died...", "Hikari");
            orb.setState("GAME_OVER");
            //Logger.debug("Game Over...");
        }

        if(enemyController.getEnemyManager().getEnemies().length == 0){
            orb.setState("LEVEL5_BLACKOUT");
            return;
        }

        this.gameEntityManager.tick(this.currentTime, this);

        Player current = playerController.getPlayerManager().getCurrent();
        if (playerController.isAttacking()) {
            // Handle the attack and then return out.
            handleAttack(input, current);
            return;
        } else if (playerController.isInspecting() || playerController.isUsing()) {
            // Handle item inspection and so-on.
            handleSelect(input, current);
            return;
        }

        if(playerController.getnext) {
            Player next = getNextPlayer();
            playerController.getCursor().x = next.x;
            playerController.getCursor().y = next.y;
            playerController.getnext = false;
            return;
        }

        if (!playerController.isTurnComplete()) {
            playerController.control(input, this.currentLevel);
        } else {
            if (enemyStartTurn) {
                this.dialogue.display("Opponent's turn");
                //counter increment here
                turnNum++;
                this.enemyStartTurn = false;
                return;
            }

            Enemy[] enemies = this.enemyController.getEnemyManager().getEnemies();
            if (currentEnemy > enemies.length - 1) {
                Logger.debug("Ending enemy turn.");
                this.dialogue.display("Enemy turn complete. Player turn begins.");
                playerController.resetTurn();
                this.currentEnemy = 0;
                enemyStartTurn = true;
            } else {
                Enemy e = enemies[currentEnemy];

                if (e.getAiActor() != null) {
                    if (e.getAiActor().inTurn()) {
                        if (e.getAiActor().step(currentTime, dialogue)) {
                            Logger.debug("Finishing turn for enemy[" + currentEnemy + "] - " + e);
                            currentEnemy++;
                        }

                        e.tick();
                        this.cameraController.set(e.x, e.y);
                        currentTime += Gdx.graphics.getDeltaTime();
                    } else {
                        Logger.debug("Deciding turn for actor: " + e);
                        e.resetMove();
                        e.getAiActor().decideTurn();
                    }
                } else {
                    currentEnemy++;
                }

                return;
            }
        }

        if (current != null) {
            current.tick();
            this.cameraController.set(current.x, current.y);
        } else {
            this.cameraController.set(this.playerController.getCursor().x, this.playerController.getCursor().y);
        }

        // TODO: Find a way to abstract this into the PlayerController.
        if (Controls.isKeyTapped(input, Input.Keys.O)) {
            orb.setState("LEVEL5_FINALE");
        }

        if (Controls.isKeyTapped(input, Input.Keys.P)) {
            this.enemyController.getEnemyManager().reset();
            this.enemyController.getEnemyManager().addEnemy(etien);
        }

        if (Controls.isKeyTapped(input, Input.Keys.R)) {
            this.restart();
        }
    }

    private void spawnSecondWave() {
        // 4 bruisers
        Enemy br1 = new CowCubeEnemy(1, 10),
                br2 = new CowCubeEnemy(2, 10),
                br3 = new CowCubeEnemy(5, 10),
                br4 = new CowCubeEnemy(6, 10);
        // 2 snipers
        Enemy sn1 = new SniperEnemy(SharedAssets.sniperSprite, 2, 8),
                sn2 = new BasicRangedEnemy(SharedAssets.rangerSprite, 5, 8);
        // 1 assassin
        Enemy as1 = new AssassinEnemy(SharedAssets.assassinSprite, 3, 9);
        // 1 tanky
        Enemy ta1 = new TankyEnemy(SharedAssets.tankySprite, 4, 9);

        br1.setAiActor(new OpportunistAI(playerController.getPlayerManager(), pathfinder, br1));
        br2.setAiActor(new OpportunistAI(playerController.getPlayerManager(), pathfinder, br2));
        br3.setAiActor(new OpportunistAI(playerController.getPlayerManager(), pathfinder, br3));
        br4.setAiActor(new OpportunistAI(playerController.getPlayerManager(), pathfinder, br4));

        sn1.setAiActor(new BasicWarriorAI(playerController.getPlayerManager(), pathfinder, sn1));
        sn2.setAiActor(new BasicWarriorAI(playerController.getPlayerManager(), pathfinder, sn2));

        as1.setAiActor(new HitAndRunAI(playerController.getPlayerManager(), pathfinder, as1));
        ta1.setAiActor(new TankAI(playerController.getPlayerManager(), pathfinder, ta1));

        this.enemyController.getEnemyManager().addEnemy(br1);
        this.enemyController.getEnemyManager().addEnemy(br2);
        this.enemyController.getEnemyManager().addEnemy(br3);
        this.enemyController.getEnemyManager().addEnemy(br4);
        this.enemyController.getEnemyManager().addEnemy(sn1);
        this.enemyController.getEnemyManager().addEnemy(sn2);
        this.enemyController.getEnemyManager().addEnemy(as1);
        this.enemyController.getEnemyManager().addEnemy(ta1);
    }

    private void spawnFirstWave() {
        Enemy cc1 = new CowCubeEnemy(9, 10),
                cc2 = new CowCubeEnemy(11, 8),
                cc3 = new CowCubeEnemy(12, 8),
                cc4 = new CowCubeEnemy(14, 10);
        Enemy bruiser1 = new BruiserEnemy(SharedAssets.bruiserSprite, 9, 8),
                bruiser2 = new BruiserEnemy(SharedAssets.bruiserSprite, 14, 8);
        cc1.setAiActor(new BasicWarriorAI(playerController.getPlayerManager(), pathfinder, cc1));
        cc2.setAiActor(new BasicWarriorAI(playerController.getPlayerManager(), pathfinder, cc2));
        cc3.setAiActor(new BasicWarriorAI(playerController.getPlayerManager(), pathfinder, cc3));
        cc4.setAiActor(new BasicWarriorAI(playerController.getPlayerManager(), pathfinder, cc4));
        bruiser1.setAiActor(new TankAI(playerController.getPlayerManager(), pathfinder, bruiser1));
        bruiser2.setAiActor(new TankAI(playerController.getPlayerManager(), pathfinder, bruiser2));

        if (playerController.getPlayerManager().getPlayers().length > 4) {
            Enemy sniper = new SniperEnemy(SharedAssets.sniperSprite, 10, 8);
            sniper.setAiActor(new HitAndRunAI(playerController.getPlayerManager(), pathfinder, sniper));
            enemyController.getEnemyManager().addEnemy(sniper);
        }

        enemyController.getEnemyManager().addEnemy(cc1);
        enemyController.getEnemyManager().addEnemy(cc2);
        enemyController.getEnemyManager().addEnemy(cc3);
        enemyController.getEnemyManager().addEnemy(cc4);
        enemyController.getEnemyManager().addEnemy(bruiser1);
        enemyController.getEnemyManager().addEnemy(bruiser2);
    }
}
