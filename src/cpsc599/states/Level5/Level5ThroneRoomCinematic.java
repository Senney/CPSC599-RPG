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

public class Level5ThroneRoomCinematic extends CinematicState {
	Player hik, sean, sasha;
	Player etien;
	
    public Level5ThroneRoomCinematic(OrbGame game, Level level, CameraController cameraController, String nextState) {
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
        AnimatedSprite etienSprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Boss/etienne.png",
                0, 0, 16, 16, 1, 0.1f);
        
        hik = new Player("Hikari", hikSprite, 8, 12, 0);
        sean = new Player("Sean", seanSprite, 9, 13, 0);
        sasha = new Player("Sasha", sashaSprite, 7, 13, 0);
        etien = new Player("Etien", etienSprite, 8, 2, 0);
        
        this.playerController.getPlayerManager().addPlayer(hik);
        this.playerController.getPlayerManager().addPlayer(sean);
        this.playerController.getPlayerManager().addPlayer(sasha);
        this.playerController.getPlayerManager().addPlayer(etien);
        
        this.dialogue.loadDialogueXML(SharedAssets.CHAPTER_5);
        this.dialogue.setDialogueTag("p3");
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
    	actions.add(CinematicAction.moveTo(hik, new Vector2(8, 9), 0.1f));
    	
    	actions.add(CinematicAction.stepDialogue(this.dialogue));
    	actions.add(CinematicAction.wait(1.0f));
    	
    	actions.add(CinematicAction.stepDialogue(this.dialogue));
    	actions.add(CinematicAction.moveTo(hik, new Vector2(8, 5), 0.1f));
    	actions.add(CinematicAction.wait(0.4f));

        super.loadCinematicActions();
    }
}
