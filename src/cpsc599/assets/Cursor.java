package cpsc599.assets;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import cpsc599.OrbGame;
import cpsc599.util.CoordinateTranslator;
import cpsc599.util.Logger;

public class Cursor {
    private AnimatedSprite cursorSprite;

    public int x, y;

    public Cursor(){
        this.cursorSprite = new AnimatedSprite("assets/tilesets/testsquare.png", 0, 0, 16, 16, 1, 0.1f);
    }

    public void tick(){

    }

    public void render(SpriteBatch batch) {
        cursorSprite.render(batch, CoordinateTranslator.translate(x), CoordinateTranslator.translate(y));
    }

    public void move(int direction){
        switch(direction){
            case Input.Keys.UP:
                y++;
                break;
            case Input.Keys.DOWN:
                y--;
                break;
            case Input.Keys.LEFT:
                x--;
                break;
            case Input.Keys.RIGHT:
                x++;
                break;
        }
    }
}
