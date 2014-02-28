package cpsc599.controller;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import cpsc599.util.CoordinateTranslator;

/**
 * Class for controlling movement and positioning of the Camera.
 */
public class CameraController {
    private int x, y;
    private double scale;
    private int width, height;

    private OrthographicCamera camera;

    /**
     * Creates the CameraController with the specified position and size.
     * @param position (x, y) position on the screen specified in units related to the map (x * 16, y * 16)
     * @param size (w, h, scale) the parameters that specify the size the camera should view.
     */
    public CameraController(Vector2 position, Vector3 size) {
        this.width = (int)size.x;
        this.height = (int)size.y;
        this.scale = (double)size.z;

        this.camera = new OrthographicCamera(size.x, size.y);
        set((int)position.x, (int)position.y);
    }

    /**
     * Sets the camera position in game-units.
     * @param x the x position.
     * @param y the y position.
     */
    public void set(int x, int y) {
        // Check that the X and Y positions don't overflow the camera off of the map.
        int realx = bound(CoordinateTranslator.translate(x), width);
        int realy = bound(CoordinateTranslator.translate(y), width);

        this.x = realx;
        this.y = realy;

        this.updateCamera();
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
