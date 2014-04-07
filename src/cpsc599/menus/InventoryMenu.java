package cpsc599.menus;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import cpsc599.items.Inventory;
import cpsc599.items.Item;
import cpsc599.util.Logger;
import cpsc599.util.SharedAssets;

import java.util.ArrayList;

/** Class for rendering the Inventory for a specific player.. **/
public class InventoryMenu extends Menu {
    private Inventory inventory;

    private ArrayList<String> options;
    private BitmapFont font;

    public InventoryMenu(int width, int height) {
        super(width, height);
        this.visible = false;

        font = SharedAssets.font_12;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public void render(SpriteBatch batch) {
        if (!visible) return;

        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(20, 20, super.width, super.height);
        shapeRenderer.end();

        batch.begin();
        int yv = 0;
        for (Item i : this.inventory.getCarry()) {
            if (i == null) continue;
            yv += font.getBounds(i.name).height + 2;
            font.draw(batch, i.name, 22, 22 + yv);
        }
        batch.end();
    }

    @Override
    public void tick(Input input) {

    }
}
