package cpsc599.states.Level1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import cpsc599.OrbGame;
import cpsc599.ai.*;
import cpsc599.assets.*;
import cpsc599.assets.Enemies.BruiserEnemy;
import cpsc599.assets.Enemies.NimbleThiefEnemy;
import cpsc599.assets.Entities.*;
import cpsc599.assets.Enemies.BasicEnemy;
import cpsc599.assets.Enemies.TankyEnemy;
import cpsc599.assets.Entities.HealthShrineGameEntity;
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

/**
 * Basic testing state.
 */
public class IntroLevelState extends LevelState {
    int currentEnemy = 0;
    float currentTime = 0f;

    private AnimatedSprite sprite;
    private boolean enemyStartTurn;
    private LevelManager levelManager;

    public int turnNum;
    public boolean isShown;

    public IntroLevelState(OrbGame game, LevelManager manager, PlayerController playerController,
                           CameraController cameraController, EnemyController enemyController) {
        super(game, playerController, cameraController, enemyController);
        this.levelManager = manager;
    }

    @Override
    public void init(OrbGame game) {
        super.init(game);
        super.setLevel(levelManager.setLevel("level0"));

        turnNum = 0;
        isShown = false;

        playerController.getPlayerManager().reset();
        enemyController.getEnemyManager().reset();

        Player p3 = new Player("Hikari", SharedAssets.hikariSprite, 2, 7, 8, 22, 4, 2, 100, 70);
        p3.getPlayerInventory().pickUp(new Item("Staff", true, Inventory.RHAND_SLOT, 2, 3, 3));
        p3.getPlayerInventory().equip(p3.getPlayerInventory().getCarry()[0]);
        p3.getPlayerInventory().pickUp(new Item("Leather Belt", true, Inventory.LEGS_SLOT));
        p3.updateStats();

        // Set up the pathfinder for this level.
        AStarPathfinder pathfinder = new AStarPathfinder(this.currentLevel, playerController.getPlayerManager(),
                enemyController.getEnemyManager(), this.gameEntityManager);

        sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Human/human2.png", 0,0,16,16,1,0.1f);
        Enemy e = new NimbleThiefEnemy(sprite, 5, 6);
        e.setAiActor(new HitAndRunAI(this.playerController.getPlayerManager(), pathfinder, e));

        sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Human/human5.png", 0,0,16,16,1,0.1f);
        Enemy e2 = new TankyEnemy(sprite, 10, 8);
        e2.setAiActor(new TankAI(this.playerController.getPlayerManager(), pathfinder, e2));

        sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Human/human4.png", 0,0,16,16,1,0.1f);
        Enemy e3 = new NimbleThiefEnemy(sprite, 13, 6);
        e3.setAiActor(new WanderingAI(this.playerController.getPlayerManager(), pathfinder, e3));

        sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Monsters/enemy9.png", 0,0,16,16,1,0.1f);
        Enemy e4 = new BasicEnemy(sprite, 13, 2);
        e4.setAiActor(new OpportunistAI(this.playerController.getPlayerManager(), pathfinder, e4));

        sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Monsters/enemy15.png", 0,0,16,16,1,0.1f);
        Enemy e5 = new BasicEnemy(sprite, 17, 4);
        e5.setAiActor(new BasicWarriorAI(this.playerController.getPlayerManager(), pathfinder, e5));

        sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Monsters/enemy13.png", 0,0,16,16,1,0.1f);
        Enemy e6 = new BasicEnemy(sprite, 19, 12);
        e6.setAiActor(new OpportunistAI(this.playerController.getPlayerManager(), pathfinder, e6));

        sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Monsters/enemy15.png", 0,0,16,16,1,0.1f);
        Enemy e7 = new BasicEnemy(sprite, 10, 18);
        e7.setAiActor(new OpportunistAI(this.playerController.getPlayerManager(), pathfinder, e7));

        sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Human/human2.png", 0,0,16,16,1,0.1f);
        Enemy e8 = new BasicEnemy(sprite, 15, 15);
        e8.setAiActor(new OpportunistAI(this.playerController.getPlayerManager(), pathfinder, e8));

        sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Human/human3.png", 0,0,16,16,1,0.1f);
        Enemy e9 = new BruiserEnemy(sprite, 19, 18);
        e9.setAiActor(new OpportunistAI(this.playerController.getPlayerManager(), pathfinder, e9));

        enemyController.getEnemyManager().addEnemy(e);
        enemyController.getEnemyManager().addEnemy(e2);
        enemyController.getEnemyManager().addEnemy(e3);
        enemyController.getEnemyManager().addEnemy(e4);
        enemyController.getEnemyManager().addEnemy(e5);
        enemyController.getEnemyManager().addEnemy(e6);
        enemyController.getEnemyManager().addEnemy(e7);
        enemyController.getEnemyManager().addEnemy(e8);
        enemyController.getEnemyManager().addEnemy(e9);

        //playerController.getPlayerManager().addPlayer(p);
        playerController.getPlayerManager().addPlayer(p3);

        // TODO: Make this not stupid.
        playerController.setupCursor();
        playerController.getCursor().x = 2;
        playerController.getCursor().y = 7;

        playerController.setupMenus();

        dialogue = new Dialogue();
        dialogue.loadDialogueXML(SharedAssets.CHAPTER_1);

        HealthShrineGameEntity shrine = new HealthShrineGameEntity(7, 8, 6);
        ArmourGameEntity armour = new ArmourGameEntity(3, 15, 2);
        SwordGameEntity sword = new SwordGameEntity(18, 0, 2);
        gameEntityManager.addEntity(armour);
        gameEntityManager.addEntity(sword);
        gameEntityManager.addEntity(shrine);

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
            orb.setState("LEVEL1_FINALE");
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
        if(turnNum == 2 && !isShown) {
            this.dialogue.addDialogue("Sean:\nHey what's going on here!?","Sean");
            this.dialogue.addDialogue("Sasha:\nWe're here to help! Hold on.", "Sasha");
            this.dialogue.setVisibility(true);
            Player seen = new Player("Sean", SharedAssets.seanSprite, 0, 6, 7, 16, 3, 2, 100, 70);
            seen.getPlayerInventory().pickUp(new Item("Sabre", true, Inventory.RHAND_SLOT, 1, 2, 3));
            seen.getPlayerInventory().equip(seen.getPlayerInventory().getCarry()[0]);
            seen.updateStats();
            playerController.getPlayerManager().addPlayer(seen);
            Player sash = new Player("Sean", SharedAssets.sashaSprite, 0, 8, 9, 12, 3, 1, 100, 70);
            sash.getPlayerInventory().pickUp(new Item("Pointy thruster", true, Inventory.RHAND_SLOT, 2, 2, 3));
            sash.getPlayerInventory().equip(sash.getPlayerInventory().getCarry()[0]);
            sash.updateStats();
            playerController.getPlayerManager().addPlayer(sash);
            isShown = true;
        }

        // TODO: Find a way to abstract this into the PlayerController.
        if (Controls.isKeyTapped(input, Controls.SELECT)) {
            Logger.debug("'SELECT' pressed.");
            orb.setState("LEVEL1_FINALE");
        }
    }
}
