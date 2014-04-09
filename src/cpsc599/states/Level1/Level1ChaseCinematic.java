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

public class Level1ChaseCinematic extends CinematicState{
    Player hik, ren, e1, e2, e3, e4, e5, e6;

    public Level1ChaseCinematic(OrbGame game, Level level, CameraController cameraController, String nextState) {
        super(game, level, cameraController, nextState);
    }

    @Override
    public void init(OrbGame game) {
        super.init(game);

        AnimatedSprite renSprite = new AnimatedSprite("assets/tilesets/primary/CharacterDesign/male.png",
                0, 3, 16, 16, 1, 0.1f);
        AnimatedSprite hikSprite = new AnimatedSprite("assets/tilesets/primary/CharacterDesign/female.png",
                0, 2, 16, 16, 1, 0.1f);
        AnimatedSprite enemySprite1 = new AnimatedSprite("assets/tilesets/primary/Enemy/Human/human2.png",
                0, 0, 16, 16, 1, 0.1f);
        AnimatedSprite enemySprite2 = new AnimatedSprite("assets/tilesets/primary/Enemy/Human/human1.png",
                0, 0, 16, 16, 1, 0.1f);
        AnimatedSprite enemySprite3 = new AnimatedSprite("assets/tilesets/primary/Enemy/Human/human4.png",
                0, 0, 16, 16, 1, 0.1f);
        AnimatedSprite enemySprite4 = new AnimatedSprite("assets/tilesets/primary/Enemy/Monster/enemy9.png",
                0, 0, 16, 16, 1, 0.1f);
        AnimatedSprite enemySprite5 = new AnimatedSprite("assets/tilesets/primary/Enemy/Human/human5.png",
                0, 0, 16, 16, 1, 0.1f);

        hik = new Player("Hikari", hikSprite, 0, 7, 0);
        ren = new Player("Ren", renSprite, 0, 7, 0);
        e1 = new Player("Enemy1", enemySprite1, 0, 6, 0);
        e2 = new Player("Enemy2", enemySprite2, 0, 8, 0);
        e3 = new Player("Enemy3", enemySprite3, 15, 7, 0);
        e4 = new Player("Enemy4", enemySprite4, 15, 8, 0);
        e5 = new Player("Enemy5", enemySprite5, 15, 9, 0);


        //this.playerController.getPlayerManager().addPlayer(hik);
        //this.playerController.getPlayerManager().addPlayer(ren);


        this.dialogue.loadDialogueXML(SharedAssets.CHAPTER_1);
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
        // Initial dialogue interaction.
        actions.add(CinematicAction.spawnActor(e1, new Vector2(0,6)));
        actions.add(CinematicAction.spawnActor(e2, new Vector2(0,8)));
        actions.add(CinematicAction.spawnActor(ren, new Vector2(0,7)));

        actions.add(CinematicAction.moveToConcurrent(e1, new Vector2(5, 6)));
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

        actions.add(CinematicAction.moveToConcurrent(e3, new Vector2(0,6)));

        /*actions.add(CinematicAction.stepDialogue(dialogue));
        actions.add(CinematicAction.stepDialogue(dialogue));
        actions.add(CinematicAction.stepDialogue(dialogue));
        actions.add(CinematicAction.stepDialogue(dialogue));
        actions.add(CinematicAction.stepDialogue(dialogue));
        actions.add(CinematicAction.stepDialogue(dialogue));
        actions.add(CinematicAction.stepDialogue(dialogue));
        actions.add(CinematicAction.stepDialogue(dialogue));
        actions.add(CinematicAction.stepDialogue(dialogue));
        actions.add(CinematicAction.stepDialogue(dialogue));
        actions.add(CinematicAction.stepDialogue(dialogue));
        actions.add(CinematicAction.stepDialogue(dialogue));
        actions.add(CinematicAction.wait(1.2f));

        // Spawn two enemies.
        actions.add(CinematicAction.spawnActor(e1, new Vector2(15, 6)));
        actions.add(CinematicAction.spawnActor(e2, new Vector2(15, 8)));
        actions.add(CinematicAction.wait(0.4f));
        actions.add(CinematicAction.stepDialogue(dialogue));
        actions.add(CinematicAction.stepDialogue(dialogue));
        actions.add(CinematicAction.moveToConcurrent(e1, new Vector2(6, 6), 0.3f));
        actions.add(CinematicAction.moveToConcurrent(e2, new Vector2(6, 8), 0.3f));
        actions.add(CinematicAction.stepDialogue(dialogue));
        actions.add(CinematicAction.stepDialogue(dialogue));
        actions.add(CinematicAction.stepDialogue(dialogue));
        actions.add(CinematicAction.stepDialogue(dialogue));
        actions.add(CinematicAction.stepDialogue(dialogue));

        actions.add(CinematicAction.moveToConcurrent(e1, new Vector2(12, 6), 0.3f));
        actions.add(CinematicAction.moveToConcurrent(e2, new Vector2(12, 8), 0.3f));
        actions.add(CinematicAction.moveToConcurrent(ren, new Vector2(12, 7), 0.3f));

        actions.add(CinematicAction.stepDialogue(dialogue));
        actions.add(CinematicAction.stepDialogue(dialogue));
        actions.add(CinematicAction.moveToConcurrent(e1, new Vector2(15, 6), 0.3f));
        actions.add(CinematicAction.moveToConcurrent(e2, new Vector2(15, 8), 0.3f));
        actions.add(CinematicAction.moveToConcurrent(ren, new Vector2(15, 7), 0.3f));
        actions.add(CinematicAction.moveToConcurrent(hik, new Vector2(9, 7), 0.3f));

        actions.add(CinematicAction.despawnActor(e1));
        actions.add(CinematicAction.despawnActor(e2));
        actions.add(CinematicAction.despawnActor(ren));

        actions.add(CinematicAction.moveToConcurrent(hik, new Vector2(15, 7), 0.3f));
        actions.add(CinematicAction.despawnActor(hik));

        actions.add(CinematicAction.wait(0.8f));*/


        super.loadCinematicActions();
    }
}
