package cpsc599.menus;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import cpsc599.util.Logger;
import cpsc599.util.SharedAssets;

import java.util.ArrayList;

public class GlobalMenu extends Menu{
    private ArrayList<String> options;
    private BitmapFont font;

    int pointer, pointer_y;
    float pointer_height;

    public GlobalMenu(int width, int height) {
        super(width, height);
        setVisible(false);

        options = new ArrayList<String>();

        //These are temporary right now
        options.add("End turn");


        font = SharedAssets.font_12;
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
        this.drawMenu(batch, 359, 250, 144, 208);

        batch.draw(SharedAssets.menu_pointer, 370, 250 + pointer_y + 185);

        int yv = 0;
        for (String s : options) {
            yv += font.getBounds(s).height + 2;
            font.draw(batch, s, 385, 458 - yv);
        }
        batch.end();
    }

    @Override
    public void tick(Input input) {

    }
}
