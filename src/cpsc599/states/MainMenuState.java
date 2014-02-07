package cpsc599.states;

/**
 * Created by srheintz on 07/02/14.
 */
public class MainMenuState extends State {
    private int time = 0;

    @Override
    public void render() {
        this.spriteBatch.begin();

        this.drawString("Tale of the Orb", (int)(this.orb.width / 2), 20, StringAlign.CENTER);

        this.spriteBatch.end();
    }

    @Override
    public void tick() {
        time++; // Increment the time value.
    }
}
