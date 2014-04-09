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
import cpsc599.util.SharedAssets;

public class Level3Finale extends CinematicState{
    Player hik, seen, sash;

    public Level3Finale(OrbGame game, Level level, CameraController cameraController, String nextState) {
        super(game, level, cameraController, nextState);
    }

    @Override
    public void init(OrbGame game) {
        super.init(game);

        hik = new Player("Hikari", SharedAssets.hikariSprite, 19, 9, 0);
        seen = new Player("Sean",SharedAssets.seanSprite, 20, 9, 0);
        sash = new Player("Sasha",SharedAssets.sashaSprite, 21, 9, 0);

        //this.playerController.getPlayerManager().addPlayer(hik);
        //this.playerController.getPlayerManager().addPlayer(ren);


        this.dialogue.loadDialogueXML(SharedAssets.CHAPTER_3);
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
        // Initial dialogue interaction.
        actions.add(CinematicAction.spawnActor(hik, new Vector2(19, 9)));
        actions.add(CinematicAction.spawnActor(seen, new Vector2(20,9)));
        actions.add(CinematicAction.spawnActor(sash, new Vector2(21,9)));
        actions.add(CinematicAction.panCamera(new Vector2(1, 1), new Vector2(18, 10), this.cameraController));

        actions.add(CinematicAction.stepDialogue(dialogue));
        actions.add(CinematicAction.stepDialogue(dialogue));

        actions.add(CinematicAction.moveToConcurrent(hik, new Vector2(25, 10)));
        actions.add(CinematicAction.moveToConcurrent(seen, new Vector2(25, 11)));
        actions.add(CinematicAction.moveToConcurrent(sash, new Vector2(24, 11)));

        actions.add(CinematicAction.despawnActor(hik));
        actions.add(CinematicAction.despawnActor(seen));
        actions.add(CinematicAction.despawnActor(sash));

        super.loadCinematicActions();
    }
}
