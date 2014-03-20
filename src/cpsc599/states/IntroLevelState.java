package cpsc599.states;

import com.badlogic.gdx.Input;
import cpsc599.OrbGame;
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
    int time = 0;
    int tickCount = 0;

    private AnimatedSprite sprite;
    private Dialogue dialogue;
    private ArrayList<Enemy> attackingList;

    public IntroLevelState(OrbGame game, LevelManager manager, PlayerController playerController,
                           CameraController cameraController, EnemyController enemyController) { //Need to add Enemy controller
        super(game, playerController, cameraController, enemyController);
        super.setLevel(manager.setLevel(0));

        sprite = new AnimatedSprite("assets/tilesets/primary/CharacterDesign/characters/male/prince/prince_front.png", 0, 0, 16, 16, 1, 0.1f);

        Player p = new Player(sprite, 2, 5, 8);
        //p.getPlayerInventory().pickUp(new Item("Sword", true, Inventory.RHAND_SLOT, 1));
        //p.getPlayerInventory().pickUp(new Item("Shield", true, Inventory.LHAND_SLOT, 0));

        Item sw = new Item("Pike", true, Inventory.RHAND_SLOT, 3, 5);
        p.getPlayerInventory().pickUp(sw);
        p.getPlayerInventory().pickUp(new Item("Shield", true, Inventory.LHAND_SLOT, 1, 1));
        p.getPlayerInventory().equip(sw);


        sprite = new AnimatedSprite("assets/tilesets/primary/CharacterDesign/characters/female/main character/main_female_front.png", 0, 0, 16, 16, 1, 0.1f);
        Player p3 = new Player(sprite, 2, 7, 8);
        p3.getPlayerInventory().pickUp(new Item("Staff", true, Inventory.RHAND_SLOT, 2, 2));
        p3.getPlayerInventory().equip(p3.getPlayerInventory().getCarry()[0]);
        p3.getPlayerInventory().pickUp(new Item("Leather Belt", true, Inventory.LEGS_SLOT));

        /*
        sprite = new AnimatedSprite("assets/tilesets/primary/CharacterDesign/characters/male/friend/friend_front.png", 0, 0, 16, 16, 1, 0.1f);
        Player p2 = new Player(sprite, 2, 9, 8);
        p2.getPlayerInventory().pickUp(new Item("Health Potion", false, Inventory.NONE, 1));
        p2.getPlayerInventory().pickUp(new Item("Wand", true, Inventory.RHAND_SLOT, 2));

        AnimatedSprite cowCube = new AnimatedSprite("assets/tilesets/cowcube.png", 0, 0, 16, 16, 1, 0f);
        Player p4 = new Player(cowCube, 20, 27, 20);
        */

        sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Monsters/enemy13.png", 0,0,16,16,1,0.1f);
        Enemy e = new Enemy(sprite, 12, 7, 8);
        
        sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Monsters/enemy14.png", 0,0,16,16,1,0.1f);
        Enemy e2 = new Enemy(sprite, 10, 5, 8);
        
        sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Monsters/enemy5.png", 0,0,16,16,1,0.1f);
        Enemy e3 = new Enemy(sprite, 10, 9, 8);

        enemyController.getEnemyManager().addEnemy(e);
        enemyController.getEnemyManager().addEnemy(e2);
        enemyController.getEnemyManager().addEnemy(e3);

        playerController.getPlayerManager().addPlayer(p);
//        playerController.getPlayerManager().addPlayer(p2);
        playerController.getPlayerManager().addPlayer(p3);
//        playerController.getPlayerManager().addPlayer(p4);

        // TODO: Make this not stupid.
        playerController.setupCursor();
        playerController.getCursor().x = (int)manager.getCurrentLevel().player_spawn.x;
        playerController.getCursor().y = (int)manager.getCurrentLevel().player_spawn.y;

        playerController.setupMenus();
        
        dialogue = new Dialogue();
    }

    @Override
    public void render() {
        super.renderer.setView(camera);
        super.drawLevel();

        super.groundLayer.begin();
        super.groundLayer.setProjectionMatrix(this.camera.combined);

        renderAttackables();

        // Render players and enemies.
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
        time++;

        Player current = playerController.getPlayerManager().getCurrent();
        if (playerController.isAttacking()) {
            handleAttack(input, current);
        }
        if (!playerController.isTurnComplete()) {
            playerController.control(input, this.currentLevel);
        } else {
            if (tickCount == 0)
                Logger.debug("WAITING 400 ticks for a simulated enemy turn.");
            // TODO: This is where we put some awesome enemy turn logic!!

            // TODO: Remove this crap when we get some AI.
            if (tickCount++ > 400) {
                Logger.debug("IntroLevelState::tick - Enemy turn complete..");
                playerController.resetTurn();
                tickCount = 0;
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

        if (Controls.isKeyTapped(input, Input.Keys.D)) {
            Logger.debug("Dialogue show button.");
            if (this.dialogue.checkTextLeft() && this.dialogue.isVisible()) {
            	this.dialogue.loadTextRemains();
            }
            else {
            	this.dialogue.toggleVisibility();
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
                current.attack(this.attackingList.get(selected));
                this.attackingList = null;
            }
            else
                //Logger.debug("Not allowed to attack nothing! ending your turn idiot...");
                Logger.debug("The character swings and only hits thin air...");
        }
        else{
            attackingList = this.enemyController.getEnemyManager().getEnemiesInRange(current.x, current.y,
            playerController.getAttackRange());
        }
    }
}
