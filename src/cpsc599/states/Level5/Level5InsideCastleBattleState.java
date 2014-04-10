package cpsc599.states.Level5;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
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

public class Level5InsideCastleBattleState extends LevelState {
    int currentEnemy = 0;
    float currentTime = 0f;

    private boolean enemyStartTurn;
    private LevelManager levelManager;

    public int turnNum;
    public boolean isShown;

    public boolean seenCastle;

    public Level5InsideCastleBattleState(OrbGame game, LevelManager manager, PlayerController playerController,
                                         CameraController cameraController, EnemyController enemyController) {
        super(game, playerController, cameraController, enemyController);
        this.levelManager = manager;
    }

    @Override
    public void init(OrbGame game) {
        super.init(game);
        super.setLevel(levelManager.setLevel("inside_crystal_castle"));

        playerController.healAll();
        playerController.resetTurn();
        gameEntityManager.getEntities().clear();

        cameraController.set(6, 23);

        turnNum = 0;
        isShown = false;
        seenCastle = false;

        //playerController.getPlayerManager().reset();
        enemyController.getEnemyManager().reset();

        // Reset the player positions
        Player[] currentPlayers = playerController.getPlayerManager().getPlayers();
        Logger.debug("Initializing level with " + currentPlayers.length + " players.");
        for (int i = 0; i < currentPlayers.length; i++) {
            currentPlayers[i].x = 7;
            currentPlayers[i].y = 13 - i;
        }

        // Set up the pathfinder for this level.
        AStarPathfinder pathfinder = new AStarPathfinder(this.currentLevel, playerController.getPlayerManager(),
                enemyController.getEnemyManager(), this.gameEntityManager);

        createEnemies(pathfinder);

        // TODO: Make this not stupid.
        playerController.setupCursor();
        playerController.getCursor().x = 2;
        playerController.getCursor().y = 7;

        playerController.setupMenus();

        dialogue = new Dialogue();
        dialogue.mapPortrait("Jack", SharedAssets.jackPortrait);

        this.gameEntityManager.addEntity(new DoorGameEntity("level5key",
                SharedAssets.loadTextureRegion(SharedAssets.PRIMARY_ASSET_FOLDER + "Town/walls.png", 16, 16, 1, 0, true),
                null, 6, 10, false));
        this.gameEntityManager.addEntity(new SwitchGameEntity(new Sprite(SharedAssets.doorSwitch), "level5key", 1, 1));
        this.gameEntityManager.addEntity(new HealthShrineGameEntity(1, 8, 10));
        this.gameEntityManager.addEntity(new SwordGameEntity(1, 11, 1));
        this.gameEntityManager.addEntity(new HealthGameEntity(1, 13, 4));

        this.enemyStartTurn = true;
    }

    public void createEnemies(AStarPathfinder pathfinder) {
        Enemy e = new GlassCannonEnemy(SharedAssets.glassCannonSprite, 14, 1);
        e.setAiActor(new HitAndRunAI(this.playerController.getPlayerManager(), pathfinder, e));

        Enemy e1 = new TankyEnemy(SharedAssets.tankySprite, 10, 5);
        e1.setAiActor(new TankAI(this.playerController.getPlayerManager(), pathfinder, e1));

        Enemy e2 = new BruiserEnemy(SharedAssets.bruiserSprite, 13, 5);
        e2.setAiActor(new OpportunistAI(this.playerController.getPlayerManager(), pathfinder, e2));

        Enemy e3 = new BruiserEnemy(SharedAssets.assassinSprite, 14, 13);
        e3.setAiActor(new OpportunistAI(this.playerController.getPlayerManager(), pathfinder, e3));

        Enemy e4 = new AssassinEnemy(SharedAssets.assassinSprite, 9, 13);
        e4.setAiActor(new AssassinAI(this.playerController.getPlayerManager(), pathfinder, e4));

        Enemy e5 = new NimbleThiefEnemy(SharedAssets.thiefSprite, 14, 9);
        e5.setAiActor(new WanderingAI(this.playerController.getPlayerManager(), pathfinder, e5));

        this.enemyController.getEnemyManager().addEnemy(e);
        this.enemyController.getEnemyManager().addEnemy(e1);
        this.enemyController.getEnemyManager().addEnemy(e2);
        this.enemyController.getEnemyManager().addEnemy(e3);
        this.enemyController.getEnemyManager().addEnemy(e4);
        this.enemyController.getEnemyManager().addEnemy(e5);
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

        if (!this.getFlagBoolean("b_dialogueShown") && enemyController.getEnemyManager().count() == 0) {
            this.dialogue.addDialogue("I think the throne room is ahead! Let's go!", "Hikari");
            this.dialogue.setVisibility(true);
            this.setFlag("b_dialogueShown", true);
        }


        if(playerController.getPlayerManager().getPlayers().length == 0)
        {
            //Game over!
            //add game over state
            transition(5);
            orb.setState("GAME_OVER");
            //Logger.debug("Game Over...");
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
            orb.setState("LEVEL5_BLACKOUT");
        }

        if (Controls.isKeyTapped(input, Input.Keys.R)) {
            this.restart();
        }


        if (Controls.isKeyTapped(input, Input.Keys.P)) {
            this.enemyController.getEnemyManager().reset();
        }
    }
}
