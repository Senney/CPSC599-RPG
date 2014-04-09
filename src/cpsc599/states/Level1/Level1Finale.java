package cpsc599.states.Level1;

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

public class Level1Finale extends CinematicState{
    Player hik, seen, sash;

    public Level1Finale(OrbGame game, Level level, CameraController cameraController, String nextState) {
        super(game, level, cameraController, nextState);
    }

    @Override
    public void init(OrbGame game) {
        super.init(game);

        AnimatedSprite hikSprite = new AnimatedSprite("assets/tilesets/primary/CharacterDesign/female.png",
                0, 2, 16, 16, 1, 0.1f);

        hik = new Player("Hikari", SharedAssets.hikariSprite, 5, 7, 0);
        seen = new Player("Sean",SharedAssets.seanSprite, 7, 8, 0);
        sash = new Player("Sasha",SharedAssets.sashaSprite, 7, 6, 0);

        //this.playerController.getPlayerManager().addPlayer(hik);
        //this.playerController.getPlayerManager().addPlayer(ren);


        this.dialogue.loadDialogueXML(SharedAssets.CHAPTER_1);
        this.dialogue.setDialogueTag("p4");
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
        // Initial dialogue interaction.
        actions.add(CinematicAction.spawnActor(hik, new Vector2(5, 7)));
        actions.add(CinematicAction.spawnActor(seen, new Vector2(7,8)));
        actions.add(CinematicAction.spawnActor(sash, new Vector2(7,6)));

        actions.add(CinematicAction.stepDialogue(dialogue));
        actions.add(CinematicAction.stepDialogue(dialogue));

        actions.add(CinematicAction.moveToConcurrent(hik, new Vector2(7, 18)));
        actions.add(CinematicAction.moveToConcurrent(seen, new Vector2(8, 18)));
        actions.add(CinematicAction.moveToConcurrent(sash, new Vector2(9, 18)));

        actions.add(CinematicAction.despawnActor(hik));
        actions.add(CinematicAction.despawnActor(seen));
        actions.add(CinematicAction.despawnActor(sash));

        actions.add(CinematicAction.stepDialogue(dialogue));

        /*actions.add(CinematicAction.moveToConcurrent(e1, new Vector2(5, 6)));
        actions.add(CinematicAction.moveToConcurrent(e2, new Vector2(5, 8)));
        actions.add(CinematicAction.moveToConcurrent(ren, new Vector2(5, 7)));

        actions.add(CinematicAction.spawnActor(hik, new Vector2(0,7)));

        actions.add(CinematicAction.stepDialogue(dialogue));

        actions.add(CinematicAction.moveToConcurrent(ren, new Vector2(4, 8)));
        actions.add(CinematicAction.wait(0.001f));

        actions.add(CinematicAction.moveToConcurrent(e2, new Vector2(5, 15)));
        actions.add(CinematicAction.moveToConcurrent(ren, new Vector2(4, 15)));
        actions.add(CinematicAction.moveToConcurrent(hik, new Vector2(2, 7)));

        actions.add(CinematicAction.despawnActor(e2));
        actions.add(CinematicAction.despawnActor(ren));

        actions.add(CinematicAction.spawnActor(e3, new Vector2(15, 7)));
        actions.add(CinematicAction.spawnActor(e4, new Vector2(15, 8)));
        actions.add(CinematicAction.spawnActor(e5, new Vector2(15, 9)));

        actions.add(CinematicAction.stepDialogue(dialogue));

        actions.add(CinematicAction.moveToConcurrent(e3, new Vector2(13, 6)));
        actions.add(CinematicAction.moveToConcurrent(e4, new Vector2(13, 2)));
        actions.add(CinematicAction.moveToConcurrent(e5, new Vector2(10, 8)));*/

        super.loadCinematicActions();
    }
}
