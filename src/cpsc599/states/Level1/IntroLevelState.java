package cpsc599.states.Level1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import cpsc599.OrbGame;
import cpsc599.ai.AStarPathfinder;
import cpsc599.ai.BasicWarrior;
import cpsc599.assets.*;
import cpsc599.assets.Entities.DoorGameEntity;
import cpsc599.assets.Entities.SwitchGameEntity;
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

/**
 * Basic testing state.
 */
public class IntroLevelState extends LevelState {
    int currentEnemy = 0;
    float currentTime = 0f;

    private AnimatedSprite sprite;
    private Dialogue dialogue;
    private ArrayList<Enemy> attackingList;
    private List<GameEntity> entityList;
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

        sprite = new AnimatedSprite("assets/tilesets/primary/CharacterDesign/characters/male/friend/friend_right.png", 0, 0, 16, 16, 1, 0.1f);

        /*Player p = new Player("Ren", sprite, 2, 5, 8, 14, 1, 3, 70, 80);

        Item sw = new Item("Pike", true, Inventory.RHAND_SLOT, 3, 5, 10);
        p.getPlayerInventory().pickUp(sw);
        p.getPlayerInventory().pickUp(new Item("Shield", true, Inventory.LHAND_SLOT, 1, 1, 5));
        p.getPlayerInventory().equip(sw);
        p.updateStats();*/

        sprite = new AnimatedSprite("assets/tilesets/primary/CharacterDesign/characters/female/main character/main_female_right.png", 0, 0, 16, 16, 1, 0.1f);

        Player p3 = new Player("Hikari", sprite, 2, 7, 8, 10, 1, 2, 60, 70);
        p3.getPlayerInventory().pickUp(new Item("Staff", true, Inventory.RHAND_SLOT, 2, 2, 3));
        p3.getPlayerInventory().equip(p3.getPlayerInventory().getCarry()[0]);
        p3.getPlayerInventory().pickUp(new Item("Leather Belt", true, Inventory.LEGS_SLOT));
        p3.updateStats();

        // Set up the pathfinder for this level.
        AStarPathfinder pathfinder = new AStarPathfinder(this.currentLevel, playerController.getPlayerManager(),
                enemyController.getEnemyManager(), this.gameEntityManager);

        sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Human/human2.png", 0,0,16,16,1,0.1f);
        Enemy e = new Enemy(sprite, 5, 6, 8);
        e.evade = 10;
        e.damage = 6;
        e.hit = 120;
        e.setAiActor(new BasicWarrior(this.playerController.getPlayerManager(), pathfinder, e));

        sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Human/human5.png", 0,0,16,16,1,0.1f);
        Enemy e2 = new Enemy(sprite, 10, 8, 8);
        e2.evade = 10;
        e2.damage = 6;
        e2.hit = 120;
        e2.setAiActor(new BasicWarrior(this.playerController.getPlayerManager(), pathfinder, e2));

        sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Human/human4.png", 0,0,16,16,1,0.1f);
        Enemy e3 = new Enemy(sprite, 13, 6, 8);
        e3.evade = 10;
        e3.damage = 6;
        e3.hit = 120;
        e3.setAiActor(new BasicWarrior(this.playerController.getPlayerManager(), pathfinder, e3));

        sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Monsters/enemy9.png", 0,0,16,16,1,0.1f);
        Enemy e4 = new Enemy(sprite, 13, 2, 8);
        e4.evade = 10;
        e4.damage = 6;
        e4.hit = 120;
        e4.setAiActor(new BasicWarrior(this.playerController.getPlayerManager(), pathfinder, e4));

        sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Monsters/enemy15.png", 0,0,16,16,1,0.1f);
        Enemy e5 = new Enemy(sprite, 17, 4, 8);
        e5.evade = 10;
        e5.damage = 6;
        e5.hit = 120;
        e5.setAiActor(new BasicWarrior(this.playerController.getPlayerManager(), pathfinder, e5));

        sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Monsters/enemy13.png", 0,0,16,16,1,0.1f);
        Enemy e6 = new Enemy(sprite, 19, 12, 8);
        e6.evade = 10;
        e6.damage = 6;
        e6.hit = 120;
        e6.setAiActor(new BasicWarrior(this.playerController.getPlayerManager(), pathfinder, e6));

        sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Monsters/enemy15.png", 0,0,16,16,1,0.1f);
        Enemy e7 = new Enemy(sprite, 10, 18, 8);
        e7.evade = 10;
        e7.damage = 6;
        e7.hit = 120;
        e7.setAiActor(new BasicWarrior(this.playerController.getPlayerManager(), pathfinder, e7));

        sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Human/human2.png", 0,0,16,16,1,0.1f);
        Enemy e8 = new Enemy(sprite, 15, 15, 8);
        e8.evade = 10;
        e8.damage = 6;
        e8.hit = 120;
        e8.setAiActor(new BasicWarrior(this.playerController.getPlayerManager(), pathfinder, e8));

        sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Human/human3.png", 0,0,16,16,1,0.1f);
        Enemy e9 = new Enemy(sprite, 19, 18, 8);
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

        //playerController.getPlayerManager().addPlayer(p);
        playerController.getPlayerManager().addPlayer(p3);

        // TODO: Make this not stupid.
        playerController.setupCursor();
        playerController.getCursor().x = 2;
        playerController.getCursor().y = 7;

        playerController.setupMenus();

        dialogue = new Dialogue();
        dialogue.loadDialogueXML(SharedAssets.CHAPTER_1);

        this.enemyStartTurn = true;
    }

    @Override
    public void render() {
        super.renderer.setView(camera);
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
            orb.setState("LEVEL2_EMPTY_FIELD");
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
            }
        }

        if (current != null) {
            current.tick();
            this.cameraController.set(current.x, current.y);
        } else {
            this.cameraController.set(this.playerController.getCursor().x, this.playerController.getCursor().y);
        }
        if(turnNum == 0 && !isShown) {
            this.dialogue.addDialogue("System:\nSean and Sasha enter", "Sean");
            this.dialogue.addDialogue("Sean:\nHey what's going on here!?","Sean");
            this.dialogue.addDialogue("Sasha:\nWe're here to help!", "Sasha");
            this.dialogue.setVisibility(true);
            Player seen = new Player("Sean", SharedAssets.seanSprite, 0, 6, 16, 4, 80, 60, 50, 20);
            seen.getPlayerInventory().pickUp(new Item("Sabre", true, Inventory.RHAND_SLOT, 3, 2, 3));
            seen.getPlayerInventory().equip(seen.getPlayerInventory().getCarry()[0]);
            seen.updateStats();
            playerController.getPlayerManager().addPlayer(seen);
            Player sash = new Player("Sean", SharedAssets.sashaSprite, 0, 8, 16, 4, 80, 60, 50, 20);
            sash.getPlayerInventory().pickUp(new Item("Pointy thruster", true, Inventory.RHAND_SLOT, 3, 2, 3));
            sash.getPlayerInventory().equip(sash.getPlayerInventory().getCarry()[0]);
            sash.updateStats();
            playerController.getPlayerManager().addPlayer(sash);
            isShown = true;
        }

        // TODO: Find a way to abstract this into the PlayerController.
        if (Controls.isKeyTapped(input, Controls.SELECT)) {
            Logger.debug("'SELECT' pressed.");
        }
    }

    private void handleSelect(Input input, Player current) {
        entityList = gameEntityManager.getEntitiesInRange(current.x, current.y);
        int selected;

        boolean inspecting = playerController.isInspecting(), using = playerController.isUsing();
        if ((selected = playerController.controlSelect(input, entityList)) != -1) {
            GameEntity e = null;

            if((e = entityList.get(selected)) == null) {
                dialogue.display("Professor Oak's words echo in your head: It is not the time to use this.");
                return;
            }
            if (using) {
                String response = e.onUse(this);
                dialogue.display(response);
                this.playerController.endTurn(current);
            } else if (inspecting) {
                String value = e.onInspect();
                dialogue.display(value);
            }
        }
    }

    private void handleAttack(Input input, Player current) {
        // Wait until an enemy is selected.
        if (attackingList == null) {
            attackingList = this.enemyController.getEnemyManager().getEnemiesInRange(current.x, current.y,
                    playerController.getAttackRange());
        }
        
        int selected;
        if ((selected = playerController.controlAttack(input, this.attackingList)) != -1) {
            if(!this.attackingList.isEmpty()) {
                int dmg = current.attack(this.attackingList.get(selected));
                boolean isDead = this.attackingList.get(selected).isDead();
                String dmgText = null;

                if (dmg < 0) {
                    dmgText = "Player attacks enemy and misses!";
                } else {
                    dmgText = "Player attacks enemy for " + dmg + " damage.";
                }
                if(isDead){
                    enemyController.getEnemyManager().removeEnemy(this.attackingList.get(selected));
                    dmgText += " The enemy is slain!";
                }
                this.dialogue.display(dmgText);

                this.attackingList = null;
            }
            else {
                //Logger.debug("Not allowed to attack nothing! ending your turn idiot...");
                Logger.debug("The character swings and only hits thin air...");
                this.dialogue.display("Player swings and hits only thin air");
            }
        }
        else{
            attackingList = this.enemyController.getEnemyManager().getEnemiesInRange(current.x, current.y,
            playerController.getAttackRange());
        }
    }
}
