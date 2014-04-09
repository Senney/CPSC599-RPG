package cpsc599.states.Level4;

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

public class Level4FieldCinematic extends CinematicState{
    Player hik, seen, sash, e1, e2;

    public Level4FieldCinematic(OrbGame game, Level level, CameraController cameraController, String nextState) {
        super(game, level, cameraController, nextState);
    }

    @Override
    public void init(OrbGame game) {
        super.init(game);

        hik = new Player("Hikari", SharedAssets.hikariSprite, 4, 23, 0);
        seen = new Player("Sean",SharedAssets.seanSprite, 3, 23, 0);
        sash = new Player("Sasha",SharedAssets.sashaSprite, 5, 23, 0);

        AnimatedSprite enemySprite1 = new AnimatedSprite("assets/tilesets/primary/Enemy/Monsters/snowman.png",
                0, 0, 16, 16, 1, 0.1f);
        e1 = new Player("Snowy", enemySprite1,8, 8, 0);
        e2 = new Player("Snowy", enemySprite1,9, 8, 0);

        //this.playerController.getPlayerManager().addPlayer(hik);
        //this.playerController.getPlayerManager().addPlayer(ren);


        this.dialogue.loadDialogueXML(SharedAssets.CHAPTER_4);
        this.dialogue.setDialogueTag("p1");
        //this.dialogue.setVisibility(true);
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

        actions.add(CinematicAction.panCamera(new Vector2(8, 3), new Vector2(6, 17), this.cameraController));
        actions.add(CinematicAction.spawnActor(hik, new Vector2(4, 23)));
        actions.add(CinematicAction.spawnActor(seen, new Vector2(3,23)));
        actions.add(CinematicAction.spawnActor(sash, new Vector2(5,23)));
        actions.add(CinematicAction.spawnActor(e1, new Vector2(8, 8)));

        actions.add(CinematicAction.moveToConcurrent(hik, new Vector2(6, 18)));
        actions.add(CinematicAction.moveToConcurrent(seen, new Vector2(5, 18)));
        actions.add(CinematicAction.moveToConcurrent(sash, new Vector2(7, 18)));
        actions.add(CinematicAction.moveToConcurrent(e1, new Vector2(8, 14)));

        actions.add(CinematicAction.stepDialogue(dialogue));
        actions.add(CinematicAction.stepDialogue(dialogue));
        actions.add(CinematicAction.stepDialogue(dialogue));
        actions.add(CinematicAction.stepDialogue(dialogue));
        actions.add(CinematicAction.stepDialogue(dialogue));

        actions.add(CinematicAction.moveToConcurrent(e1, new Vector2(9, 8)));
        actions.add(CinematicAction.spawnActor(e2, new Vector2(8, 8)));
        actions.add(CinematicAction.moveToConcurrent(e1, new Vector2(4, 14)));
        actions.add(CinematicAction.moveToConcurrent(e2, new Vector2(10, 14)));

        actions.add(CinematicAction.stepDialogue(dialogue));

        super.loadCinematicActions();
    }
}
