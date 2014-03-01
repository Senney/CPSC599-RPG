package cpsc599.menus;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

/**
 * Menu that is opened after a player has decided where to move.
 */
public class ActionMenu extends Menu {

    private ArrayList<String> options;
    private BitmapFont font;

    public ActionMenu(int width, int height) {
        super(width, height);
        this.visible = false;

        options = new ArrayList<String>();

        //These are temporary right now
        options.add("Attack");
        options.add("Equip");
        options.add("Drop");
        options.add("Pickup");
        options.add("Inventory");

        font = new BitmapFont();
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
