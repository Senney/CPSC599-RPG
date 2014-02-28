package cpsc599.menus;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import cpsc599.util.Logger;

/** Class for containing the information required to display a menu. **/
public abstract class Menu {
    protected int height;
    protected int width;
    protected boolean visible;

    /**
     * Important note about LibGDX is that it uses a y-up coordinate system by default. This can introduce some
     * confusion when producing things in screen-space, versus world space, since our use of the OrthoCamera in
     * world space allows us to flip the y-coordinate system.
     * @param width
     * @param height
     */
    public Menu(int width, int height) {
        this.height = height;
        this.width = width;
    }

    public abstract void render(SpriteBatch batch);
    public abstract void tick(Input input);


    public boolean toggleVisible() {
        visible = !visible;
        Logger.debug("Setting visibility of Inventory to: " + visible);
        return visible;
    }

    public boolean isVisible() { return this.visible; }
}
