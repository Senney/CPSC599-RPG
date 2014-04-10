package cpsc599.states;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import cpsc599.Main;
import cpsc599.OrbGame;
import cpsc599.util.Controls;
import cpsc599.util.Logger;
import cpsc599.util.SharedAssets;

/**
 * A state for managing the main menu.
 */
public class MainMenuState extends State {
    private int time = 0;

    private TextureRegion backgroundImage;

    public MainMenuState() {
        Logger.debug("Initializing MainMenuState");
    }

    @Override
    public void init(OrbGame game) {
        super.init(game);

        this.backgroundImage = SharedAssets.loadTextureRegion(SharedAssets.PRIMARY_ASSET_FOLDER + "Title_screen/title_screen.png", 256, 256, 0, 0, false);
    }

    @Override
    public void render() {
        if (this.spriteBatch == null) {
            Logger.error("init() must be called first.");
            return;
        }

        this.spriteBatch.begin();
        this.draw(this.backgroundImage, 0, 0);
        this.drawString("Press start", this.orb.width/2, 40 + (int)(Math.sin(time * 0.05) * 6), StringAlign.CENTER);
        this.spriteBatch.end();
    }

    @Override
    public void tick(Input input) {
        time++; // Increment the time value.
        if (Controls.isKeyTapped(input, Controls.START)) {
            orb.setState("PROLOGUE");
        }
    }
}
