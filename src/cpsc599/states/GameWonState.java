package cpsc599.states;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import cpsc599.Main;
import cpsc599.OrbGame;
import cpsc599.util.SharedAssets;

public class GameWonState extends State {
    BitmapFont font;

    @Override
    public void init(OrbGame game) {
        super.init(game);

        font = SharedAssets.font_14;
    }

    @Override
    public void render() {
        spriteBatch.begin();

        font.drawWrapped(spriteBatch, "Congratulations on beating Tale of the Orb! You've rescued Ren and defeated the"+
        " evil Cow Cube Cult! You're a true hero.\n\nWatch out for Tale of the Orb 2, DLC edition! Coming as soon as we're" +
                " bought out by EA games.", 15, Main.GAME_HEIGHT - 5, Main.GAME_WIDTH - 30);

        spriteBatch.end();
    }

    @Override
    public void tick(Input input) {

    }
}
