package cpsc599.states;

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
            Logger.error("MainMenuState::render - init() must be called first.");
            return;
        }

        this.spriteBatch.begin();
        this.drawString("Tale of the Orb", (int)(this.orb.width / 2), 20, StringAlign.CENTER);
        this.spriteBatch.end();
    }

    @Override
    public void tick() {
        time++; // Increment the time value.
    }
}
