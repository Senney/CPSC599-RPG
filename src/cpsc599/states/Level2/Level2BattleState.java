package cpsc599.states.Level2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import cpsc599.OrbGame;
import cpsc599.ai.*;
import cpsc599.assets.AnimatedSprite;
import cpsc599.assets.Dialogue;
import cpsc599.assets.Enemies.*;
import cpsc599.assets.Enemy;
import cpsc599.assets.Entities.*;
import cpsc599.assets.Player;
import cpsc599.controller.CameraController;
import cpsc599.controller.EnemyController;
import cpsc599.controller.PlayerController;
import cpsc599.items.Inventory;
import cpsc599.items.Item;
import cpsc599.managers.LevelManager;
import cpsc599.states.LevelState;
import cpsc599.util.Controls;
import cpsc599.util.Logger;
import cpsc599.util.SharedAssets;

public class Level2BattleState extends LevelState{
    int currentEnemy = 0;
    float currentTime = 0f;

    private boolean hasKey;
    private boolean usedKey;
    private Player prisoner;
    private boolean prisoner_joined;

    private boolean extras;

    private AnimatedSprite sprite;
    private boolean enemyStartTurn;
    private LevelManager levelManager;

    public int turnNum;
    public boolean isShown;

    private Enemy ex1, ex2, ex3;

    public Level2BattleState(OrbGame game, LevelManager manager, PlayerController playerController,
                           CameraController cameraController, EnemyController enemyController) {
        super(game, playerController, cameraController, enemyController);
        this.levelManager = manager;
    }

