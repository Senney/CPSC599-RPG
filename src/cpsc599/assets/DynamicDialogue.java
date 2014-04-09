package cpsc599.assets;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import cpsc599.util.Logger;
import cpsc599.util.SharedAssets;

/**
 * For small, dynamic dialogue boxes that disappear at the end of turns.
 * Key notes:
 *  Non-scrollable.
 *  Non-wrapping
 */
public class DynamicDialogue {
    private boolean visible;
    private String text;
    private BitmapFont font;
    private float width, height;
    private Vector2 position;
    private BitmapFont.TextBounds bounds;

    public DynamicDialogue() {
        this.text = "";
        this.visible = false;
        this.font = SharedAssets.font_12;
    }

    public void setVisibility(boolean vis) {
        this.visible = vis;
    }

    public void render(SpriteBatch batch) {
        if (this.visible) {
            batch.begin();
            // Draw box.
            drawTextBackdrop(batch, (int) position.x, (int) position.y, (int) width, (int) height);
            // Render text.
            font.draw(batch, text, this.position.x + 8, this.position.y + this.height - 8);
            batch.end();
        }
    }

    public void setText(String text) {
        this.text = text;
        bounds = font.getBounds(text);
        this.width = round16(bounds.width) + 16;
        this.height = round16(bounds.height) + 16;
    }

    public String getText() {
        return this.text;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    private float round16(float value) {
        if (value % 16 != 0) {
            int numDiv = (int)(value / 16.f);
            value = (numDiv+1) * 16;
        }

        return value;
    }

    private void drawTextBackdrop(SpriteBatch batch, int xpos, int ypos, int width, int height) {
        if (width % 16 != 0 || height % 16 != 0) {
            Logger.fatal("Unable to draw menu with non-16-divisible width and height.");
            return;
        }

        int width_iter = width / 16;
        int height_iter = height / 16;

        // Draw the bottom and top
        batch.draw(SharedAssets.menu_texture[2][0], xpos, ypos);
        batch.draw(SharedAssets.menu_texture[0][0], xpos, ypos + (16 * (height_iter-1)));
        for (int i = 1; i < width_iter - 1; i++) {
            batch.draw(SharedAssets.menu_texture[2][1], xpos+(16*i), ypos);
            batch.draw(SharedAssets.menu_texture[0][1], xpos+(16*i), ypos + (16 * (height_iter-1)));
        }
        batch.draw(SharedAssets.menu_texture[2][2], xpos + (16 * (width_iter-1)), ypos);
        batch.draw(SharedAssets.menu_texture[0][2], xpos + (16 * (width_iter-1)), ypos + (16 * (height_iter-1)));

        // Draw the middle
        for (int i = 1; i < height_iter - 1; i++) {
            batch.draw(SharedAssets.menu_texture[1][0], xpos, ypos + (16 *i));
            for (int j = 1; j < width_iter - 1; j++) {
                batch.draw(SharedAssets.menu_texture[1][1], xpos + (16*j), ypos + (16*i));
            }
            batch.draw(SharedAssets.menu_texture[1][2], xpos + (16 * (width_iter - 1)), ypos + (16*i));
        }
    }

    public void show(String text) {
        this.setText(text);
        this.setVisibility(true);
    }
}
