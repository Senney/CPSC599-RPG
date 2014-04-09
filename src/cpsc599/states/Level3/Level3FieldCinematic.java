package cpsc599.states.Level3;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import cpsc599.OrbGame;
import cpsc599.assets.AnimatedSprite;
import cpsc599.assets.CinematicAction;
import cpsc599.assets.Level;
import cpsc599.assets.Player;
import cpsc599.controller.CameraController;
import cpsc599.states.CinematicState;
import cpsc599.util.Controls;
import cpsc599.util.SharedAssets;

public class Level3FieldCinematic extends CinematicState {
    Player hik, sean, sasha;
    Player cc1, cc2, cc3;

    public Level3FieldCinematic(OrbGame game, Level level, CameraController cameraController, String nextState) {
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

        hik = new Player("Hikari", hikSprite, 1, 6, 0);
        sean = new Player("Sean", seanSprite, 1, 7, 0);
        sasha = new Player("Sasha", sashaSprite, 1, 8, 0);

        cc1 = new Player("Cow1", ccSprite);
        cc2 = new Player("Cow2", ccSprite);
        cc3 = new Player("Cow3", ccSprite);

        this.cameraController.set(0, 15);

        this.playerController.getPlayerManager().addPlayer(hik);
        this.playerController.getPlayerManager().addPlayer(sean);
        this.playerController.getPlayerManager().addPlayer(sasha);

        this.dialogue.loadDialogueXML(SharedAssets.CHAPTER_3);
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

    }

    @Override
    protected void loadCinematicActions() {
        // actions.add(CinematicAction.FUNCTION(PARAMS));
        actions.add(CinematicAction.stepDialogue(this.dialogue));
        actions.add(CinematicAction.stepDialogue(this.dialogue));
        actions.add(CinematicAction.stepDialogue(this.dialogue));
        actions.add(CinematicAction.panCamera(new Vector2(0, 15), new Vector2(25, 10), this.cameraController));
        actions.add(CinematicAction.wait(0.4f));
        actions.add(CinematicAction.spawnActor(cc1, new Vector2(25, 10)));
        actions.add(CinematicAction.spawnActor(cc2, new Vector2(25, 11)));
        actions.add(CinematicAction.spawnActor(cc3, new Vector2(24, 13)));
        actions.add(CinematicAction.stepDialogue(this.dialogue));
        actions.add(CinematicAction.moveToConcurrent(cc1, new Vector2(19, 9)));
        actions.add(CinematicAction.moveToConcurrent(cc2, new Vector2(20, 10)));
        actions.add(CinematicAction.moveToConcurrent(cc3, new Vector2(19, 13)));
        actions.add(CinematicAction.panCamera(new Vector2(25, 10), new Vector2(0, 15), this.cameraController));
        actions.add(CinematicAction.stepDialogue(this.dialogue));

        super.loadCinematicActions();
    }
}
