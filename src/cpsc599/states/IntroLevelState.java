package cpsc599.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import cpsc599.OrbGame;
import cpsc599.ai.AStarPathfinder;
import cpsc599.ai.BasicWarrior;
import cpsc599.assets.AnimatedSprite;
import cpsc599.assets.Dialogue;
import cpsc599.assets.Enemy;
import cpsc599.assets.Player;
import cpsc599.controller.CameraController;
import cpsc599.controller.EnemyController;
import cpsc599.controller.PlayerController;
import cpsc599.items.Inventory;
import cpsc599.items.Item;
import cpsc599.managers.LevelManager;
import cpsc599.util.Controls;
import cpsc599.util.CoordinateTranslator;
import cpsc599.util.Logger;
import cpsc599.util.SharedAssets;

import java.util.ArrayList;

/**
 * Basic testing state.
 */
public class IntroLevelState extends LevelState {
    int currentEnemy = 0;
    float currentTime = 0f;

    private AnimatedSprite sprite;
    private Dialogue dialogue;
    private ArrayList<Enemy> attackingList;
    private boolean enemyStartTurn;

    public IntroLevelState(OrbGame game, LevelManager manager, PlayerController playerController,
                           CameraController cameraController, EnemyController enemyController) {
        super(game, playerController, cameraController, enemyController);
        super.setLevel(manager.setLevel(0));

        sprite = new AnimatedSprite("assets/tilesets/primary/CharacterDesign/characters/male/friend/friend_right.png", 0, 0, 16, 16, 1, 0.1f);

        Player p = new Player(sprite, 2, 5, 8, 14, 1, 3, 70, 80);

        Item sw = new Item("Pike", true, Inventory.RHAND_SLOT, 3, 5, 10);
        p.getPlayerInventory().pickUp(sw);
        p.getPlayerInventory().pickUp(new Item("Shield", true, Inventory.LHAND_SLOT, 1, 1, 5));
        p.getPlayerInventory().equip(sw);
        p.updateStats();

        sprite = new AnimatedSprite("assets/tilesets/primary/CharacterDesign/characters/female/main character/main_female_right.png", 0, 0, 16, 16, 1, 0.1f);
        Player p3 = new Player(sprite, 2, 7, 8, 10, 1, 2, 60, 70);
        p3.getPlayerInventory().pickUp(new Item("Staff", true, Inventory.RHAND_SLOT, 2, 2, 3));
        p3.getPlayerInventory().equip(p3.getPlayerInventory().getCarry()[0]);
        p3.getPlayerInventory().pickUp(new Item("Leather Belt", true, Inventory.LEGS_SLOT));
        p3.updateStats();

        // Set up the pathfinder for this level.
        AStarPathfinder pathfinder = new AStarPathfinder(this.currentLevel, playerController.getPlayerManager(),
                enemyController.getEnemyManager());

        sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Monsters/enemy13.png", 0,0,16,16,1,0.1f);
        Enemy e = new Enemy(sprite, 12, 7, 8);
        e.evade = 10;
        e.damage = 6;
        e.hit = 120;
        e.setAiActor(new BasicWarrior(this.playerController.getPlayerManager(), pathfinder, e));

        sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Monsters/enemy14.png", 0,0,16,16,1,0.1f);
        Enemy e2 = new Enemy(sprite, 10, 5, 8);
        e2.evade = 10;
        e2.damage = 6;
        e2.hit = 120;
        e2.setAiActor(new BasicWarrior(this.playerController.getPlayerManager(), pathfinder, e2));

        sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Monsters/enemy5.png", 0,0,16,16,1,0.1f);
        Enemy e3 = new Enemy(sprite, 10, 9, 8);
        e3.evade = 10;
        e3.damage = 6;
        e3.hit = 120;
        e3.setAiActor(new BasicWarrior(this.playerController.getPlayerManager(), pathfinder, e3));

        enemyController.getEnemyManager().addEnemy(e);
        enemyController.getEnemyManager().addEnemy(e2);
        enemyController.getEnemyManager().addEnemy(e3);

        playerController.getPlayerManager().addPlayer(p);
        playerController.getPlayerManager().addPlayer(p3);

        // TODO: Make this not stupid.
        playerController.setupCursor();
        playerController.getCursor().x = (int)manager.getCurrentLevel().player_spawn.x;
        playerController.getCursor().y = (int)manager.getCurrentLevel().player_spawn.y;

        playerController.setupMenus();

        dialogue = new Dialogue();
        dialogue.loadDialogueXML("src/cpsc599/assets/Script.xml");
        dialogue.setDialogueTag("testing");
        dialogue.toggleVisibility();

        this.enemyStartTurn = true;
    }

