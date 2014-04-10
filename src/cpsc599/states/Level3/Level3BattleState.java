package cpsc599.states.Level3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import cpsc599.OrbGame;
import cpsc599.ai.*;
import cpsc599.assets.*;
import cpsc599.assets.Enemies.*;
import cpsc599.assets.Entities.HealthShrineGameEntity;
import cpsc599.assets.Entities.HouseGameEntity;
import cpsc599.controller.CameraController;
import cpsc599.controller.EnemyController;
import cpsc599.controller.PlayerController;
import cpsc599.items.Inventory;
import cpsc599.items.Item;
import cpsc599.managers.LevelManager;
import cpsc599.states.LevelState;
import cpsc599.util.Controls;
import cpsc599.util.CoordinateTranslator;
import cpsc599.util.Logger;
import cpsc599.util.SharedAssets;

public class Level3BattleState extends LevelState {
    int currentEnemy = 0;
    float currentTime = 0f;

    private AnimatedSprite sprite;
    private boolean enemyStartTurn;
    private LevelManager levelManager;

    private Player jack;
    private boolean b_jackJoined;

    private Enemy ex1, ex2, ex3;

    public int turnNum;
    public boolean extra;
    public boolean isShown;
    private boolean b_serpentSpawned;
    private Enemy serpentBoss;

    public Level3BattleState(OrbGame game, LevelManager manager, PlayerController playerController,
                           CameraController cameraController, EnemyController enemyController) {
        super(game, playerController, cameraController, enemyController);
        this.levelManager = manager;
    }

    @Override
    public void init(OrbGame game) {
        super.init(game);
        super.setLevel(levelManager.setLevel("level2"));

        playerController.healAll();
        playerController.resetTurn();
        gameEntityManager.getEntities().clear();

        turnNum = 0;
        isShown = false;
        extra = false;

        //playerController.getPlayerManager().reset();
        enemyController.getEnemyManager().reset();

        // Reset the player positions
        this.initialPlayerList = playerController.getPlayerManager().getPlayers();
        Player[] currentPlayers = playerController.getPlayerManager().getPlayers();
        for (int i = 0; i < currentPlayers.length; i++) {
            currentPlayers[i].x = 1;
            currentPlayers[i].y = 6 + i;
        }

        jack = new Player("Jack", SharedAssets.jackSprite, 7, 14, 7, 10, 4, 1, 120, 60);
        jack.getPlayerInventory().pickUp(new Item("Pitchfork", true, Inventory.RHAND_SLOT, 1, 4, 1));
        jack.getPlayerInventory().equip(jack.getPlayerInventory().getCarry()[0]);
        jack.updateStats();

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

        HouseGameEntity houseEntity = new HouseGameEntity(new Sprite(SharedAssets.orangeHouse), 7, 14, "house1",
                "Looks like someone is home...", "A player wants to join your party!");
        gameEntityManager.addEntity(houseEntity);

        this.b_jackJoined = false;
        this.enemyStartTurn = true;
    }

