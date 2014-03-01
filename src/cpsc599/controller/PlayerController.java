package cpsc599.controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import cpsc599.assets.AnimatedSprite;
import cpsc599.assets.Cursor;
import cpsc599.assets.Player;
import cpsc599.managers.PlayerManager;
import cpsc599.menus.ActionMenu;
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

    private ActionMenu actMenu;

    private PlayerManager playerManager;
    private Vector2 selectorPosition;

    public PlayerController(PlayerManager manager) {
        this.playerManager = manager;
        this.selectorPosition = new Vector2();
    }

    public boolean isCursor() {
        return (this.playerManager.getCurrent() == null);
    }

    public void setupCursor() {
        cursor = new Cursor(new AnimatedSprite("assets/tilesets/cursor.png", 0, 0, 16, 16, 1, 0.1f));
    }

    public void setupActionmenu(){
        actMenu = new ActionMenu(100,200);
    }

    public ActionMenu getActMenu() {return this.actMenu;}

    public Cursor getCursor() {
        return this.cursor;
    }

    public void control(Input input) {
        Player p = this.playerManager.getCurrent();

        if (p != null) {
            movePlayer(input, p);
        } else {
            moveCursor(input);
        }

        // Move the player.
        if (Controls.isKeyTapped(input, Controls.A_BUTTON)) {
            Logger.debug("PlayerController::control - 'A' button pressed.");
            // Check to see if we're over a player with our cursor.
            if (p == null) {
                // No player is selected, so we should check if a player is under the cursor.
                selectPlayerOnCursor();
            } else {
                /*actMenu.toggleVisible();
                Logger.debug("PlayerController:: control - entering action menu");
                //enter action menu mode
                String action = actionMenuMode(input);
                if(action.equals("End Turn"))
                {
                    actMenu.toggleVisible();
                    releasePlayer();
                }
                else if(action.equals("Attack"));*/
                
                releasePlayer();
            }
        }

        // Reset the selector position.
        if (Controls.isKeyTapped(input, Controls.B_BUTTON)) {
            Logger.debug("PlayerController::control - 'B' button pressed.");
            if (p != null) {
                if (p.x == (int)this.selectorPosition.x && p.y == (int)this.selectorPosition.y) {
                    releasePlayer();
                } else {
                    resetPlayerToCursor(p);
                }
            }
        }
    }

    private void resetPlayerToCursor(Player p) {
        p.x = (int)this.selectorPosition.x;
        p.y = (int)this.selectorPosition.y;
        p.resetMove();
    }

    private void selectPlayerOnCursor() {
        for (Player player : this.playerManager.getPlayers()) {
            if (player.x == cursor.x && player.y == cursor.y) {
                this.playerManager.setCurrent(player);
                this.selectorPosition.x = player.x;
                this.selectorPosition.y = player.y;
                player.resetMove();
            }
        }
    }

    private void releasePlayer() {
        this.cursor.x = this.playerManager.getCurrent().x;
        this.cursor.y = this.playerManager.getCurrent().y;
        this.playerManager.setCurrent(null); // Nullify the current player.
    }

    private String actionMenuMode(Input input){
        /**
         * Ok so obviously I don't know how the library handles non blocking input so ill just comment this out for now
         */
         if(Controls.isKeyTapped(input, Controls.LEFT)){
              Logger.debug("Action Menu - selected \'Attack\'");
         }
         if(Controls.isKeyTapped(input, Controls.START)) //my magic 'end turn button' wll be replaced by a menu selection
         {
             Logger.debug("PlayerController:: actionMenumode - ending turn");
             return "End Turn";
         }
        if(Controls.isKeyTapped(input, Controls.A_BUTTON))
        {
            //add get option from menu and check to see which option they chose
            //add conditions for attacking (must be near enemy)
            Logger.debug("PlayerController:: actionMenuMode - Attacking enemy");
            return "Attack";
        }
        else
            Logger.debug("PlayerController:: actionMenuMode - Cancelling menu");
        return "Cancel";
    }

    private void moveCursor(Input input) {
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

    private void movePlayer(Input input, Player p) {
        if (Controls.isKeyTapped(input, Controls.UP)) {
            p.move(0, -1);
        } else if (Controls.isKeyTapped(input, Controls.DOWN)) {
            p.move(0, 1);
        } else if (Controls.isKeyTapped(input, Controls.RIGHT)) {
            p.move(1, 0);
        } else if (Controls.isKeyTapped(input, Controls.LEFT)) {
            p.move(-1, 0);
        }
    }
}
