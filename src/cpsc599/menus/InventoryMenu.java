package cpsc599.menus;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import cpsc599.items.Inventory;

import java.util.ArrayList;

/** Class for rendering the Inventory for a specific player.. **/
public class InventoryMenu extends Menu {
    private Inventory inventory;

    private ArrayList<String> options;
    private BitmapFont font;
    private boolean visible;

    public InventoryMenu(int width, int height) {
        super(width, height);
        this.visible = false;

        options = new ArrayList<String>();
        for (int x = 0; x < 14; x++) {
            options.add("Item " + (x+1));
        }

        font = new BitmapFont();
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public boolean toggleVisible() {
        visible = !visible;
        return visible;
    }

    @Override
    public void render(SpriteBatch batch) {
        if (!visible) return;

        ShapeRenderer shapeRenderer = new ShapeRenderer();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(20, 20, super.width, super.height);
        shapeRenderer.end();

        int yv = 0;
        for (String s : options) {
            yv += font.getBounds(s).height + 2;
            font.draw(batch, s, 22, 22 + yv);
        }
    }

    @Override
    public void tick(Input input) {

    }
}
