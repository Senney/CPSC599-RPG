package cpsc599.assets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import cpsc599.Main;
import cpsc599.util.Logger;

/**
 * Asset for containing information regarding HealthBars.
 */
public class HealthBar {
    final int HEALTH_BAR_WIDTH = 60;
    final int HEALTH_BAR_HEIGHT = 10;

    final Color HEALTH_COLOR = new Color(0.20f, 1.0f, 0.20f, 1.0f);

    ShapeRenderer renderer;

    public HealthBar() {
        this.renderer = new ShapeRenderer();
    }

    public void render(int health, int maxHealth, SpriteBatch batch) {
        double pct = (double)health / (double)maxHealth;
        if (pct > 1) {
            Logger.error("Player had over 100% health.");
            return;
        }

        int width = (int)(HEALTH_BAR_WIDTH * pct);
        int rem_width = HEALTH_BAR_WIDTH - width;
        int ypos = (int)(Main.GAME_HEIGHT * Main.GAME_SCALE) - HEALTH_BAR_HEIGHT - 20;

        this.renderer.begin(ShapeRenderer.ShapeType.Filled);
        this.renderer.setColor(this.HEALTH_COLOR);
        this.renderer.rect(10, ypos, width, HEALTH_BAR_HEIGHT);
        this.renderer.setColor(Color.RED);
        this.renderer.rect(10 + width, ypos, rem_width, HEALTH_BAR_HEIGHT);
        this.renderer.end();
    }
}
