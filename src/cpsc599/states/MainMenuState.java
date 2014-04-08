package cpsc599.states;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import cpsc599.Main;
import cpsc599.util.Controls;
import cpsc599.util.Logger;

/**
 * A state for managing the main menu.
 */
public class MainMenuState extends State {
    private int time = 0;

    public MainMenuState() {
        Logger.debug("Initializing MainMenuState");
    }

    @Override
    public void render() {
        if (this.spriteBatch == null) {
            Logger.error("init() must be called first.");
            return;
        }

        this.spriteBatch.begin();
        this.drawString("Tale of the Orb", (int)(this.orb.width / 2), 200, StringAlign.CENTER);
        this.drawString("Press start", this.orb.width/2, (this.orb.height/2) + (int)(Math.sin(time * 0.05) * 6), StringAlign.CENTER);
        this.spriteBatch.end();
    }

    @Override
    public void tick(Input input) {
        time++; // Increment the time value.
        if (Controls.isKeyTapped(input, Controls.START)) {
            orb.setState("LEVEL1_VILLAGE");
        }
    }
}
