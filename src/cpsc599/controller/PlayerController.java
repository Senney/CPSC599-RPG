package cpsc599.controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import cpsc599.assets.Player;
import cpsc599.managers.PlayerManager;
import cpsc599.util.Controls;

/**
 * Acts as a controller for all players.
 */
public class PlayerController {
    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    private PlayerManager playerManager;
    private Vector2 selectorPosition;

    public PlayerController(PlayerManager manager) {
        this.playerManager = manager;
        this.selectorPosition = new Vector2();
    }

    public void control(Input input) {
        Player p = this.playerManager.getCurrent();
        if (Controls.isKeyTapped(input, Controls.UP)) {
            p.move(0, -1);
        } else if (Controls.isKeyTapped(input, Controls.DOWN)) {
            p.move(0, 1);
        } else if (Controls.isKeyTapped(input, Controls.RIGHT)) {
            p.move(1, 0);
        } else if (Controls.isKeyTapped(input, Controls.LEFT)) {
            p.move(-1, 0);
        }

        // Move the player.
        if (Controls.isKeyTapped(input, Controls.A_BUTTON)) {
            // End the turn here.
        }

        // Reset the selector position.
        if (Controls.isKeyTapped(input, Controls.B_BUTTON)) {
            selectorPosition.x = (float)p.x;
            selectorPosition.y = (float)p.y;
        }
    }
}
