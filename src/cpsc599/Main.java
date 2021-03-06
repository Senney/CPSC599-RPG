package cpsc599;

import cpsc599.util.Logger;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class Main {
    public static final int GAME_WIDTH = 256;
    public static final int GAME_HEIGHT = 240;
    public static final double GAME_SCALE = 2;

	public static void main(String[] args) {
		// Initialize logger.
		Logger.setOutputStream(System.out);
		Logger.setLogLevel(Logger.DEBUG);
		Logger.debug("Logger initialization completed.");
		
		// Initialize the game window.
		Logger.debug("Creating LwjglApplication.");
		new LwjglApplication(new OrbGame(GAME_WIDTH, GAME_HEIGHT, GAME_SCALE), "Tale of the Orb",
                (int)(GAME_WIDTH * GAME_SCALE), (int)(GAME_HEIGHT * GAME_SCALE), false);
	}
}
