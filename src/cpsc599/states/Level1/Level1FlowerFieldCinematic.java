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

public class Level1FlowerFieldCinematic extends CinematicState {
    Player hik, ren, e1, e2;

    public Level1FlowerFieldCinematic(OrbGame game, Level level, CameraController cameraController, String nextState) {
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

        hik = new Player("Hikari", hikSprite, 5, 7, 0);
        ren = new Player("Ren", renSprite, 6, 7, 0);
        e1 = new Player("Enemy1", enemySprite1, 15, 6, 0);
        e2 = new Player("Enemy2", enemySprite2, 15, 8, 0);

        this.playerController.getPlayerManager().addPlayer(hik);
        this.playerController.getPlayerManager().addPlayer(ren);


        this.dialogue.loadDialogueXML("src/cpsc599/assets/Script/chapter1.xml");
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

        actions.add(CinematicAction.wait(0.8f));


        super.loadCinematicActions();
    }
}
