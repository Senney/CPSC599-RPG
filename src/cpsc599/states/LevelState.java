package cpsc599.states;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import cpsc599.Main;
import cpsc599.OrbGame;
import cpsc599.assets.Level;
import cpsc599.controller.CameraController;
import cpsc599.controller.EnemyController;
import cpsc599.controller.PlayerController;
import cpsc599.managers.GameEntityManager;
import cpsc599.util.Logger;

public abstract class LevelState extends State {
    protected Level currentLevel;

    protected OrthogonalTiledMapRenderer renderer;
    protected OrthographicCamera camera;
    protected PlayerController playerController;
    protected CameraController cameraController;
    protected GameEntityManager gameEntityManager;
    protected EnemyController enemyController;
    protected static float TRANSLATE_SPEED = 1f;

    protected SpriteBatch groundLayer;
    protected SpriteBatch overlayLayer;

    protected LevelState(OrbGame game, PlayerController playerController, CameraController cameraController, EnemyController enemyController) {
        super.init(game);
        this.playerController = playerController;
        this.cameraController = cameraController;
        this.enemyController = enemyController;
        this.groundLayer = new SpriteBatch();
        this.overlayLayer = new SpriteBatch();
    }

    protected void setLevel(Level level) {
        if (this.spriteBatch == null) {
            Logger.fatal("Hey developer! Call init(OrbGame) first!");
        }

        if (level == null) {
            Logger.fatal("level was NULL!");
            return;
        }

        if (level.tiledMap == null) {
            Logger.fatal("Level manager ");
            return;
        }


        camera = (OrthographicCamera)cameraController.getCamera();

        this.currentLevel = level;
        renderer = new OrthogonalTiledMapRenderer(currentLevel.tiledMap, 1.0f, super.spriteBatch);
        renderer.setView(camera.combined, 0, 0, Main.GAME_WIDTH, Main.GAME_HEIGHT);

        Vector2 dimensions = this.currentLevel.getMapDimensions();
        this.cameraController.setCameraBounds(dimensions);
        this.gameEntityManager = new GameEntityManager((int)dimensions.x, (int)dimensions.y);
        this.playerController.setGameEntityManager(this.gameEntityManager);
    }

    @Override
    public abstract void render();

    @Override
    public abstract void tick(Input input);

    public Level getLevel() {
        return this.currentLevel;
    }

    protected void drawLevel() {
        renderer.render();
    }

    public Camera getCamera() {
        return this.camera;
    }
}
