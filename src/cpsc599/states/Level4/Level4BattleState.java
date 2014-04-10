package cpsc599.states.Level4;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import cpsc599.OrbGame;
import cpsc599.ai.*;
import cpsc599.assets.*;
import cpsc599.assets.Enemies.*;
import cpsc599.assets.Entities.*;
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

public class Level4BattleState extends LevelState{
    int currentEnemy = 0;
    float currentTime = 0f;

    private Enemy serpentBoss;
    private boolean b_serpentSpawned;

    private AnimatedSprite sprite;
    private boolean enemyStartTurn;
    private LevelManager levelManager;

    public int turnNum;
    public boolean isShown;

    public boolean seenCastle;

    public Level4BattleState(OrbGame game, LevelManager manager, PlayerController playerController,
                             CameraController cameraController, EnemyController enemyController) {
        super(game, playerController, cameraController, enemyController);
        this.levelManager = manager;
    }

    @Override
    public void init(OrbGame game) {
        super.init(game);
        super.setLevel(levelManager.setLevel("level3"));

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
        this.initialPlayerList = playerController.getPlayerManager().getPlayers();
        Player[] currentPlayers = playerController.getPlayerManager().getPlayers();
        for (int i = 0; i < currentPlayers.length; i++) {
            currentPlayers[i].x = 5 + i;
            currentPlayers[i].y = 18;
        }

        // Set up the pathfinder for this level.
        AStarPathfinder pathfinder = new AStarPathfinder(this.currentLevel, playerController.getPlayerManager(),
                enemyController.getEnemyManager(), this.gameEntityManager);

        createEnemies(pathfinder);

        // TODO: Make this not stupid.
        playerController.setupCursor();
        playerController.getCursor().x = 5;
        playerController.getCursor().y = 18;

        playerController.setupMenus();

        dialogue = new Dialogue();
        dialogue.mapPortrait("Jack", SharedAssets.jackPortrait);


        this.enemyStartTurn = true;
    }

    public void createEnemies(AStarPathfinder pathfinder) {
        Enemy e = new CowCubeEnemy(8, 8);
        e.setAiActor(new BasicWarriorAI(this.playerController.getPlayerManager(), pathfinder, e));

        Enemy e2 = new CowCubeEnemy(11, 4);
        e2.setAiActor(new BasicWarriorAI(this.playerController.getPlayerManager(), pathfinder, e2));

        Enemy e3 = new CowCubeEnemy(5, 3);
        e3.setAiActor(new BasicWarriorAI(this.playerController.getPlayerManager(), pathfinder, e3));

        Enemy e4 = new CowCubeEnemy(1, 5);
        e4.setAiActor(new BasicWarriorAI(this.playerController.getPlayerManager(), pathfinder, e4));

        sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Monsters/snowman.png", 0,0,16,16,1,0.1f);
        Enemy e5 = new Enemy(sprite, 4, 14, 8);
        e5.evade = 10;
        e5.damage = 6;
        e5.hit = 120;
        e5.setAiActor(new BasicWarriorAI(this.playerController.getPlayerManager(), pathfinder, e5));

        sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Monsters/snowman.png", 0,0,16,16,1,0.1f);
        Enemy e6 = new Enemy(sprite, 10, 14, 8);
        e6.evade = 10;
        e6.damage = 6;
        e6.hit = 120;
        e6.setAiActor(new BasicWarriorAI(this.playerController.getPlayerManager(), pathfinder, e6));

        sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Monsters/snowman.png", 0,0,16,16,1,0.1f);
        Enemy e7 = new Enemy(sprite, 14, 11, 8);
        e7.evade = 10;
        e7.damage = 6;
        e7.hit = 120;
        e7.setAiActor(new BasicWarriorAI(this.playerController.getPlayerManager(), pathfinder, e7));

        sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Monsters/snowman.png", 0,0,16,16,1,0.1f);
        Enemy e8 = new Enemy(sprite, 14, 7, 8);
        e8.evade = 10;
        e8.damage = 6;
        e8.hit = 120;
        e8.setAiActor(new BasicWarriorAI(this.playerController.getPlayerManager(), pathfinder, e8));

        Enemy e9 = new GlassCannonEnemy(SharedAssets.glassCannonSprite, 0, 5);
        e9.setAiActor(new OpportunistAI(this.playerController.getPlayerManager(), pathfinder, e9));

        Enemy e10 = new AssassinEnemy(SharedAssets.assassinSprite, 2, 5);
        e10.setAiActor(new AssassinAI(this.playerController.getPlayerManager(), pathfinder, e10));

        Enemy e11 = new GlassCannonEnemy(SharedAssets.glassCannonSprite, 5, 5);
        e11.setAiActor(new OpportunistAI(this.playerController.getPlayerManager(), pathfinder, e11));

        Enemy e12 = new BruiserEnemy(SharedAssets.bruiserSprite, 8, 5);
        e12.setAiActor(new OpportunistAI(this.playerController.getPlayerManager(), pathfinder, e12));

        Enemy e13 = new BruiserEnemy(SharedAssets.bruiserSprite, 11, 5);
        e13.setAiActor(new OpportunistAI(this.playerController.getPlayerManager(), pathfinder, e13));

        Enemy e14 = new GlassCannonEnemy(SharedAssets.glassCannonSprite, 14, 12);
        e14.setAiActor(new OpportunistAI(this.playerController.getPlayerManager(), pathfinder, e14));

        Enemy e15 = new GlassCannonEnemy(SharedAssets.glassCannonSprite, 13, 18);
        e15.setAiActor(new OpportunistAI(this.playerController.getPlayerManager(), pathfinder, e15));

        Enemy e16 = new SniperEnemy(SharedAssets.sniperSprite, 14, 18);
        e16.setAiActor(new OpportunistAI(this.playerController.getPlayerManager(), pathfinder, e16));


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
        enemyController.getEnemyManager().addEnemy(e13);
        enemyController.getEnemyManager().addEnemy(e14);
        enemyController.getEnemyManager().addEnemy(e15);
        enemyController.getEnemyManager().addEnemy(e16);

        gameEntityManager.addEntity(new HealthShrineGameEntity(9, 10, 7));
        gameEntityManager.addEntity(new ArmourGameEntity(12, 16, 2));
        gameEntityManager.addEntity(new ShieldGameEntity(14, 15, 2));
        gameEntityManager.addEntity(new AmuletGameEntity(14, 13, 2));
        gameEntityManager.addEntity(new SwordGameEntity(0, 3, 2));
        gameEntityManager.addEntity(new ArmourGameEntity(6, 3, 2));
        gameEntityManager.addEntity(new HealthGameEntity(12, 4, 2));

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

        //checks to see if someone is in range of castle
        for(int i = 0; i < playerController.getPlayerManager().getPlayers().length; i++) {
            if(!seenCastle && playerController.getPlayerManager().getPlayer(i).y <= 10) {
                seenCastle = true;
                String name = playerController.getPlayerManager().getPlayer(i).getName();
                dialogue.addDialogue(name + ":\nHey I see a castle in the distance! I think we can navigate around this mountain pass to get there.", name);
                this.dialogue.setVisibility(true);
            }
            if(playerController.getPlayerManager().getPlayer(i).x == 17 && playerController.getPlayerManager().getPlayer(i).y == 3) {
                Logger.debug("Level complete!");
                orb.setState("LEVEL4_FINALE");
            }
            else
                break;
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
        if (Controls.isKeyTapped(input, Controls.SELECT)) {
            Logger.debug("'SELECT' pressed.");
            orb.setState("LEVEL4_FINALE");
        }
    }
}
