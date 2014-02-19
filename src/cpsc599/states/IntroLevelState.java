package cpsc599.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import cpsc599.OrbGame;
import cpsc599.assets.AnimatedSprite;
import cpsc599.managers.LevelManager;
import cpsc599.util.Controls;
import cpsc599.util.Logger;

/**
 * Basic testing state.
 */
public class IntroLevelState extends LevelState {
    int time = 0;

    private AnimatedSprite sprite;

    public IntroLevelState(OrbGame game, LevelManager manager) {
        super(game);
        super.setLevel(manager.setLevel(0));

        TiledMapTileLayer layer = (TiledMapTileLayer)this.currentLevel.tiledMap.getLayers().get(0);
        sprite = new AnimatedSprite("assets/tilesets/mario.png", 0, 0, 16, 28, 4, 0.1f);
    }

    @Override
    public void render() {
        super.renderer.setView(camera);
        super.drawLevel();

        super.spriteBatch.begin();
        sprite.render(super.spriteBatch, 32, 32);
        super.spriteBatch.end();
    }

    @Override
    public void tick(Input input) {
        time++;

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
    }
}
