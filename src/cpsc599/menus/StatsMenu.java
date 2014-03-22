package cpsc599.menus;

/*Menu shown when checking the stats of a character
 */

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import cpsc599.assets.Actor;

import java.util.ArrayList;

public class StatsMenu extends Menu {

    private ArrayList<String> stats;
    private BitmapFont font;

    Actor actor;

    public StatsMenu(int width, int height, Actor actor)
    {
        super(width, height);
        this.visible = false;
        this.actor = actor;

        stats = new ArrayList<String>();

        stats.add("   STATS");
        stats.add("HP: ");
        stats.add("Damage: ");
        stats.add("Strength: ");
        stats.add("Defence: ");
        stats.add("Speed: ");

        font = new BitmapFont();
    }

    @Override
    public void render(SpriteBatch batch) {
        if (!visible) return;

        batch.begin();
        //this.drawMenu(batch, 20, 10, 128, 112);
        this.drawMenu(batch, 375, 250, 128, 208);

        int yv = 0;
        int size =0;
        int count = 0;
        for (String s : stats) {
            yv += font.getBounds(s).height + 2;
            //font.draw(batch, s, 45, 23 + yv);
            //System.out.println("String s = " + s);
            font.draw(batch, s, 385, 458 - yv);
            if(s.equals("HP: "))
                font.draw(batch, actor.currentHealth + "/" + actor.maxHealth, 460, 458 - yv);
            else if(s.equals("Strength: "))
                font.draw(batch, Integer.toString(actor.strength), 480, 458 - yv);
            else if(s.equals("Defence: "))
                font.draw(batch, Integer.toString(actor.defence), 480, 458 - yv);
            else if(s.equals("Speed: "))
                font.draw(batch, Integer.toString(actor.speed), 480, 458 - yv);
            count++;
        }
        //yv = 0;
       // yv += font.getBounds(s).height + 2;
        //yv += font.getBounds(s).height + 2;
        //font.draw(batch, actor.currentHealth + "/" + actor.maxHealth, 460, 458 - yv);
        //yv += font.getBounds(s).height + 2;
        //font.draw()
        batch.end();
    }

    public void setActor(Actor actor)
    {
        this.actor = actor;
    }

    @Override
    public void tick(Input input) {

    }
}
