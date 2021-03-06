package cpsc599;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import cpsc599.controller.CameraController;
import cpsc599.controller.EnemyController;
import cpsc599.controller.PlayerController;
import cpsc599.managers.EnemyManager;
import cpsc599.managers.LevelManager;
import cpsc599.managers.PlayerManager;
import cpsc599.managers.StateManager;
import cpsc599.states.*;
import cpsc599.states.Level1.*;
import cpsc599.states.Level2.Level2AfterBattleCinematic;
import cpsc599.states.Level2.Level2BattleState;
import cpsc599.states.Level2.Level2EmptyFieldCinematic;
import cpsc599.states.Level3.Level3BattleState;
import cpsc599.states.Level3.Level3FieldCinematic;
import cpsc599.states.Level3.Level3Finale;
import cpsc599.states.Level4.Level4BattleState;
import cpsc599.states.Level4.Level4FieldCinematic;
import cpsc599.states.Level4.Level4Finale;
import cpsc599.states.Level5.*;
import cpsc599.states.Prologue.PrologueCinematicState;
import cpsc599.util.Logger;
import cpsc599.util.SharedAssets;

import java.io.IOException;

public class OrbGame implements ApplicationListener {
    public static float frameTime = 0f;

	private AssetManager assetManager;
    private LevelManager levelManager;
    private StateManager stateManager;
    private PlayerManager playerManager;
    private EnemyManager enemyManager;

    private CameraController cameraController;
    private PlayerController playerController;
    private EnemyController enemyController;

    public int width, height;
    private double scale;

    // 60 logic ticks per second.
    final private float accum_time = 1.0f / 60.0f;
    private float accumulator;
	
	public OrbGame(int w, int h, double scale) {
		Logger.debug("OrbGame class constructed.");

        this.accumulator = 0.0f;
        this.width = w;
        this.height = h;
        this.scale = scale;

	}

