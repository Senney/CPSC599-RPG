package cpsc599.states.Level5;

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

public class Level5AlmightyCinematic extends CinematicState {
	Player hik, sean, sasha;
	Player etien, almighty, cc1, cc2, cc3, etien2;

    public Level5AlmightyCinematic(OrbGame game, Level level, CameraController cameraController, String nextState) {
        super(game, level, cameraController, nextState);
    }

    @Override
    public void init(OrbGame game) {
        super.init(game);
        
        AnimatedSprite hikSprite = new AnimatedSprite("assets/tilesets/primary/CharacterDesign/female.png",
                0, 1, 16, 16, 1, 0.1f);
        AnimatedSprite seanSprite = new AnimatedSprite("assets/tilesets/primary/CharacterDesign/male.png",
                3, 1, 16, 16, 1, 0.1f);
        AnimatedSprite sashaSprite = new AnimatedSprite("assets/tilesets/primary/CharacterDesign/female.png",
                2, 1, 16, 16, 1, 0.1f);
        AnimatedSprite etienSprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Boss/etienne.png",
                0, 0, 16, 16, 1, 0.1f);
        AnimatedSprite etien2Sprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Boss/etienne2.png",
                0, 0, 16, 16, 1, 0.1f);
        AnimatedSprite almightySprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Monsters/giant_cow_cube.png",
        		0, 0, 128, 128, 1, 0.1f);
        AnimatedSprite ccSprite = new AnimatedSprite("assets/tilesets/cowcube.png", 0, 0, 16, 16, 1, 0.1f);
        
        hik = new Player("Hikari", hikSprite, 8, 8, 0);
        sean = new Player("Sean", seanSprite, 9, 9, 0);
        sasha = new Player("Sasha", sashaSprite, 7, 9, 0);
        etien = new Player("Etien", etienSprite, 8, 2, 0);
        etien2 = new Player("Etien", etien2Sprite, 8, 5, 0);
        
        almighty = new Player("Almighty", almightySprite);
        cc1 = new Player("Cow1", ccSprite);
        cc2 = new Player("Cow2", ccSprite);
        cc3 = new Player("Cow3", ccSprite);
        
        this.playerController.getPlayerManager().addPlayer(hik);
        this.playerController.getPlayerManager().addPlayer(sean);
        this.playerController.getPlayerManager().addPlayer(sasha);
        this.playerController.getPlayerManager().addPlayer(etien);
        
        this.dialogue.loadDialogueXML(SharedAssets.CHAPTER_5);
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
    	actions.add(CinematicAction.wait(1.0f));

        actions.add(CinematicAction.moveTo(hik, new Vector2(8, 6), 0.1f));
    	actions.add(CinematicAction.stepDialogue(this.dialogue));
    	actions.add(CinematicAction.wait(0.4f));

        actions.add(CinematicAction.stepDialogue(this.dialogue));
        actions.add(CinematicAction.wait(1.0f));

        actions.add(CinematicAction.stepDialogue(this.dialogue));
        actions.add(CinematicAction.moveTo(hik, new Vector2(8, 8), 0.1f));
        actions.add(CinematicAction.wait(0.4f));
        actions.add(CinematicAction.moveTo(etien, new Vector2(8, 5)));
    	
    	actions.add(CinematicAction.despawnActor(etien));
    	actions.add(CinematicAction.wait(0.2f));
    	actions.add(CinematicAction.spawnActor(etien, new Vector2(8, 5)));
    	actions.add(CinematicAction.wait(0.2f));
    	actions.add(CinematicAction.despawnActor(etien));
    	actions.add(CinematicAction.wait(0.2f));
    	actions.add(CinematicAction.spawnActor(etien, new Vector2(8, 5)));
    	actions.add(CinematicAction.wait(0.2f));
    	actions.add(CinematicAction.despawnActor(etien));
    	actions.add(CinematicAction.wait(0.2f));
    	actions.add(CinematicAction.spawnActor(etien, new Vector2(8, 5)));
    	actions.add(CinematicAction.wait(0.2f));
    	actions.add(CinematicAction.despawnActor(etien));
    	actions.add(CinematicAction.wait(0.2f));
    	actions.add(CinematicAction.spawnActor(etien, new Vector2(8, 5)));
    	actions.add(CinematicAction.wait(0.2f));

    	actions.add(CinematicAction.stepDialogue(this.dialogue));
    	actions.add(CinematicAction.wait(0.2f));
    	
    	actions.add(CinematicAction.spawnActor(etien, new Vector2(8, 5)));
    	actions.add(CinematicAction.despawnActor(etien));
    	actions.add(CinematicAction.wait(0.2f));
    	
    	actions.add(CinematicAction.despawnActor(etien));
    	actions.add(CinematicAction.wait(0.2f));
    	actions.add(CinematicAction.spawnActor(etien, new Vector2(8, 5)));
    	actions.add(CinematicAction.wait(0.8f));
    	actions.add(CinematicAction.despawnActor(etien));
    	actions.add(CinematicAction.wait(0.2f));
    	
    	actions.add(CinematicAction.moveToConcurrent(hik, new Vector2(8, 10), 0.4f));
    	actions.add(CinematicAction.moveToConcurrent(sean, new Vector2(9, 11), 0.4f));
    	actions.add(CinematicAction.moveToConcurrent(sasha, new Vector2(7, 11), 0.4f));

        actions.add(CinematicAction.stepDialogue(this.dialogue));
    	actions.add(CinematicAction.spawnActor(almighty, new Vector2(4, 2)));
        actions.add(CinematicAction.wait(0.8f));
        actions.add(CinematicAction.despawnActor(almighty));
        actions.add(CinematicAction.wait(0.2f));
        actions.add(CinematicAction.spawnActor(almighty, new Vector2(4, 2)));
        actions.add(CinematicAction.wait(0.2f));
        actions.add(CinematicAction.despawnActor(almighty));
        actions.add(CinematicAction.wait(0.2f));
        actions.add(CinematicAction.spawnActor(almighty, new Vector2(4, 2)));
        actions.add(CinematicAction.wait(0.1f));
        actions.add(CinematicAction.despawnActor(almighty));
        actions.add(CinematicAction.wait(0.1f));
        actions.add(CinematicAction.spawnActor(etien2, new Vector2(8, 5)));
    	
    	actions.add(CinematicAction.spawnActor(cc1, new Vector2(4, 9)));
    	actions.add(CinematicAction.wait(0.2f));
    	actions.add(CinematicAction.spawnActor(cc2, new Vector2(12, 7)));
    	actions.add(CinematicAction.wait(0.2f));
    	actions.add(CinematicAction.spawnActor(cc3, new Vector2(2, 5)));
    	actions.add(CinematicAction.wait(0.4f));
    	
    	actions.add(CinematicAction.stepDialogue(this.dialogue));
    	actions.add(CinematicAction.stepDialogue(this.dialogue));
        actions.add(CinematicAction.stepDialogue(this.dialogue));
        actions.add(CinematicAction.stepDialogue(this.dialogue));
    	actions.add(CinematicAction.wait(0.4f));
    	
    	actions.add(CinematicAction.wait(0.4f));

        super.loadCinematicActions();
    }
}
