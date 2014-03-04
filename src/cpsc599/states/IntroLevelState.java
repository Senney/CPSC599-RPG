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
import cpsc599.util.Logger;

/**
 * Basic testing state.
 */
public class IntroLevelState extends LevelState {
    int time = 0;

    private AnimatedSprite sprite;
    private Dialogue dialogue;

    public IntroLevelState(OrbGame game, LevelManager manager, PlayerController playerController,
                           CameraController cameraController, EnemyController enemyController) { //Need to add Enemy controller
        super(game, playerController, cameraController, enemyController);
        super.setLevel(manager.setLevel(0));

        sprite = new AnimatedSprite("assets/tilesets/primary/CharacterDesign/male.png", 0, 0, 16, 16, 1, 0.1f);

        Player p = new Player(sprite, 3, 3, 8);
        p.getPlayerInventory().pickUp(new Item("Sword", true, Inventory.RHAND_SLOT));
        p.getPlayerInventory().pickUp(new Item("Shield", true, Inventory.LHAND_SLOT));
        Player p2 = new Player(sprite, 5, 5, 6);

        sprite = new AnimatedSprite("assets/tilesets/Enemy.png", 0,0,16,16,1,0.1f);
        Enemy e = new Enemy(sprite, 1, 1, 3);

        enemyController.getEnemyManager().addEnemy(e);

        playerController.getPlayerManager().addPlayer(p);
        playerController.getPlayerManager().addPlayer(p2);

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
        
        super.overlayLayer.begin();
        Player current = playerController.getPlayerManager().getCurrent();
        // Render player-related overlays outside of the groundLayer.
        if (current != null) current.getPlayerHealthBar().render(10, 15, super.overlayLayer);
        this.playerController.getActMenu().render(this.overlayLayer);
        this.playerController.getInventoryMenu().render(super.overlayLayer);
        dialogue.render(this.overlayLayer);
        super.overlayLayer.end();
    }

    @Override
    public void tick(Input input) {
        time++;
        playerController.control(input, this.currentLevel);

        Player current = playerController.getPlayerManager().getCurrent();
        if (current != null) {
            current.tick();
            this.cameraController.set(current.x, current.y);

        } else {
            this.cameraController.set(this.playerController.getCursor().x, this.playerController.getCursor().y);
        }

        // TODO: Find a way to abstract this into the PlayerController.
        if (Controls.isKeyTapped(input, Controls.SELECT)) {
            Logger.debug("IntroLevelState::tick - 'SELECT' pressed.");
        }

        if (Controls.isKeyTapped(input, Input.Keys.D)) {
            Logger.debug("IntroLevelState::tick - Dialogue show button.");
            if (dialogue.checkTextLeft()) {
            	Logger.debug("HIT");
            	dialogue.loadTextRemains();
            }
            else {
            	this.dialogue.toggleVisibility();
            }
        }
    }
}
