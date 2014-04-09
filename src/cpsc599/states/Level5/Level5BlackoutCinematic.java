package cpsc599.states.Level5;

import com.badlogic.gdx.Input;

import cpsc599.OrbGame;
import cpsc599.assets.CinematicAction;
import cpsc599.assets.Level;
import cpsc599.controller.CameraController;
import cpsc599.states.CinematicState;
import cpsc599.util.SharedAssets;

public class Level5BlackoutCinematic extends CinematicState {
    public Level5BlackoutCinematic(OrbGame game, Level level, CameraController cameraController, String nextState) {
        super(game, level, cameraController, nextState);
    }

    @Override
    public void init(OrbGame game) {
        super.init(game);
        
        this.dialogue.loadDialogueXML(SharedAssets.CHAPTER_5);
        this.dialogue.setDialogueTag("p2");
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
    	actions.add(CinematicAction.wait(0.2f));
    	
    	actions.add(CinematicAction.stepDialogue(this.dialogue));
    	actions.add(CinematicAction.stepDialogue(this.dialogue));
    	actions.add(CinematicAction.stepDialogue(this.dialogue));
    	actions.add(CinematicAction.wait(0.2f));
    	
    	actions.add(CinematicAction.stepDialogue(this.dialogue));
    	actions.add(CinematicAction.stepDialogue(this.dialogue));
    	actions.add(CinematicAction.wait(0.6f));
    	
    	actions.add(CinematicAction.stepDialogue(this.dialogue));
    	actions.add(CinematicAction.stepDialogue(this.dialogue));
    	actions.add(CinematicAction.wait(0.4f));
    	
    	actions.add(CinematicAction.wait(0.4f));

        super.loadCinematicActions();
    }
}
