package cpsc599.controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import cpsc599.assets.AnimatedSprite;
import cpsc599.assets.Cursor;
import cpsc599.assets.Player;
import cpsc599.managers.PlayerManager;
import cpsc599.util.Controls;
import cpsc599.util.Logger;

/**
 * Acts as a controller for all players.
 */
public class PlayerController {
    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    private Cursor cursor;

    private PlayerManager playerManager;
    private Vector2 selectorPosition;

    public PlayerController(PlayerManager manager) {
        this.playerManager = manager;
        this.selectorPosition = new Vector2();
    }

    public void ctrl_cursor(Input input)
    {
        
    }

    public boolean isCursor() {
        return (this.playerManager.getCurrent() == null);
    }

    public void setupCursor() {
        cursor = new Cursor(new AnimatedSprite("assets/tilesets/cursor.png", 0, 0, 16, 16, 1, 0.1f));
    }

    public Cursor getCursor() {
        return this.cursor;
    }

    public void control(Input input) {
        Player p = this.playerManager.getCurrent();

        if (p != null) {
            if (Controls.isKeyTapped(input, Controls.UP)) {
                p.move(0, -1);
            } else if (Controls.isKeyTapped(input, Controls.DOWN)) {
                p.move(0, 1);
            } else if (Controls.isKeyTapped(input, Controls.RIGHT)) {
                p.move(1, 0);
            } else if (Controls.isKeyTapped(input, Controls.LEFT)) {
                p.move(-1, 0);
            }
        } else {
            // Control the cursor here.
            if (Controls.isKeyTapped(input, Controls.UP)) {
                cursor.move(Controls.UP);
            } else if (Controls.isKeyTapped(input, Controls.DOWN)) {
                cursor.move(Controls.DOWN);
            } else if (Controls.isKeyTapped(input, Controls.RIGHT)) {
                cursor.move(Controls.LEFT);
            } else if (Controls.isKeyTapped(input, Controls.LEFT)) {
                cursor.move(Controls.RIGHT);
            }
        }

        // Move the player.
        if (Controls.isKeyTapped(input, Controls.A_BUTTON)) {
            Logger.debug("PlayerController::control - 'A' button pressed.");
            // Check to see if we're over a player with our cursor.
            if (p == null) {
                // We must on the cursor.
                for (Player player : this.playerManager.getPlayers()) {
                    if (player.x == cursor.x && player.y == cursor.y) {
                        this.playerManager.setCurrent(player);
                        this.selectorPosition.x = player.x;
                        this.selectorPosition.y = player.y;
                    }
                }
            } else {
                this.cursor.x = this.playerManager.getCurrent().x;
                this.cursor.y = this.playerManager.getCurrent().y;
                this.playerManager.setCurrent(null); // Nullify the current player.
            }
        }

        // Reset the selector position.
        if (Controls.isKeyTapped(input, Controls.B_BUTTON)) {
            Logger.debug("PlayerController::control - 'B' button pressed.");
            if (p != null) {
                if (p.x == (int)this.selectorPosition.x && p.y == (int)this.selectorPosition.y) {
                    this.cursor.x = this.playerManager.getCurrent().x;
                    this.cursor.y = this.playerManager.getCurrent().y;
                    this.playerManager.setCurrent(null); // Nullify the current player.
                } else {
                    p.x = (int)this.selectorPosition.x;
                    p.y = (int)this.selectorPosition.y;
                }
            }
        }
    }
}