    @Override
    public void init(OrbGame game) {
        super.init(game);
        super.setLevel(levelManager.setLevel("level1"));

        turnNum = 0;
        isShown = false;
        hasKey = false;
        usedKey = false;
        extras = false;
        //playerController.getPlayerManager().reset();

        // Reset the player positions
        Player[] currentPlayers = playerController.getPlayerManager().getPlayers();
        for (int i = 0; i < currentPlayers.length; i++) {
            currentPlayers[i].x = 4;
            currentPlayers[i].y = 6 + i;
        }
        enemyController.getEnemyManager().reset();

        /*Player p3 = new Player("Hikari", SharedAssets.hikariSprite, 2, 7, 8, 22, 4, 2, 100, 70);
        p3.getPlayerInventory().pickUp(new Item("Staff", true, Inventory.RHAND_SLOT, 2, 3, 3));
        p3.getPlayerInventory().equip(p3.getPlayerInventory().getCarry()[0]);
        p3.getPlayerInventory().pickUp(new Item("Leather Belt", true, Inventory.LEGS_SLOT));
        p3.updateStats();*/

        // Set up the pathfinder for this level.
        AStarPathfinder pathfinder = new AStarPathfinder(this.currentLevel, playerController.getPlayerManager(),
                enemyController.getEnemyManager(), this.gameEntityManager);

        sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Boss/dragon.png", 0,0,16,16,1,0.1f);
        Enemy e = new SniperEnemy(sprite, 18, 5);
        e.setAiActor(new OpportunistAI(this.playerController.getPlayerManager(), pathfinder, e));

        Enemy e2 = new CowCubeEnemy(13, 8);
        e2.setAiActor(new BasicWarriorAI(this.playerController.getPlayerManager(), pathfinder, e2));

        sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Human/human4.png", 0,0,16,16,1,0.1f);
        Enemy e3 = new NimbleThiefEnemy(sprite, 18, 10);
        e3.setAiActor(new WanderingAI(this.playerController.getPlayerManager(), pathfinder, e3));

        sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Boss/golem.png", 0,0,16,16,1,0.1f);
        Enemy e4 = new BruiserEnemy(sprite, 9, 14);
        e4.setAiActor(new OpportunistAI(this.playerController.getPlayerManager(), pathfinder, e4));

        sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Human/human1.png", 0,0,16,16,1,0.1f);
        Enemy e5 = new TankyEnemy(sprite, 11, 14);
        e5.setAiActor(new OpportunistAI(this.playerController.getPlayerManager(), pathfinder, e5));

        sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Human/human6.png", 0,0,16,16,1,0.1f);
        Enemy e6 = new BasicRangedEnemy(sprite, 14, 16);
        e6.setAiActor(new HitAndRunAI(this.playerController.getPlayerManager(), pathfinder, e6));

        sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Human/human6.png", 0,0,16,16,1,0.1f);
        Enemy e7 = new BasicRangedEnemy(sprite, 17, 18);
        e7.setAiActor(new HitAndRunAI(this.playerController.getPlayerManager(), pathfinder, e7));

        sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Human/human2.png", 0,0,16,16,1,0.1f);
        Enemy e8 = new NimbleThiefEnemy(sprite, 14, 21);
        e8.setAiActor(new OpportunistAI(this.playerController.getPlayerManager(), pathfinder, e8));

        sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Boss/golem.png", 0,0,16,16,1,0.1f);
        Enemy e9 = new BruiserEnemy(sprite, 8, 21);
        e9.setAiActor(new WanderingAI(this.playerController.getPlayerManager(), pathfinder, e9));

        sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Human/human6.png", 0,0,16,16,1,0.1f);
        Enemy e10 = new BasicRangedEnemy(sprite, 6, 16);
        e10.setAiActor(new HitAndRunAI(this.playerController.getPlayerManager(), pathfinder, e10));

        Enemy e11 = new CowCubeEnemy(12, 9);
        e11.setAiActor(new BasicWarriorAI(this.playerController.getPlayerManager(), pathfinder, e11));

        Enemy e12 = new CowCubeEnemy(13, 10);
        e12.setAiActor(new BasicWarriorAI(this.playerController.getPlayerManager(), pathfinder, e12));

        sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Human/human6.png", 0,0,16,16,1,0.1f);
        ex1 = new BasicRangedEnemy(sprite, 0, 5);
        ex1.setAiActor(new HitAndRunAI(this.playerController.getPlayerManager(), pathfinder, ex1));

        sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Boss/golem.png", 0,0,16,16,1,0.1f);
        ex2 = new BruiserEnemy(sprite, 1, 6);
        ex2.setAiActor(new WanderingAI(this.playerController.getPlayerManager(), pathfinder, ex2));

        sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Boss/snow_bird.png", 0,0,16,16,1,0.1f);
        ex3 = new GlassCannonEnemy(sprite, 0, 7);
        ex3.setAiActor(new OpportunistAI(this.playerController.getPlayerManager(), pathfinder, ex3));

        enemyController.getEnemyManager().addEnemy(e);
        enemyController.getEnemyManager().addEnemy(e2);
        enemyController.getEnemyManager().addEnemy(e3);
        enemyController.getEnemyManager().addEnemy(e4);
        enemyController.getEnemyManager().addEnemy(e5);
        enemyController.getEnemyManager().addEnemy(e6);
        enemyController.getEnemyManager().addEnemy(e7);
        enemyController.getEnemyManager().addEnemy(e8);
        enemyController.getEnemyManager().addEnemy(e9);
        enemyController.getEnemyManager().addEnemy(e10);
        enemyController.getEnemyManager().addEnemy(e11);
        enemyController.getEnemyManager().addEnemy(e12);

        //playerController.getPlayerManager().addPlayer(p);
        //playerController.getPlayerManager().addPlayer(p3);

        prisoner = new Player("Prisoner", SharedAssets.prisonerSprite, 10, 17, 7, 10, 4, 1, 120, 60);
        prisoner.getPlayerInventory().pickUp(new Item("Flesh crackling flesh strip", true, Inventory.RHAND_SLOT, 3, 4, 2));
        prisoner.getPlayerInventory().equip(prisoner.getPlayerInventory().getCarry()[0]);
        prisoner.updateStats();

        prisoner_joined = false;

        // TODO: Make this not stupid.
        playerController.setupCursor();
        playerController.getCursor().x = 2;
        playerController.getCursor().y = 7;

        playerController.setupMenus();

        dialogue = new Dialogue();
        //dialogue.loadDialogueXML(SharedAssets.CHAPTER_1);
        dialogue.mapPortrait("Prisoner", SharedAssets.prisonerPortrait);

        /*HealthShrineGameEntity shrine = new HealthShrineGameEntity(7, 8, 6);
        ArmourGameEntity armour = new ArmourGameEntity(3, 15, 2);
        SwordGameEntity sword = new SwordGameEntity(18, 0, 2);
        gameEntityManager.addEntity(armour);
        gameEntityManager.addEntity(sword);
        gameEntityManager.addEntity(shrine);*/

        HouseGameEntity houseEntity = new HouseGameEntity(new Sprite(SharedAssets.orangeHouse), 1, 18, "key",
                "Looks like someone is home...", "Here take this key to unlock one of the cells in the fortress!");
        DoorGameEntity door1 = new DoorGameEntity("key" ,10, 15, false);
        DoorGameEntity door2 = new DoorGameEntity("key" ,18, 15, false);
        UsableDoorGameEntity door3 = new UsableDoorGameEntity("open" ,7, 9, false);
        UsableDoorGameEntity door4 = new UsableDoorGameEntity("open" ,7, 10, false);
        UsableDoorGameEntity door5 = new UsableDoorGameEntity("open" ,14, 19, false);
        UsableDoorGameEntity door6 = new UsableDoorGameEntity("open" ,15, 19, false);
        UsableDoorGameEntity door7 = new UsableDoorGameEntity("open" ,16, 19, false);
        PrisonerEntity fakePrisoner = new PrisonerEntity(10,17);
        ShieldGameEntity shield = new ShieldGameEntity(18, 12, 2);
        AmuletGameEntity amulet = new AmuletGameEntity(18, 13, 2);
        SwordGameEntity sword = new SwordGameEntity(18, 14, 2);
        gameEntityManager.addEntity(new HealthShrineGameEntity(14, 12, 6));
        gameEntityManager.addEntity(fakePrisoner);
        gameEntityManager.addEntity(shield);
        gameEntityManager.addEntity(amulet);
        gameEntityManager.addEntity(new HealthGameEntity(18,8,2));
        gameEntityManager.addEntity(sword);
        gameEntityManager.addEntity(door1);
        gameEntityManager.addEntity(door2);
        gameEntityManager.addEntity(door3);
        gameEntityManager.addEntity(door4);
        gameEntityManager.addEntity(door5);
        gameEntityManager.addEntity(door6);
        gameEntityManager.addEntity(door7);
        gameEntityManager.addEntity(houseEntity);

        this.enemyStartTurn = true;
    }

