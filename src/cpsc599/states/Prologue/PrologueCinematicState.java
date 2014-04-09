package cpsc599.states.Prologue;

import com.badlogic.gdx.Input;

import cpsc599.OrbGame;
import cpsc599.assets.AnimatedSprite;
import cpsc599.assets.CinematicAction;
import cpsc599.assets.Level;
import cpsc599.assets.Player;
import cpsc599.controller.CameraController;
import cpsc599.states.CinematicState;
import cpsc599.util.SharedAssets;

public class PrologueCinematicState extends CinematicState {
	Player orb2;
	
    public PrologueCinematicState(OrbGame game, Level level, CameraController cameraController, String nextState) {
        super(game, level, cameraController, nextState);
    }

    @Override
    public void init(OrbGame game) {
        super.init(game);
        
        AnimatedSprite orb2Sprite = new AnimatedSprite("assets/tilesets/primary/Items/orb2.png", 0, 0, 16, 16, 1, 0.1f);
        
        orb2 = new Player("Orb2", orb2Sprite, 7, 5, 0);
        
        this.playerController.getPlayerManager().addPlayer(orb2);
        
        this.dialogue.loadDialogueXML(SharedAssets.PROLOGUE);
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
    	actions.add(CinematicAction.wait(0.4f));

        super.loadCinematicActions();
    }
}
