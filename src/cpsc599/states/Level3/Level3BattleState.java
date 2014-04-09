package cpsc599.states.Level3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import cpsc599.OrbGame;
import cpsc599.ai.AStarPathfinder;
import cpsc599.ai.BasicWarrior;
import cpsc599.assets.*;
import cpsc599.assets.Enemies.CowCubeEnemy;
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

import java.util.ArrayList;
import java.util.List;

public class Level3BattleState extends LevelState {
    int currentEnemy = 0;
    float currentTime = 0f;

    private AnimatedSprite sprite;
    private Dialogue dialogue;
    private boolean enemyStartTurn;
    private LevelManager levelManager;

    private Player jack;
    private boolean b_jackJoined;

    public int turnNum;
    public boolean isShown;

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
        gameEntityManager.getEntities().clear();

        turnNum = 0;
        isShown = false;

        //playerController.getPlayerManager().reset();
        enemyController.getEnemyManager().reset();

        // Reset the player positions
        Player[] currentPlayers = playerController.getPlayerManager().getPlayers();
        for (int i = 0; i < currentPlayers.length; i++) {
            currentPlayers[i].x = 1;
            currentPlayers[i].y = 6 + i;
        }

        jack = new Player("Jack", SharedAssets.jackSprite, 7, 14, 7, 10, 4, 1, 120, 60);
        jack.getPlayerInventory().pickUp(new Item("Pitchfork", true, Inventory.RHAND_SLOT, 1, 4, 1));
        jack.getPlayerInventory().equip(jack.getPlayerInventory().getCarry()[0]);

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
        dialogue.loadDialogueXML(SharedAssets.CHAPTER_1);
        dialogue.mapPortrait("Jack", SharedAssets.jackPortrait);

        HouseGameEntity houseEntity = new HouseGameEntity(new Sprite(SharedAssets.orangeHouse), 7, 14, "house1",
                "Looks like someone is home...", "A player wants to join your party!");
        gameEntityManager.addEntity(houseEntity);