    @Override
    public void render() {
        super.renderer.setView(camera);
        super.drawLevel();

        super.groundLayer.begin();
        super.groundLayer.setProjectionMatrix(this.camera.combined);

        renderGroundLayer();
        renderAttackables();
        renderUsables();

        super.groundLayer.end();

        // These have to be rendered outside of the overlay batch.
        this.playerController.getActMenu().render(this.overlayLayer);
        this.playerController.getInventoryMenu().render(super.overlayLayer);
        this.playerController.getStatsMenu().render(this.overlayLayer);
        this.playerController.getGlobalMenu().render(this.overlayLayer);
        this.playerController.getDynamicDialogue().render(this.overlayLayer);
        this.dialogue.render(this.overlayLayer);
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


        if(enemyController.getEnemyManager().getEnemies().length == 0){
            orb.setState("LEVEL2_FINALE");
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

        if(playerController.getPlayerManager().getPlayers().length == 0)
        {
            //Game over!
            //add game over state
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

        /**
         * Handle the getting of the key
         */
        if (this.getFlagBoolean("key") && !hasKey && !usedKey) {
            this.dialogue.addDialogue("Key obtained!", "Prisoner");
            this.dialogue.setVisibility(true);
            hasKey = true;
            return;
        }

        if(turnNum == 4 && !extras) {
            enemyController.getEnemyManager().addEnemy(ex1);
            enemyController.getEnemyManager().addEnemy(ex2);
            enemyController.getEnemyManager().addEnemy(ex3);
            extras = true;
        }

        if(this.getFlagBoolean("getPrisoner")) {
            Logger.debug("Prisoner has joined the party");
            playerController.getPlayerManager().addPlayer(prisoner);
            prisoner_joined = true;
            this.setFlag("getPrisoner", false);
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


            // TODO: This is where we put some awesome enemy turn logic!!
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
        if (Controls.isKeyTapped(input, Controls.SELECT)) {
            Logger.debug("'SELECT' pressed.");
            orb.setState("LEVEL2_FINALE");
        }
    }
}
