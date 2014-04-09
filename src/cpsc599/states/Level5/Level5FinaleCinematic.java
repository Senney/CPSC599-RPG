package cpsc599.states.Level5;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

import cpsc599.OrbGame;
import cpsc599.assets.AnimatedSprite;
import cpsc599.assets.CinematicAction;
import cpsc599.assets.Level;
import cpsc599.assets.Player;
import cpsc599.controller.CameraController;
import cpsc599.states.CinematicState;
import cpsc599.util.SharedAssets;

public class Level5FinaleCinematic extends CinematicState {
	Player hik, sean, sasha;
	Player birdcage0, birdcage, ren;
	
    public Level5FinaleCinematic(OrbGame game, Level level, CameraController cameraController, String nextState) {
        super(game, level, cameraController, nextState);
    }

    @Override
    public void init(OrbGame game) {
        super.init(game);
        
        AnimatedSprite hikSprite = new AnimatedSprite("assets/tilesets/primary/CharacterDesign/female.png",
                0, 1, 16, 16, 1, 0.1f);
        AnimatedSprite seanSprite = new AnimatedSprite("assets/tilesets/primary/CharacterDesign/male.png",
                3, 1, 16, 16, 1, 0.1f);
        AnimatedSprite sashaSprite = new AnimatedSprite("assets/tilesets/primary/CharacterDesign/female.png",
                2, 1, 16, 16, 1, 0.1f);
        AnimatedSprite cage0Sprite = new AnimatedSprite("assets/tilesets/primary/Castle/prince_birdcage.png",
                0, 0, 64, 64, 1, 0.1f);
        AnimatedSprite cageSprite = new AnimatedSprite("assets/tilesets/primary/Castle/empty_birdcage.png",
                0, 0, 64, 64, 1, 0.1f);
        AnimatedSprite renSprite = new AnimatedSprite("assets/tilesets/primary/CharacterDesign/male.png",
                0, 0, 16, 16, 1, 0.1f);
        
        hik = new Player("Hikari", hikSprite, 8, 8, 0);
        sean = new Player("Sean", seanSprite, 9, 9, 0);
        sasha = new Player("Sasha", sashaSprite, 7, 9, 0);
        birdcage0 = new Player("Birdcage0", cage0Sprite, 10, 1, 0);
        
        birdcage = new Player("Birdcage", cageSprite);
        ren = new Player("Ren", renSprite);
        
        this.playerController.getPlayerManager().addPlayer(hik);
        this.playerController.getPlayerManager().addPlayer(sean);
        this.playerController.getPlayerManager().addPlayer(sasha);
        this.playerController.getPlayerManager().addPlayer(birdcage0);
        
        this.dialogue.loadDialogueXML(SharedAssets.CHAPTER_5);
        this.dialogue.setDialogueTag("p5");
        this.dialogue.setVisibility(true);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void tick(Input input) {
        // Before AI actions.

        super.tick(input);

        // After AI actions.
        if (actions.size() == 0) goToNextState();
    }

    @Override
    protected void loadCinematicActions() {
    	actions.add(CinematicAction.moveTo(hik, new Vector2(11, 4), 0.2f));
    	actions.add(CinematicAction.wait(1.0f));
    	
    	actions.add(CinematicAction.stepDialogue(this.dialogue));
    	actions.add(CinematicAction.wait(0.6f));
    	
    	actions.add(CinematicAction.despawnActor(hik));
    	actions.add(CinematicAction.despawnActor(sean));
    	actions.add(CinematicAction.despawnActor(sasha));
    	actions.add(CinematicAction.despawnActor(birdcage0));
    	
    	actions.add(CinematicAction.spawnActor(hik, new Vector2(8, 8)));
    	actions.add(CinematicAction.spawnActor(sean, new Vector2(9, 9)));
    	actions.add(CinematicAction.spawnActor(sasha, new Vector2(7, 9)));
    	actions.add(CinematicAction.spawnActor(ren, new Vector2(8, 6)));
    	actions.add(CinematicAction.spawnActor(birdcage, new Vector2(10, 1)));
    	
    	actions.add(CinematicAction.stepDialogue(this.dialogue));
    	actions.add(CinematicAction.stepDialogue(this.dialogue));
    	actions.add(CinematicAction.stepDialogue(this.dialogue));
    	actions.add(CinematicAction.stepDialogue(this.dialogue));
    	actions.add(CinematicAction.stepDialogue(this.dialogue));
    	actions.add(CinematicAction.stepDialogue(this.dialogue));
    	actions.add(CinematicAction.stepDialogue(this.dialogue));
    	actions.add(CinematicAction.stepDialogue(this.dialogue));
    	actions.add(CinematicAction.moveTo(hik, new Vector2(8, 7)));
    	actions.add(CinematicAction.stepDialogue(this.dialogue));
    	actions.add(CinematicAction.wait(0.4f));
    	
    	actions.add(CinematicAction.wait(0.4f));

        super.loadCinematicActions();
    }
}
