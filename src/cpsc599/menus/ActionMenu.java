package cpsc599.menus;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import cpsc599.util.Logger;

import java.util.ArrayList;

/**
 * Menu that is opened after a player has decided where to move.
 */
public class ActionMenu extends Menu {
    private ArrayList<String> options;
    private BitmapFont font;

    int pointer;

    public ActionMenu(int width, int height) {
        super(width, height);
        this.visible = false;
        this.pointer = 0;

        options = new ArrayList<String>();

        //These are temporary right now
        options.add("Attack");
        options.add("Equip");
        options.add("Drop");
        options.add("Pickup");
        options.add("Inventory");

        font = new BitmapFont();
    }

    public void movePointer(int dir) {
        pointer += dir;

        if (pointer >= options.size()) {
            pointer = 0;
        } if (pointer < 0) {
            pointer = options.size() - 1;
        }

        Logger.debug("ActionMenu::movePointer - Setting pointer to: " + this.options.get(pointer));
    }

    public String getAction() {
        return this.options.get(pointer);
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
