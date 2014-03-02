package cpsc599.states;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import cpsc599.OrbGame;
import cpsc599.assets.AnimatedSprite;
import cpsc599.assets.Enemy;
import cpsc599.assets.Player;
import cpsc599.controller.CameraController;
import cpsc599.controller.EnemyController;
import cpsc599.controller.PlayerController;
import cpsc599.items.Inventory;
import cpsc599.items.Item;
import cpsc599.managers.LevelManager;
import cpsc599.menus.ActionMenu;
import cpsc599.menus.InventoryMenu;
import cpsc599.util.Controls;
import cpsc599.util.Logger;

/**
 * Basic testing state.
 */
public class IntroLevelState extends LevelState {
    int time = 0;

    private AnimatedSprite sprite;
    private InventoryMenu inventoryMenu;

    public IntroLevelState(OrbGame game, LevelManager manager, PlayerController playerController,
                           CameraController cameraController, EnemyController enemyController) { //Need to add Enemy controller
        super(game, playerController, cameraController, enemyController);
        super.setLevel(manager.setLevel(0));

        sprite = new AnimatedSprite("assets/tilesets/cowcube.png", 0, 0, 16, 16, 1, 0.1f);

        Player p = new Player(sprite, 10, 10, 8);
        p.getPlayerInventory().pickUp(new Item("Sword", true, Inventory.RHAND_SLOT));
        p.getPlayerInventory().pickUp(new Item("Shield", true, Inventory.LHAND_SLOT));
        Player p2 = new Player(sprite, 12, 12, 6);

        sprite = new AnimatedSprite("assets/tilesets/Enemy.png", 0,0,16,16,1,0.1f);
        Enemy e = new Enemy(sprite, 14, 14, 3);

        enemyController.getEnemyManager().addEnemy(e);

        playerController.getPlayerManager().addPlayer(p);
        playerController.getPlayerManager().addPlayer(p2);

        // TODO: Make this not stupid.
        playerController.setupCursor();

        playerController.setupActionmenu();

        inventoryMenu = new InventoryMenu(100, 200);
    }

    @Override
    public void render() {
        super.renderer.setView(camera);
        super.drawLevel();

        super.enemyLayer.begin();
        super.enemyLayer.setProjectionMatrix(this.camera.combined);
        for(Enemy e : enemyController.getEnemyManager().getEnemies())
            e.render(super.enemyLayer);
        super.enemyLayer.end();

        super.playerLayer.begin();
        inventoryMenu.render(super.playerLayer);
        this.playerController.getActMenu().render(this.playerLayer);
        super.playerLayer.setProjectionMatrix(this.camera.combined);
        for (Player p : playerController.getPlayerManager().getPlayers())
            p.render(super.playerLayer);
        if (this.playerController.isCursor()) {
            this.playerController.getCursor().render(this.playerLayer);
        }
        super.playerLayer.end();
    }

    @Override
    public void tick(Input input) {
        time++;
        playerController.control(input);

        Player current = playerController.getPlayerManager().getCurrent();
        if (current != null) {
            current.tick();
            this.cameraController.set(current.x, current.y);
            inventoryMenu.setInventory(current.getPlayerInventory());

        } else {
            this.cameraController.set(this.playerController.getCursor().x, this.playerController.getCursor().y);
        }

        // TODO: Find a way to abstract this into the PlayerController.
        if (Controls.isKeyTapped(input, Controls.SELECT)) {
            Logger.debug("IntroLevelState::tick - 'SELECT' pressed.");
            if (current == null) {
                // Move cursor to the next available player.
            } else {
                Boolean isVis = false;
                // Show the inventory.
                if(playerController.getActMenu().isVisible())
                {
                    playerController.getActMenu().toggleVisible();
                    isVis = true;
                }
                this.inventoryMenu.toggleVisible();

                if(!this.inventoryMenu.isVisible() && isVis)
                    playerController.getActMenu().toggleVisible();
            }
        }

        /*
        if (input.isKeyPressed(Controls.UP)) {
            super.camera.translate(0f, -TRANSLATE_SPEED);
        }

        if (input.isKeyPressed(Controls.DOWN)) {
            super.camera.translate(0f, TRANSLATE_SPEED);
        }

        if (input.isKeyPressed(Controls.LEFT)) {
            super.camera.translate(TRANSLATE_SPEED, 0f);
        } else if (input.isKeyPressed(Controls.RIGHT)) {
            super.camera.translate(-TRANSLATE_SPEED, 0f);
        }
        super.camera.update();
        */
    }
}
