package cpsc599.menus;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import cpsc599.util.Logger;
import cpsc599.util.SharedAssets;

import java.util.ArrayList;

/**
 * Menu that is opened after a player has decided where to move.
 */
public class ActionMenu extends Menu {
    private ArrayList<String> options;
    private BitmapFont font;

    int pointer, pointer_y;
    float pointer_height;

    public ActionMenu(int width, int height) {
        super(width, height);
        this.visible = false;

        options = new ArrayList<String>();

        //These are temporary right now
        options.add("Attack");
        options.add("Stats");
        options.add("Equip");
        options.add("Drop");
        options.add("Pickup");
        options.add("Inventory");
        options.add("End Turn");


        font = new BitmapFont();
        this.pointer_height = 14;
    }

    public void movePointer(int dir) {
        pointer += dir;

        if (pointer >= options.size()) {
            pointer = 0;
        } if (pointer < 0) {
            pointer = options.size() - 1;
        }
        this.pointer_y = (int)(this.pointer_height * this.pointer);

        Logger.debug("Setting pointer to: " + this.options.get(pointer));
    }

    public String getAction() {
        return this.options.get(pointer);
    }

    @Override
    public void render(SpriteBatch batch) {
        if (!visible) return;

        batch.begin();
        this.drawMenu(batch, 20, 10, 128, 112);

        batch.draw(SharedAssets.menu_pointer, 32, 26 + pointer_y);

        int yv = 0;
        for (String s : options) {
            yv += font.getBounds(s).height + 2;
            font.draw(batch, s, 45, 23 + yv);
        }
        batch.end();
    }

    @Override
    public void tick(Input input) {

    }
}
