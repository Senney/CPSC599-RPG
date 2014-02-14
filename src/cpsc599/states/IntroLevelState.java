package cpsc599.states;

import cpsc599.OrbGame;
import cpsc599.managers.LevelManager;

/**
 * Created by KRUSHER40 on 2/14/14.
 */
public class IntroLevelState extends LevelState {
    public IntroLevelState(OrbGame game, LevelManager manager) {
        super(game);
        super.setLevel(manager.setLevel(0));
    }

    @Override
    public void render() {
        super.drawLevel();
    }

    @Override
    public void tick() {

    }
}