    public void createEnemies(AStarPathfinder pathfinder) {
        Enemy e = new CowCubeEnemy(20, 10);
        e.setAiActor(new BasicWarriorAI(this.playerController.getPlayerManager(), pathfinder, e));

        Enemy e2 = new CowCubeEnemy(17, 8);
        e2.setAiActor(new BasicWarriorAI(this.playerController.getPlayerManager(), pathfinder, e2));

        Enemy e3 = new CowCubeEnemy(17, 13);
        e3.setAiActor(new BasicWarriorAI(this.playerController.getPlayerManager(), pathfinder, e3));

        Enemy e4 = new CowCubeEnemy(22, 14);
        e4.setAiActor(new BasicWarriorAI(this.playerController.getPlayerManager(), pathfinder, e4));

        sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Human/human6.png", 0,0,16,16,1,0.1f);
        Enemy e5 = new BasicRangedEnemy(sprite, 14, 4);
        e5.setAiActor(new HitAndRunAI(this.playerController.getPlayerManager(), pathfinder, e5));

        sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Human/human6.png", 0,0,16,16,1,0.1f);
        Enemy e6 = new BasicRangedEnemy(sprite, 15, 4);
        e6.setAiActor(new HitAndRunAI(this.playerController.getPlayerManager(), pathfinder, e6));

        sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Boss/dragon.png", 0,0,16,16,1,0.1f);
        Enemy e7 = new SniperEnemy(sprite, 23, 4);
        e7.setAiActor(new WanderingAI(this.playerController.getPlayerManager(), pathfinder, e7));

        sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Human/human1.png", 0,0,16,16,1,0.1f);
        Enemy e8 = new TankyEnemy(sprite, 20, 1);
        e8.setAiActor(new OpportunistAI(this.playerController.getPlayerManager(), pathfinder, e8));

        sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Human/human4.png", 0,0,16,16,1,0.1f);
        Enemy e9 = new NimbleThiefEnemy(sprite, 21, 4);
        e9.setAiActor(new WanderingAI(this.playerController.getPlayerManager(), pathfinder, e9));

        sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Human/human4.png", 0,0,16,16,1,0.1f);
        Enemy e10 = new NimbleThiefEnemy(sprite, 19, 4);
        e10.setAiActor(new WanderingAI(this.playerController.getPlayerManager(), pathfinder, e10));

        sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Boss/dragon.png", 0,0,16,16,1,0.1f);
        ex2 = new SniperEnemy(sprite, 9, 1);
        ex2.setAiActor(new BasicWarriorAI(this.playerController.getPlayerManager(), pathfinder, ex2));

        sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Boss/snow_bird.png", 0,0,16,16,1,0.1f);
        ex3 = new GlassCannonEnemy(sprite, 8, 1);
        ex2.setAiActor(new BasicWarriorAI(this.playerController.getPlayerManager(), pathfinder, ex3));

        AnimatedSprite serpentSprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Boss/serpent.png", 0, 0, 16, 16, 1, 0.1f);
        serpentBoss = new AssassinEnemy(serpentSprite, 18, 8);
        serpentBoss.maxHealth = 14;
        serpentBoss.heal(-1);
        serpentBoss.setAiActor(new AssassinAI(this.playerController.getPlayerManager(), pathfinder, serpentBoss));
        this.b_serpentSpawned = false;

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

        gameEntityManager.addEntity(new HealthShrineGameEntity(25, 10, 8));
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

        if (enemyController.getEnemyManager().getEnemies().length == 3 && !b_serpentSpawned) {
            this.enemyController.getEnemyManager().addEnemy(this.serpentBoss);
            this.dialogue.display("Who dares fight against the Cow Cube Cult!?");
            b_serpentSpawned = true;
            cameraController.set(this.serpentBoss.x, this.serpentBoss.y);
            return;
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
        if(playerController.restart) {
            super.restart();
            playerController.restart = false;
            return;
        }

        /**
         * Handle the joining of Jack.
         */
        if (!this.b_jackJoined && this.getFlagBoolean("house1")) {
            recruitJack();
            return;
        }

        if(turnNum == 5 && !extra && playerController.getPlayerManager().getPlayers().length >= 4) {
            enemyController.getEnemyManager().addEnemy(ex2);
            enemyController.getEnemyManager().addEnemy(ex3);
            extra = true;
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

        if (Controls.isKeyTapped(input, Input.Keys.O)) {
            if (!b_jackJoined) {
                this.recruitJack();
            }
            orb.setState("LEVEL3_FINALE");
        }

        if (Controls.isKeyTapped(input, Input.Keys.R)) {
            this.restart();
        }
    }

    private void recruitJack() {
        this.dialogue.addDialogue("Hey, I'm Jack! I'd like to help you defeat these vile creatures!", "Jack");
        this.dialogue.setVisibility(true);
        this.playerController.getPlayerManager().addPlayer(jack);
        b_jackJoined = true;
        return;
    }
}
