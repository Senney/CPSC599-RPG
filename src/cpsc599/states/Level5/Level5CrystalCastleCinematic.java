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

public class Level5CrystalCastleCinematic extends CinematicState {
	Player hik, sean, sasha;
	Player orb, orb2;
	
	public Level5CrystalCastleCinematic(OrbGame game, Level level, CameraController cameraController, String nextState) {
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
        AnimatedSprite orbSprite = new AnimatedSprite("assets/tilesets/primary/Items/orb.png", 0, 0, 16, 16, 1, 0.1f);
        AnimatedSprite orb2Sprite = new AnimatedSprite("assets/tilesets/primary/Items/orb2.png", 0, 0, 16, 16, 1, 0.1f);
        
        hik = new Player("Hikari", hikSprite, 8, 12, 0);
        sean = new Player("Sean", seanSprite, 9, 13, 0);
        sasha = new Player("Sasha", sashaSprite, 7, 13, 0);
        orb = new Player("Orb", orbSprite, 7, 5, 0);
        orb2 = new Player("Orb2", orb2Sprite, 7, 5, 0);
        
        this.cameraController.set(0, 15);

        this.playerController.getPlayerManager().addPlayer(hik);
        this.playerController.getPlayerManager().addPlayer(sean);
        this.playerController.getPlayerManager().addPlayer(sasha);
        
        this.dialogue.loadDialogueXML(SharedAssets.CHAPTER_5);
        this.dialogue.setDialogueTag("p1");
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
    	actions.add(CinematicAction.stepDialogue(this.dialogue));
    	actions.add(CinematicAction.wait(0.2f));
    	
    	actions.add(CinematicAction.moveToConcurrent(hik, new Vector2(8, 11)));
    	actions.add(CinematicAction.moveToConcurrent(sean, new Vector2(9, 12)));
    	actions.add(CinematicAction.moveToConcurrent(sasha, new Vector2(7, 12)));
    	actions.add(CinematicAction.wait(0.4f));
    	
    	actions.add(CinematicAction.stepDialogue(this.dialogue));
    	actions.add(CinematicAction.wait(0.4f));
    	
    	actions.add(CinematicAction.stepDialogue(this.dialogue));
    	
    	actions.add(CinematicAction.moveTo(hik, new Vector2(8, 10)));
    	actions.add(CinematicAction.wait(1.8f));
    	
    	actions.add(CinematicAction.stepDialogue(this.dialogue));
    	actions.add(CinematicAction.wait(0.6f));
    	
    	// Orb glows
    	actions.add(CinematicAction.spawnActor(orb2, new Vector2(8, 10)));
    	actions.add(CinematicAction.wait(0.6f));
    	
    	actions.add(CinematicAction.moveTo(orb2, new Vector2(8, 9)));
    	actions.add(CinematicAction.wait(0.6f));
    	
    	actions.add(CinematicAction.moveTo(orb2, new Vector2(8, 8)));
    	actions.add(CinematicAction.wait(0.6f));
    	
    	actions.add(CinematicAction.spawnActor(orb, new Vector2(8, 8)));
    	actions.add(CinematicAction.despawnActor(orb2));
    	actions.add(CinematicAction.wait(0.6f));
    	
    	actions.add(CinematicAction.stepDialogue(this.dialogue)); // unless can be shown instead
    	
    	actions.add(CinematicAction.despawnActor(orb));

    	actions.add(CinematicAction.moveToConcurrent(sean, new Vector2(9, 11)));
    	actions.add(CinematicAction.moveToConcurrent(sasha, new Vector2(7, 11)));
    	actions.add(CinematicAction.wait(0.2f));
    	
    	actions.add(CinematicAction.moveToConcurrent(sean, new Vector2(9, 10)));
    	actions.add(CinematicAction.moveToConcurrent(sasha, new Vector2(7, 10)));
    	actions.add(CinematicAction.wait(0.2f));
    	
    	actions.add(CinematicAction.stepDialogue(this.dialogue));
    	actions.add(CinematicAction.wait(0.4f));
    	
    	actions.add(CinematicAction.wait(0.4f));

        super.loadCinematicActions();
    }
}
