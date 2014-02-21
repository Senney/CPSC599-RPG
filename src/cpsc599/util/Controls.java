package cpsc599.util;

import com.badlogic.gdx.Input;

import java.util.HashMap;

/**
 * Static variables related to various control elements for the game.
 */
public class Controls {

    /**
     * Directional controls.
     */
    public static int UP = Input.Keys.UP;
    public static int LEFT = Input.Keys.LEFT;
    public static int RIGHT = Input.Keys.RIGHT;
    public static int DOWN = Input.Keys.DOWN;

    /**
     * Activation buttons. NES had 2 buttons, so do we.
     */
    public static int A_BUTTON = Input.Keys.Z;
    public static int B_BUTTON = Input.Keys.X;

    /**
     * Interaction buttons.
     */
    public static int START = Input.Keys.ENTER;
    public static int SELECT = Input.Keys.SPACE;

    private static HashMap<Integer, Boolean> pressed = new HashMap<Integer, Boolean>();
    public static boolean isKeyTapped(Input inew, int key) {
        boolean state = inew.isKeyPressed(key);
        if (!pressed.containsKey(key)) {
            pressed.put(key, state);
        }

        boolean tapped = (!state && pressed.get(key));
        pressed.put(key, state);

        return tapped;
    }
}
