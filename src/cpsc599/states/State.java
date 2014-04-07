package cpsc599.states;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;

import cpsc599.Main;
import cpsc599.OrbGame;
import cpsc599.util.Logger;

import java.util.HashMap;

/**
 * Super-class for all states. Contains base functions to control drawing, and defines basic abstract functions
 * that must be defined for a state to draw properly.
 */
public abstract class State {
    protected OrbGame orb;
    protected static BitmapFont font = new BitmapFont(true);
    public SpriteBatch spriteBatch;

    protected HashMap<String, Object> flags;

    abstract public void render();
    abstract public void tick(Input input);

    /**
     * Initializes the state. Must be called before you can render it.
     * @param game
     */
    public void init(OrbGame game) {
        Logger.debug("Initializing game state");

        if (this.orb == null) {
            Logger.debug("State has already been initialized.");
        }

        if (flags == null) {
            flags = new HashMap<String, Object>();
        }

        this.orb = game;

        Matrix4 projection = new Matrix4();
        projection.setToOrtho(0, Main.GAME_WIDTH, Main.GAME_HEIGHT, 0, 1, -1);

        this.spriteBatch = new SpriteBatch();
        this.spriteBatch.setProjectionMatrix(projection);
    }

    /**
     * Draws a texture region on the screen at the specified coordinates. Ensure that spriteBatch.begin()
     * is called before calling draw, and that spriteBatch.end() is called afterwards.
     * @param texture The texture region to be drawn. Usually derived from a sprite sheet.
     * @param x x screen coordinate.
     * @param y y screen coordinate.
     */
    public void draw(TextureRegion texture, int x, int y) {
        spriteBatch.draw(texture, (float)x, (float)y, texture.getRegionWidth(), texture.getRegionHeight());
    }

    /**
     * Specifies how to align the text. Offsets on the x axis to align.
     */
    public enum StringAlign {
        LEFT, CENTER, RIGHT
    };

    /**
     * Draws a string on the screen, left-aligned, at the specified position.
     * @param string The string to draw.
     * @param x
     * @param y
     */
    public void drawString(String string, int x, int y) {
        font.draw(this.spriteBatch, string, x, y);
    }

    public void setFlag(String key, Object value) {
        this.flags.put(key, value);
    }

    public Object getFlag(String key) {
        return this.flags.get(key);
    }

    /**
     * Draws a string on the screen in alignment.
     * @param string The string to draw.
     * @param x x position.
     * @param y y position.
     * @param align Text alignment.
     */
    public void drawString(String string, int x, int y, StringAlign align) {
        float xbound = font.getBounds(string).width;
        switch (align) {
            case LEFT:
                // Do nothing!
                break;
            case CENTER:
                x -= (xbound / 2);
                break;
            case RIGHT:
                x -= xbound;
                break;
        }

        font.draw(spriteBatch, string, x, y);
    }
}
