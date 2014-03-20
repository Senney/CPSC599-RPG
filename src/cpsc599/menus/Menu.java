package cpsc599.menus;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import cpsc599.util.Logger;
import cpsc599.util.SharedAssets;

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

    public void drawMenu(SpriteBatch batch, int xpos, int ypos, int width, int height) {
        if (width % 16 != 0 || height % 16 != 0) {
            Logger.fatal("Unable to draw menu with non-16-divisible width and height.");
            return;
        }

        int width_iter = width / 16;
        int height_iter = height / 16;

        // Draw the bottom and top
        batch.draw(SharedAssets.menu_texture[2][0], xpos, ypos);
        batch.draw(SharedAssets.menu_texture[0][0], xpos, ypos + (16 * (height_iter-1)));
        for (int i = 1; i < width_iter - 1; i++) {
            batch.draw(SharedAssets.menu_texture[2][1], xpos+(16*i), ypos);
            batch.draw(SharedAssets.menu_texture[0][1], xpos+(16*i), ypos + (16 * (height_iter-1)));
        }
        batch.draw(SharedAssets.menu_texture[2][2], xpos + (16 * (width_iter-1)), ypos);
        batch.draw(SharedAssets.menu_texture[0][2], xpos + (16 * (width_iter-1)), ypos + (16 * (height_iter-1)));

        // Draw the middle
        for (int i = 1; i < height_iter - 1; i++) {
            batch.draw(SharedAssets.menu_texture[1][0], xpos, ypos + (16 *i));
            for (int j = 1; j < width_iter - 1; j++) {
                batch.draw(SharedAssets.menu_texture[1][1], xpos + (16*j), ypos + (16*i));
            }
            batch.draw(SharedAssets.menu_texture[1][2], xpos + (16 * (width_iter - 1)), ypos + (16*i));
        }
    }


    public boolean toggleVisible() {
        visible = !visible;
        Logger.debug("Setting visibility of Inventory to: " + visible);
        return visible;
    }

    public boolean setVisible(boolean visible) {
        return (this.visible = visible);
    }

    public boolean isVisible() { return this.visible; }
}
