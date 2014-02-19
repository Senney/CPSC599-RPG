package cpsc599.states;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import cpsc599.OrbGame;
import cpsc599.managers.LevelManager;
import cpsc599.util.Logger;

/**
 * Created by KRUSHER40 on 2/14/14.
 */
public class IntroLevelState extends LevelState {
    int time = 0;

    public IntroLevelState(OrbGame game, LevelManager manager) {
        super(game);
        super.setLevel(manager.setLevel(0));

        TiledMapTileLayer layer = (TiledMapTileLayer)this.currentLevel.tiledMap.getLayers().get(0);
    }

    @Override
    public void render() {
        super.renderer.setView(camera);
        super.drawLevel();
    }

    @Override
    public void tick(Input input) {
        time++;

        if (input.isKeyPressed(Input.Keys.UP)) {
            super.camera.translate(0f, -TRANSLATE_SPEED);
        }

        if (input.isKeyPressed(Input.Keys.S)) {
            super.camera.translate(0f, TRANSLATE_SPEED);
        }

        if (input.isKeyPressed(Input.Keys.A)) {
            super.camera.translate(TRANSLATE_SPEED, 0f);
        } else if (input.isKeyPressed(Input.Keys.D)) {
            super.camera.translate(-TRANSLATE_SPEED, 0f);
        }

        super.camera.update();
    }
}