    @Override
    public void render() {
        super.renderer.setView(camera);
        super.drawLevel();

        super.groundLayer.begin();
        super.groundLayer.setProjectionMatrix(this.camera.combined);

        renderAttackables();

        // Render players and enemies.
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
        this.dialogue.render(this.overlayLayer);
    }

    private void renderAttackables() {
        if (this.playerController.isAttacking() && attackingList != null && attackingList.size() != 0) {
            for (int i = 0; i < attackingList.size(); i++) {
                Enemy e = attackingList.get(i);
                if (i == playerController.getSelectedAttack()) {
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

        if(enemyController.getEnemyManager().getEnemies().length == 0){
            orb.setState("PROLOGUE_CINEMATIC");
            //levelManager.setLevel(6);
        }

        //you will probably hate me for this...
        for(int i =0; i<playerController.getPlayerManager().getPlayers().length; i++)
        {
            if(playerController.getPlayerManager().getPlayer(i).isDead())
            {
                //add cool death scene here!
                playerController.getPlayerManager().removePlayer(i);
            }
        }

        if(playerController.getPlayerManager().getPlayers().length == 0)
        {
            //Game over!
            //add game over state
            orb.setState("GAME_OVER");
            //Logger.debug("Game Over...");
        }

        if (this.dialogue.isVisible()) {
            if (Controls.isKeyTapped(input, Controls.A_BUTTON)) {
                if (this.dialogue.checkTextLeft() && this.dialogue.isVisible()) {
                    this.dialogue.loadTextRemains();
                }
                else {
                    this.dialogue.toggleVisibility();
                }
            }
            return;
        }

        Player current = playerController.getPlayerManager().getCurrent();
        if (playerController.isAttacking()) {
            // Handle the attack and then return out.
            handleAttack(input, current);
            return;
        }

        if (!playerController.isTurnComplete()) {
            playerController.control(input, this.currentLevel);
        } else {
            if (enemyStartTurn) {
                this.dialogue.setDialogueText("Opponent's Turn");
                this.dialogue.toggleVisibility();
                this.enemyStartTurn = false;
                return;
            }


            // TODO: This is where we put some awesome enemy turn logic!!
            Enemy[] enemies = this.enemyController.getEnemyManager().getEnemies();
            if (currentEnemy > enemies.length - 1) {
                Logger.debug("Ending enemy turn.");
                playerController.resetTurn();
                this.currentEnemy = 0;
                enemyStartTurn = true;
            } else {
                Enemy e = enemies[currentEnemy];

                if (e.getAiActor() != null) {
                    if (e.getAiActor().inTurn()) {
                        if (e.getAiActor().step(currentTime, dialogue)) {
                            Logger.debug("Finishing turn for enemy[" + currentEnemy + "] - " + e);
                            e.tick();
                            currentEnemy++;
                        }

                        e.tick();
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

        // TODO: Find a way to abstract this into the PlayerController.
        if (Controls.isKeyTapped(input, Controls.SELECT)) {
            Logger.debug("'SELECT' pressed.");
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
                this.dialogue.setDialogueText(dmgText);
                this.dialogue.setVisibility(true);

                this.attackingList = null;
            }
            else {
                //Logger.debug("Not allowed to attack nothing! ending your turn idiot...");
                Logger.debug("The character swings and only hits thin air...");
                this.dialogue.setDialogueText("Player swings and hits only thin air.");
                this.dialogue.setVisibility(true);
            }
        }
        else{
            attackingList = this.enemyController.getEnemyManager().getEnemiesInRange(current.x, current.y,
            playerController.getAttackRange());
        }
    }
}
