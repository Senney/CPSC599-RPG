package cpsc599.managers;

import cpsc599.util.Logger;

/**
 * Created by srheintz on 07/02/14.
 */
public class StateManager {
    public static enum STATES {
        MAIN_SCREEN,
        GAME_PLAYING
    };

    private STATES state;

    public void setState(StateManager.STATES state) {
        Logger.debug("Setting state to: " + state);
        this.state = state;
    }
    public STATES getState() {
        return this.state;
    }
}
