package cpsc599.controller;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.GdxNativesLoader;
import cpsc599.Main;
import cpsc599.util.CoordinateTranslator;
import cpsc599.util.Logger;
import sun.net.www.content.text.plain;

/**
 * Class for controlling movement and positioning of the Camera.
 */
public class CameraController {
    private int x, y;
    private double scale;
    private int width, height;

    private OrthographicCamera camera;
    private Rectangle cameraRect;

    private final int CAMERA_SIDE_THRESH = 32; // 32 pixels from the side of the rectangle, we start to move it.

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
        Logger.info("Constructing");
        this.width = (int)size.x;
        this.height = (int)size.y;
        this.scale = (double)size.z;
        this.cameraRect = new Rectangle(CoordinateTranslator.translate((int)position.x),
                CoordinateTranslator.translate((int)position.y), size.x, size.y);

        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(true, size.x, size.y);
        set((int)position.x, (int)position.y);

        this.camera.zoom = (float)this.scale;

    }

    public void setCameraBounds(Vector2 size) {
        this.width = CoordinateTranslator.translate((int)size.x);
        this.height = CoordinateTranslator.translate((int)size.y);
        Logger.debug("Setting camera bounds to (" + this.width + ", " + this.height + ")");
    }

    /**
     * Moves the camera to the noted position, in game-units. This method revolves around a rectangle which
     * represents the camera. Once the x, y position has reached a threshold (CAMERA_SIDE_THRESH) from the edge
     * of this rectangle, the rectangle repositions itself accordingly.
     * <strong>Currently NOT functioning.</strong>
     * @param x
     * @param y
     */
    public void move(int x, int y) {
        if (this.camera == null) {
            Logger.fatal("Camera must be initialized before it can be used");
            return;
        }

        int realx = CoordinateTranslator.translate(x);
        int realy = CoordinateTranslator.translate(y);

        if (realx <= (this.cameraRect.x + CAMERA_SIDE_THRESH)) {
            if (realx > CoordinateTranslator.TILE_SIZE) {
                this.cameraRect.x -= CoordinateTranslator.TILE_SIZE;
            }
            this.set((int)this.cameraRect.x, y);
        } else if (realx >= ((this.cameraRect.x + this.cameraRect.width) - CAMERA_SIDE_THRESH)) {
            if (realx < CoordinateTranslator.TILE_SIZE) {
                this.cameraRect.x += CoordinateTranslator.TILE_SIZE;
            }
            this.set((int)this.cameraRect.x, y);
        } else if (realy <= (this.cameraRect.y + CAMERA_SIDE_THRESH)) {
            if (realy > CoordinateTranslator.TILE_SIZE) {
                this.cameraRect.y -= CoordinateTranslator.TILE_SIZE;
            }
            this.set(x, y);
        } else if (realy >= ((this.cameraRect.x + this.cameraRect.width) - CAMERA_SIDE_THRESH)) {
            if (realy < CoordinateTranslator.TILE_SIZE) {
                this.cameraRect.y += CoordinateTranslator.TILE_SIZE;
            }
            this.set(x, y);
        }

        return;
    }

    /**
     * Sets the camera position in game-units.
     * @param x the x position.
     * @param y the y position.
     */
    public void set(int x, int y) {
        if (this.camera == null) {
            Logger.fatal("Camera must be instantiated before it can be set.");
            return;
        }

        // Check that the X and Y positions don't overflow the camera off of the map.
        int realx = bound(CoordinateTranslator.translate(x), width, width);
        int realy = bound(CoordinateTranslator.translate(y), height, height);

        if (this.x == realx && this.y == realy) return; // Don't do unnecessary work.
        Logger.debug("Setting camera position to (" + realx + ", " + realy + ")");
        this.x = realx;
        this.y = realy;

        this.updateCamera();
    }

    public Camera getCamera() {
        return this.camera;
    }

    private int bound(int d, int v, int m) {
        if (d - (v / 2) < 0) return v / 2;
        if (d + (v / 2) > m) return m - (v / 2);
        return d;
    }

    private void updateCamera() {
        this.camera.position.x = this.x;
        this.camera.position.y = this.y;
        this.camera.update();
    }
}