	@Override
	public void dispose() {
		Logger.debug("OrbGame class disposing.");
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

    public void setState(String stateName) {
        if (this.stateManager.setState(stateName) == null) {
            Logger.fatal("Unable to set state to " + stateName);
            System.exit(1);
        }
        this.stateManager.current.init(this);
    }

    public void tick() {
        frameTime += Gdx.graphics.getDeltaTime();

        playerController.setLevel(levelManager.getCurrentLevel());
        this.stateManager.current.tick(Gdx.input);
    }

	@Override
	public void render() {
        // Clear the rendering surface.
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        accumulator += Gdx.graphics.getDeltaTime();
		while (accumulator >= accum_time) {
            this.tick();
            accumulator -= accum_time;
        }

        this.stateManager.current.render();
        this.cameraController.getCamera().apply(Gdx.graphics.getGL10());
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create() {
		Logger.debug("OrbGame class create method run.");
        Logger.debug("Window created with size: (" + this.width + ", " + this.height + "), " +
                "Scale: " + this.scale);

        SharedAssets.load();

        assetManager = new AssetManager();
        stateManager = new StateManager();
        playerManager = new PlayerManager();
        enemyManager = new EnemyManager();

        enemyController = new EnemyController(enemyManager);
        playerController = new PlayerController(playerManager, enemyManager); //this might cause issues
        cameraController = new CameraController(new Vector2(0, 0), new Vector3(this.width, this.height, 1));

        levelManager = new LevelManager(assetManager);
        try {
        	levelManager.addLevelDir("assets/tilesets/primary/Maps/Prologue");
            levelManager.addLevelDir("assets/tilesets/primary/Maps/Chapter1");
            levelManager.addLevelDir("assets/tilesets/primary/Maps/Chapter2");
            levelManager.addLevelDir("assets/tilesets/primary/Maps/Chapter3");
            levelManager.addLevelDir("assets/tilesets/primary/Maps/Chapter4");
            levelManager.addLevelDir("assets/tilesets/primary/Maps/Chapter5");
        } catch (IOException ex) {
            Logger.fatal("LevelManager creation failed... Exiting.");
            System.exit(1);
        }

        playerController.setLevel(levelManager.getCurrentLevel());

        // TODO: Fill this in with the proper state.
        // Start and Finish states
        stateManager.addState("MAIN_MENU", new MainMenuState());
        stateManager.addState("GAME_OVER", new GameOverState());

        // Prologue State
        stateManager.addState("PROLOGUE", new PrologueCinematicState(this, levelManager.setLevel("black"), this.cameraController, "LEVEL1_VILLAGE"));

        // Chapter 1 States
        stateManager.addState("LEVEL1_VILLAGE", new Level1VillageCinematic(this, levelManager.setLevel("field_map"), cameraController, "LEVEL1_FLOWER_FIELD"));
        stateManager.addState("LEVEL1_FLOWER_FIELD", new Level1FlowerFieldCinematic(this, levelManager.setLevel("flower_field_map"), cameraController, "LEVEL1_CHASE"));
        stateManager.addState("LEVEL1_CHASE", new Level1ChaseCinematic(this, levelManager.setLevel("level0"), cameraController, "LEVEL1"));
        stateManager.addState("LEVEL1", new IntroLevelState(this, levelManager, playerController, cameraController, enemyController));
        stateManager.addState("LEVEL1_FINALE", new Level1Finale(this, levelManager.setLevel("level0"), cameraController, "LEVEL2_EMPTY_FIELD"));

        // Chapter 2 states
        stateManager.addState("LEVEL2_EMPTY_FIELD", new Level2EmptyFieldCinematic(this, levelManager.setLevel("level1"), cameraController, "LEVEL2"));
        stateManager.addState("LEVEL2", new Level2BattleState(this, levelManager, playerController, cameraController, enemyController));
        //stateManager.addState("LEVEL2", new Level2BattleState(this, levelManager, playerController, cameraController, enemyController));
        stateManager.addState("LEVEL2_FINALE", new Level2AfterBattleCinematic(this, levelManager.setLevel("level1"), this.cameraController, "LEVEL3_FIELD"));

        // Chapter 3 states
        stateManager.addState("LEVEL3_FIELD", new Level3FieldCinematic(this, levelManager.setLevel("level2"), this.cameraController, "LEVEL3"));
        stateManager.addState("LEVEL3", new Level3BattleState(this, levelManager, playerController, cameraController, enemyController));
        stateManager.addState("LEVEL3_FINALE", new Level3Finale(this, levelManager.setLevel("level2"), this.cameraController, "LEVEL4_FIELD"));

        // Chapter 4 states
        stateManager.addState("LEVEL4_FIELD", new Level4FieldCinematic(this, levelManager.setLevel("level3"), this.cameraController, "LEVEL4"));
        stateManager.addState("LEVEL4", new Level4BattleState(this, levelManager, playerController, cameraController, enemyController));
        stateManager.addState("LEVEL4_FINALE", new Level4Finale(this, levelManager.setLevel("level3"), this.cameraController, "LEVEL5_CRYSTAL_CASTLE"));

        // Chapter 5 states
        stateManager.addState("LEVEL5_CRYSTAL_CASTLE", new Level5CrystalCastleCinematic(this, levelManager.setLevel("crystal_castle"), this.cameraController, "LEVEL5_INSIDE_CASTLE"));
        stateManager.addState("LEVEL5_INSIDE_CASTLE", new Level5InsideCastleBattleState(this, levelManager, playerController, cameraController, enemyController));
        stateManager.addState("LEVEL5_BLACKOUT", new Level5BlackoutCinematic(this, levelManager.setLevel("blackout_castle"), this.cameraController, "LEVEL5_ALMIGHTY"));
        stateManager.addState("LEVEL5_ALMIGHTY", new Level5AlmightyCinematic(this, levelManager.setLevel("throne_room"), this.cameraController, "LEVEL5_LABYRINTH"));
        stateManager.addState("LEVEL5_LABYRINTH", new Level5LabyrinthBattleState(this, levelManager, playerController, cameraController, enemyController));
        stateManager.addState("LEVEL5_FINALE", new Level5FinaleCinematic(this, levelManager.setLevel("empty_throne_room"), this.cameraController, "WON"));
        stateManager.addState("WON", new GameWonState());
        
        setState("MAIN_MENU");
	}

    public StateManager getStateManager() {
        return stateManager;
    }
}
