package cpsc599.states;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import cpsc599.OrbGame;
import cpsc599.assets.AnimatedSprite;
import cpsc599.assets.Player;
import cpsc599.controller.CameraController;
import cpsc599.controller.PlayerController;
import cpsc599.managers.LevelManager;

/**
 * Basic testing state.
 */
public class IntroLevelState extends LevelState {
    int time = 0;

    private AnimatedSprite sprite;

    public IntroLevelState(OrbGame game, LevelManager manager, PlayerController playerController, CameraController cameraController) {
        super(game, playerController, cameraController);
        super.setLevel(manager.setLevel(0));

        TiledMapTileLayer layer = (TiledMapTileLayer)this.currentLevel.tiledMap.getLayers().get(0);
        sprite = new AnimatedSprite("assets/tilesets/testsquare.png", 0, 0, 16, 16, 1, 0.1f);

        playerController.getPlayerManager().addPlayer(new Player(sprite));
    }

    @Override
    public void render() {
        super.renderer.setView(camera);
        super.drawLevel();

        super.spriteBatch.begin();
        for (Player p : playerController.getPlayerManager().getPlayers())
            p.render(super.spriteBatch);
        super.spriteBatch.end();
    }

    @Override
    public void tick(Input input) {
        time++;
        playerController.control(input);

        Player current = playerController.getPlayerManager().getCurrent();
        current.tick();

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
        */

        this.cameraController.set(current.x, current.y);
    }
}
