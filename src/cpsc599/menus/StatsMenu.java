package cpsc599.menus;

/*Menu shown when checking the stats of a character
 */

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import cpsc599.util.SharedAssets;

import java.util.ArrayList;

public class StatsMenu extends Menu {

    private ArrayList<String> stats;
    int pointer, pointer_y;
    private BitmapFont font;

    public StatsMenu(int width, int height)
    {
        super(width, height);
        this.visible = false;
    }

    @Override
    public void render(SpriteBatch batch) {
        if (!visible) return;

        batch.begin();
        this.drawMenu(batch, 20, 10, 128, 112);

        batch.draw(SharedAssets.menu_pointer, 32, 26 + pointer_y);

        int yv = 0;
        for (String s : stats) {
            yv += font.getBounds(s).height + 2;
            font.draw(batch, s, 45, 23 + yv);
        }
        batch.end();
    }

    @Override
    public void tick(Input input) {

    }
}
