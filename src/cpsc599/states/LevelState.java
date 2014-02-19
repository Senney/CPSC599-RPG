package cpsc599.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Disposable;
import cpsc599.Main;
import cpsc599.OrbGame;
import cpsc599.assets.Level;
import cpsc599.util.Logger;

public abstract class LevelState extends State {
    protected Level currentLevel;

    protected OrthogonalTiledMapRenderer renderer;
    protected OrthographicCamera camera;
    protected static float TRANSLATE_SPEED = 2f;

    protected LevelState(OrbGame game) {
        super.init(game);
    }

    protected void setLevel(Level level) {
        if (this.spriteBatch == null) {
            Logger.fatal("LevelState::setLevel - Hey developer! Call init(OrbGame) first!");
        }

        if (level == null) {
            Logger.fatal("LevelState::setLevel - level was NULL!");
            return;
        }

        if (level.tiledMap == null) {
            Logger.fatal("LevelState::setLevel - Level manager ");
            return;
        }


        camera = new OrthographicCamera();
        camera.setToOrtho(false);

        this.currentLevel = level;
        renderer = new OrthogonalTiledMapRenderer(currentLevel.tiledMap, super.spriteBatch);
        renderer.setView(camera);
    }

    @Override
    public abstract void render();

    @Override
    public abstract void tick(Input input);

    public Level getLevel() {
        return this.currentLevel;
    }

    protected void drawLevel() {
        // TODO: Add the logic to draw a level here.
        renderer.render();

        this.spriteBatch.begin();
        this.drawString("FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);
        this.spriteBatch.end();
    }
}
