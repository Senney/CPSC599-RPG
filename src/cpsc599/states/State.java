package cpsc599.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;

import cpsc599.OrbGame;

/**
 * Super-class for all states. Contains base functions to control drawing, and defines basic abstract functions
 * that must be defined for a state to draw properly.
 */
public abstract class State {
    private OrbGame orb;
    public SpriteBatch spriteBatch;

    abstract void render();
    abstract void tick(/*Input input*/);

    void init(OrbGame game) {
        this.orb = game;

        Matrix4 projection = new Matrix4();
        projection.setToOrtho(0, 320, 240, 0, -1, 1);

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
}
