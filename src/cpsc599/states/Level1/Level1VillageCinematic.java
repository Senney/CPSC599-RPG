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
import cpsc599.util.Controls;

public class Level1VillageCinematic extends CinematicState {
    private Player cc1,cc2,p1,p2;

    public Level1VillageCinematic(OrbGame game, Level level, CameraController cameraController) {
        super(game, level, cameraController);
    }

    @Override
    public void init(OrbGame game) {
        super.init(game);

        this.dialogue.loadDialogueXML("src/cpsc599/assets/Script/chapter1.xml");
        this.dialogue.setDialogueTag("p1");
        this.dialogue.setVisibility(true);

        AnimatedSprite sp = new AnimatedSprite("assets/tilesets/cowcube.png", 0, 0, 16, 16, 1, 0.1f);
        AnimatedSprite m1 = new AnimatedSprite("assets/tilesets/primary/CharacterDesign/male.png",
                3, 3, 16, 16, 1, 0.1f);
        AnimatedSprite m2 = new AnimatedSprite("assets/tilesets/primary/CharacterDesign/male.png",
                2, 3, 16, 16, 1, 0.1f);

        cc1 = new Player("Cow Cube", sp, 11, 11, 0);
        cc2 = new Player("Cow Cube", sp, 11, 14, 0);
        p1 = new Player("Villager 1", m1, 6, 8, 0);
        p2 = new Player("Villager 2", m2, 7, 8, 0);
        this.playerController.getPlayerManager().addPlayer(cc1);
        this.playerController.getPlayerManager().addPlayer(cc2);
        this.playerController.getPlayerManager().addPlayer(p1);
        this.playerController.getPlayerManager().addPlayer(p2);
    }

    @Override
    public void render() {
        super.render();

        this.dialogue.render(this.overlayLayer);
    }

    @Override
    public void tick(Input input) {
        if (Controls.isKeyTapped(input, Controls.START)) {
            this.orb.setState("LEVEL0");
        }

        if (this.dialogue.isVisible()) {
            if (Controls.isKeyTapped(input, Controls.A_BUTTON)) {
                if (this.dialogue.checkTextLeft() && this.dialogue.isVisible()) {
                    this.dialogue.loadTextRemains();
                }
                else {
                    this.dialogue.setVisibility(false);
                }
            }
            return;
        }

        super.tick(input);

        if (actions.size() == 0) {
            this.orb.setState("LEVEL0");
        }
    }

    @Override
    protected void loadCinematicActions() {
        actions.add(CinematicAction.stepDialogue(dialogue));
        actions.add(CinematicAction.stepDialogue(dialogue));
        actions.add(CinematicAction.stepDialogue(dialogue));
        actions.add(CinematicAction.stepDialogue(dialogue));
        actions.add(CinematicAction.stepDialogue(dialogue));
        actions.add(CinematicAction.moveTo(cc1, new Vector2(9, 10), 0.5f));
        actions.add(CinematicAction.moveTo(cc2, new Vector2(7, 10), 0.5f));
        actions.add(CinematicAction.stepDialogue(dialogue));
        actions.add(CinematicAction.stepDialogue(dialogue));
        actions.add(CinematicAction.moveTo(p1, new Vector2(11, 6), 0.25f));
        actions.add(CinematicAction.moveTo(p2, new Vector2(13, 6), 0.25f));
        actions.add(CinematicAction.despawnActor(p1));
        actions.add(CinematicAction.despawnActor(p2));
        actions.add(CinematicAction.stepDialogue(dialogue));
        actions.add(CinematicAction.wait(1.0f));

        super.loadCinematicActions();
    }
}

