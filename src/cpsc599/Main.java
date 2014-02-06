package cpsc599;

import cpsc599.util.Logger;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class Main {
	public static void main(String[] args) {
		// Initialize logger.
		Logger.setOutputStream(System.out);
		Logger.setLogLevel(Logger.DEBUG);
		Logger.debug("Logger initialization completed.");
		
		// Initialize the game window.
		Logger.debug("Creating LwjglApplication.");
		new LwjglApplication(new OrbGame(), "Tale of the Orb", 320, 240, false);
	}
}