        this.b_jackJoined = false;
        this.enemyStartTurn = true;
    }

    public void createEnemies(AStarPathfinder pathfinder) {
        Enemy e = new CowCubeEnemy(20, 10);
        e.setAiActor(new BasicWarrior(this.playerController.getPlayerManager(), pathfinder, e));

        Enemy e2 = new CowCubeEnemy(17, 8);
        e2.setAiActor(new BasicWarrior(this.playerController.getPlayerManager(), pathfinder, e2));

        Enemy e3 = new CowCubeEnemy(17, 13);
        e3.setAiActor(new BasicWarrior(this.playerController.getPlayerManager(), pathfinder, e3));

        Enemy e4 = new CowCubeEnemy(22, 14);
        e4.setAiActor(new BasicWarrior(this.playerController.getPlayerManager(), pathfinder, e4));

        sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Monsters/enemy15.png", 0,0,16,16,1,0.1f);
        Enemy e5 = new Enemy(sprite, 14, 4, 8);
        e5.evade = 10;
        e5.damage = 6;
        e5.hit = 120;
        e5.setAiActor(new BasicWarrior(this.playerController.getPlayerManager(), pathfinder, e5));

        sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Monsters/enemy13.png", 0,0,16,16,1,0.1f);
        Enemy e6 = new Enemy(sprite, 14, 1, 8);
        e6.evade = 10;
        e6.damage = 6;
        e6.hit = 120;
        e6.setAiActor(new BasicWarrior(this.playerController.getPlayerManager(), pathfinder, e6));

        sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Monsters/enemy15.png", 0,0,16,16,1,0.1f);
        Enemy e7 = new Enemy(sprite, 17, 2, 8);
        e7.evade = 10;
        e7.damage = 6;
        e7.hit = 120;
        e7.setAiActor(new BasicWarrior(this.playerController.getPlayerManager(), pathfinder, e7));

        sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Human/human2.png", 0,0,16,16,1,0.1f);
        Enemy e8 = new Enemy(sprite, 20, 1, 8);
        e8.evade = 10;
        e8.damage = 6;
        e8.hit = 120;
        e8.setAiActor(new BasicWarrior(this.playerController.getPlayerManager(), pathfinder, e8));

        sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Human/human3.png", 0,0,16,16,1,0.1f);
        Enemy e9 = new Enemy(sprite, 22, 2, 8);
        e9.evade = 10;
        e9.damage = 6;
        e9.hit = 120;
        e9.setAiActor(new BasicWarrior(this.playerController.getPlayerManager(), pathfinder, e9));


        enemyController.getEnemyManager().addEnemy(e);
        enemyController.getEnemyManager().addEnemy(e2);
        enemyController.getEnemyManager().addEnemy(e3);
        enemyController.getEnemyManager().addEnemy(e4);
        enemyController.getEnemyManager().addEnemy(e5);
        enemyController.getEnemyManager().addEnemy(e6);
        enemyController.getEnemyManager().addEnemy(e7);
        enemyController.getEnemyManager().addEnemy(e8);
        enemyController.getEnemyManager().addEnemy(e9);
    }

    @Override
    public void render() {
        super.renderer.setView((OrthographicCamera)this.cameraController.getCamera());
        super.drawLevel();

        super.groundLayer.begin();
        super.groundLayer.setProjectionMatrix(this.camera.combined);

        renderAttackables();
        renderUsables();

        // Render players and enemies.
        this.gameEntityManager.render(super.groundLayer);
        if (playerController.getPlayerManager().getCurrent() != null) {
            Player current = playerController.getPlayerManager().getCurrent();
            groundLayer.draw(SharedAssets.highlight3, CoordinateTranslator.translate(current.x),
                    CoordinateTranslator.translate(current.y));
        }
        for (Player p : playerController.getPlayerManager().getPlayers())
            p.render(super.groundLayer);
        for(Enemy e : enemyController.getEnemyManager().getEnemies())
            e.render(super.groundLayer);


        // If we're in cursor-mode, render the cursor.
        if (this.playerController.isCursor()) {
            this.playerController.getCursor().render(this.groundLayer);
        }

        super.groundLayer.end();

        Player current = playerController.getPlayerManager().getCurrent();
        // These have to be rendered outside of the overlay batch.
        this.playerController.getActMenu().render(this.overlayLayer);
        this.playerController.getInventoryMenu().render(super.overlayLayer);
        this.playerController.getStatsMenu().render(this.overlayLayer);
        this.playerController.getGlobalMenu().render(this.overlayLayer);
        this.dialogue.render(this.overlayLayer);
    }

    private void renderUsables() {
        if ((this.playerController.isInspecting() || this.playerController.isUsing()) && this.entityList != null) {
            for (int i = 0; i < entityList.size(); i++) {
                GameEntity e = entityList.get(i);
                int x = (int)e.getPosition().x, y = (int)e.getPosition().y;
                if (i == playerController.getSelectedUnit()) {
                    groundLayer.draw(SharedAssets.highlight2, CoordinateTranslator.translate(x),
                            CoordinateTranslator.translate(y));
                } else {
                    groundLayer.draw(SharedAssets.highlight, CoordinateTranslator.translate(x),
                            CoordinateTranslator.translate(y));
                }
            }
        }
    }

    private void renderAttackables() {
        if (this.playerController.isAttacking() && attackingList != null && attackingList.size() != 0) {
            for (int i = 0; i < attackingList.size(); i++) {
                Enemy e = attackingList.get(i);
                if (i == playerController.getSelectedUnit()) {
                    groundLayer.draw(SharedAssets.highlight2, CoordinateTranslator.translate(e.x),
                            CoordinateTranslator.translate(e.y));
                } else {
                    groundLayer.draw(SharedAssets.highlight, CoordinateTranslator.translate(e.x),
                            CoordinateTranslator.translate(e.y));
                }
            }
        }
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

        /**
         * Handle the joining of Jack.
         */
        if (!this.b_jackJoined && this.getFlagBoolean("house1")) {
            this.dialogue.addDialogue("Hey, I'm Jack! I'd like to help you defeat these vile creatures!", "Jack");
            this.dialogue.setVisibility(true);
            this.playerController.getPlayerManager().addPlayer(jack);
            b_jackJoined = true;
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
        if (Controls.isKeyTapped(input, Controls.SELECT)) {
            Logger.debug("'SELECT' pressed.");
        }
    }
}
