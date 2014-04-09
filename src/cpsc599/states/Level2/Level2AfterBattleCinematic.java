package cpsc599.states.Level2;

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

public class Level2AfterBattleCinematic extends CinematicState {
	Player hik, sean, sasha;
    Player cc1, cc2, cc3;

    public Level2AfterBattleCinematic(OrbGame game, Level level, CameraController cameraController, String nextState) {
        super(game, level, cameraController, nextState);
    }

    @Override
    public void init(OrbGame game) {
        super.init(game);

        AnimatedSprite hikSprite = new AnimatedSprite("assets/tilesets/primary/CharacterDesign/female.png",
                0, 2, 16, 16, 1, 0.1f);
        AnimatedSprite seanSprite = new AnimatedSprite("assets/tilesets/primary/CharacterDesign/male.png",
                3, 3, 16, 16, 1, 0.1f);
        AnimatedSprite sashaSprite = new AnimatedSprite("assets/tilesets/primary/CharacterDesign/female.png",
                2, 2, 16, 16, 1, 0.1f);
        AnimatedSprite ccSprite = new AnimatedSprite("assets/tilesets/cowcube.png", 0, 0, 16, 16, 1, 0.1f);

        hik = new Player("Hikari", hikSprite, 12, 9, 0);
        sean = new Player("Sean", seanSprite, 11, 8, 0);
        sasha = new Player("Sasha", sashaSprite, 11, 10, 0);

        cc1 = new Player("Cow1", ccSprite, 14, 8, 0);
        cc2 = new Player("Cow2", ccSprite, 15, 9, 0);
        cc3 = new Player("Cow3", ccSprite, 15, 8, 0);

        this.cameraController.set(25, 5);

        this.playerController.getPlayerManager().addPlayer(hik);
        this.playerController.getPlayerManager().addPlayer(sean);
        this.playerController.getPlayerManager().addPlayer(sasha);
        this.playerController.getPlayerManager().addPlayer(cc1);
        this.playerController.getPlayerManager().addPlayer(cc2);
        this.playerController.getPlayerManager().addPlayer(cc3);

        this.dialogue.loadDialogueXML(SharedAssets.CHAPTER_2);
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
    	actions.add(CinematicAction.stepDialogue(this.dialogue));
        actions.add(CinematicAction.stepDialogue(this.dialogue));
        actions.add(CinematicAction.stepDialogue(this.dialogue));
        actions.add(CinematicAction.stepDialogue(this.dialogue));
        actions.add(CinematicAction.stepDialogue(this.dialogue));
        actions.add(CinematicAction.stepDialogue(this.dialogue));
        actions.add(CinematicAction.stepDialogue(this.dialogue));
        actions.add(CinematicAction.wait(0.2f));
        
        actions.add(CinematicAction.moveTo(hik, new Vector2(13, 9)));
        actions.add(CinematicAction.stepDialogue(this.dialogue));
        actions.add(CinematicAction.wait(0.2f));
        
        actions.add(CinematicAction.stepDialogue(this.dialogue));
        actions.add(CinematicAction.wait(0.2f));
        
        actions.add(CinematicAction.stepDialogue(this.dialogue));
        actions.add(CinematicAction.stepDialogue(this.dialogue));
        actions.add(CinematicAction.stepDialogue(this.dialogue));
        actions.add(CinematicAction.stepDialogue(this.dialogue));
        actions.add(CinematicAction.stepDialogue(this.dialogue));
        
        actions.add(CinematicAction.moveToConcurrent(hik, new Vector2(14, 11)));
        actions.add(CinematicAction.moveToConcurrent(sean, new Vector2(13, 10)));
        actions.add(CinematicAction.moveToConcurrent(sasha, new Vector2(13, 11)));
        
        actions.add(CinematicAction.stepDialogue(this.dialogue));
        
        actions.add(CinematicAction.moveToConcurrent(hik, new Vector2(14, 16)));
        actions.add(CinematicAction.moveToConcurrent(sean, new Vector2(13, 15)));
        actions.add(CinematicAction.moveToConcurrent(sasha, new Vector2(13, 16)));
        actions.add(CinematicAction.wait(0.5f));
        
        actions.add(CinematicAction.stepDialogue(this.dialogue));
        actions.add(CinematicAction.wait(1.0f));
        
        actions.add(CinematicAction.wait(0.4f));
        
    	super.loadCinematicActions();
    }
}
