package cpsc599.states;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import cpsc599.Main;
import cpsc599.OrbGame;
import cpsc599.assets.*;
import cpsc599.controller.CameraController;
import cpsc599.controller.EnemyController;
import cpsc599.controller.PlayerController;
import cpsc599.managers.GameEntityManager;
import cpsc599.util.CoordinateTranslator;
import cpsc599.util.Logger;
import cpsc599.util.SharedAssets;

import java.util.ArrayList;
import java.util.List;

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
    protected ArrayList<Enemy> attackingList;
    protected List<GameEntity> entityList;
    protected Dialogue dialogue;


    protected LevelState(OrbGame game, PlayerController playerController, CameraController cameraController, EnemyController enemyController) {
        //super.init(game);
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

    protected void renderUsables() {
        if ((this.playerController.isInspecting() || this.playerController.isUsing()) && this.entityList != null) {
            for (int i = 0; i < entityList.size(); i++) {
                GameEntity e = entityList.get(i);
                int x = (int)e.getPosition().x, y = (int)e.getPosition().y;
                if (i == playerController.getSelectedUnit()) {
                    groundLayer.draw(SharedAssets.cursorHighlighted, CoordinateTranslator.translate(x),
                            CoordinateTranslator.translate(y));
                } else {
                    groundLayer.draw(SharedAssets.cursorNormal, CoordinateTranslator.translate(x),
                            CoordinateTranslator.translate(y));
                }
            }
        }
    }

    protected void renderAttackables() {
        if (this.playerController.isAttacking() && attackingList != null && attackingList.size() != 0) {
            for (int i = 0; i < attackingList.size(); i++) {
                Enemy e = attackingList.get(i);
                if (i == playerController.getSelectedUnit()) {
                    groundLayer.draw(SharedAssets.cursorHighlighted, CoordinateTranslator.translate(e.x),
                            CoordinateTranslator.translate(e.y));
                } else {
                    groundLayer.draw(SharedAssets.cursorNormal, CoordinateTranslator.translate(e.x),
                            CoordinateTranslator.translate(e.y));
                }
            }
        }
    }

    public void renderGroundLayer() {
        // Render players and enemies.
        this.gameEntityManager.render(groundLayer);
        if (playerController.getPlayerManager().getCurrent() != null) {
            Player current = playerController.getPlayerManager().getCurrent();
            groundLayer.draw(SharedAssets.highlight3, CoordinateTranslator.translate(current.x),
                    CoordinateTranslator.translate(current.y));
        }
        for (Player p : playerController.getPlayerManager().getPlayers())
            p.render(groundLayer);
        for(Enemy e : enemyController.getEnemyManager().getEnemies())
            e.render(groundLayer);

        // If we're in cursor-mode, render the cursor.
        if (this.playerController.isCursor()) {
            this.playerController.getCursor().render(this.groundLayer);
        }
    }

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


    protected void handleSelect(Input input, Player current) {
        entityList = gameEntityManager.getEntitiesInRange(current.x, current.y);
        int selected;

        boolean inspecting = playerController.isInspecting(), using = playerController.isUsing();
        if ((selected = playerController.controlSelect(input, entityList)) != -1) {
            GameEntity e = null;

            if(entityList.size() == 0 || (e = entityList.get(selected)) == null) {
                dialogue.display("Professor Oak's words echo in your head: It is not the time to use this.");
                return;
            }
            if (using) {
                String response = e.onUse(this, current);
                dialogue.display(response);
                this.playerController.endTurn(current);
            } else if (inspecting) {
                String value = e.onInspect();
                dialogue.display(value);
            }
        }
    }

    protected void handleAttack(Input input, Player current) {
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
