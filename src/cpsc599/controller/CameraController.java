package cpsc599.controller;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.GdxNativesLoader;
import cpsc599.util.CoordinateTranslator;
import cpsc599.util.Logger;

/**
 * Class for controlling movement and positioning of the Camera.
 */
public class CameraController {
    private int x, y;
    private double scale;
    private int width, height;

    private OrthographicCamera camera;

    static {
        // HACK: Don't remove this!! Forces the JVM to load unloaded native dlls... which aren't loaded.
        GdxNativesLoader.load();
    }

    /**
     * Creates the CameraController with the specified position and size.
     * @param position (x, y) position on the screen specified in units related to the map (x * 16, y * 16)
     * @param size (w, h, scale) the parameters that specify the size the camera should view.
     */
    public CameraController(Vector2 position, Vector3 size) {
        Logger.info("CameraController - Constructing");
        this.width = (int)size.x;
        this.height = (int)size.y;
        this.scale = (double)size.z;

        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(true, size.x, size.y);
        set((int)position.x, (int)position.y);

    }

    /**
     * Sets the camera position in game-units.
     * @param x the x position.
     * @param y the y position.
     */
    public void set(int x, int y) {
        if (this.camera == null) {
            Logger.fatal("CameraController::set - Camera must be instantiated before it can be set.");
            return;
        }

        // Check that the X and Y positions don't overflow the camera off of the map.
        int realx = bound(CoordinateTranslator.translate(x), width);
        int realy = bound(CoordinateTranslator.translate(y), width);

        this.x = realx;
        this.y = realy;

        this.updateCamera();

        Logger.info("CameraController::set - Camera set to position (" + this.x + ", " + this.y + ")");
    }

    public Camera getCamera() {
        return this.camera;
    }

    private int bound(int x, int v) {
        if (x - (v / 2) < 0) x = v / 2;
        return x;
    }

    private void updateCamera() {
        this.camera.position.x = this.x;
        this.camera.position.y = this.y;
        this.camera.update();
    }
}
