package cpsc599.menus;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** Class for containing the information required to display a menu. **/
public abstract class Menu {
    protected int height;
    protected int width;

    public Menu(int width, int height) {
        this.height = height;
        this.width = width;
    }

    public abstract void render(SpriteBatch batch);
    public abstract void tick(Input input);
}
