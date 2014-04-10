package cpsc599.states;

import com.badlogic.gdx.Input;
import cpsc599.util.Controls;
import cpsc599.util.Logger;

//Game over state
public class GameOverState extends State{
    private int time = 0;
    public String level;

    public GameOverState() {
        Logger.debug("Initializing MainMenuState");
    }

    @Override
    public void render() {
        if (this.spriteBatch == null) {
            Logger.error("init() must be called first.");
            return;
        }
        this.spriteBatch.begin();
        this.drawString("Game Over", (int)(this.orb.width / 2), 20 + (int)(Math.sin(time * 0.07) * 10), StringAlign.CENTER);
        this.drawString("You have died", this.orb.width/2, this.orb.height/2, StringAlign.CENTER);
        this.spriteBatch.end();
    }

    @Override
    public void tick(Input input) {
        time++; // Increment the time value.
        if (Controls.isKeyTapped(input, Controls.START)) {
            orb.setState(level);
        }
    }
}
