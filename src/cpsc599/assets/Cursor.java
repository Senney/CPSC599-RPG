package cpsc599.assets;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import cpsc599.Main;
import cpsc599.OrbGame;
import cpsc599.managers.LevelManager;
import cpsc599.states.LevelState;
import cpsc599.util.CoordinateTranslator;
import cpsc599.util.Logger;

public class Cursor {
    private TextureRegion cursorSprite;

    public int x, y;

    public Cursor(TextureRegion sprite){
        this.cursorSprite = sprite;
    }

    public void tick(){

    }

    public void render(SpriteBatch batch) {
        batch.draw(this.cursorSprite, CoordinateTranslator.translate(x), CoordinateTranslator.translate(y));
    }

    public void move(int direction){
        switch(direction){
            case Input.Keys.UP:
                if(y <= 0) break;
                y--;
                break;
            case Input.Keys.DOWN:
                y++;
                break;
            case Input.Keys.LEFT:
                x++;
                break;
            case Input.Keys.RIGHT:
                if(x <= 0) break;
                x--;
                break;
        }
    }
}
