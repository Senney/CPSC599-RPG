package cpsc599.states.Level4;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import cpsc599.OrbGame;
import cpsc599.assets.CinematicAction;
import cpsc599.assets.Level;
import cpsc599.assets.Player;
import cpsc599.controller.CameraController;
import cpsc599.states.CinematicState;
import cpsc599.util.SharedAssets;

public class Level4Finale extends CinematicState{
    Player hik, seen, sash;

    public Level4Finale(OrbGame game, Level level, CameraController cameraController, String nextState) {
        super(game, level, cameraController, nextState);
    }

    @Override
    public void init(OrbGame game) {
        super.init(game);

        hik = new Player("Hikari", SharedAssets.hikariSprite, 17, 4, 0);
        seen = new Player("Sean",SharedAssets.seanSprite, 16, 4, 0);
        sash = new Player("Sasha",SharedAssets.sashaSprite, 18, 4, 0);

        //this.playerController.getPlayerManager().addPlayer(hik);
        //this.playerController.getPlayerManager().addPlayer(ren);


        this.dialogue.loadDialogueXML(SharedAssets.CHAPTER_4);
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
        actions.add(CinematicAction.spawnActor(hik, new Vector2(17, 4)));
        actions.add(CinematicAction.spawnActor(seen, new Vector2(16,4)));
        actions.add(CinematicAction.spawnActor(sash, new Vector2(18,4)));

        actions.add(CinematicAction.panCamera(new Vector2(8, 12), new Vector2(13, 7), this.cameraController));

        actions.add(CinematicAction.stepDialogue(dialogue));
        actions.add(CinematicAction.stepDialogue(dialogue));
        actions.add(CinematicAction.stepDialogue(dialogue));

        actions.add(CinematicAction.despawnActor(hik));
        actions.add(CinematicAction.despawnActor(seen));
        actions.add(CinematicAction.despawnActor(sash));

        super.loadCinematicActions();
    }
}
