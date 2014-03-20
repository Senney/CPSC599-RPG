package cpsc599.controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import cpsc599.assets.*;
import cpsc599.items.Inventory;
import cpsc599.managers.EnemyManager;
import cpsc599.managers.PlayerManager;
import cpsc599.menus.ActionMenu;
import cpsc599.menus.InventoryMenu;
import cpsc599.util.Controls;
import cpsc599.util.Logger;

import java.util.ArrayList;

/**
 * Acts as a controller for all players.
 */
public class PlayerController {
    private boolean turnComplete;
    private boolean attacking;
    private int attackRange;

    private int selectedAttack;

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    private Cursor cursor;

    private ActionMenu actMenu;
    private StatsMenu statMenu;
    private InventoryMenu inventoryMenu;

    private PlayerManager playerManager;
    private EnemyManager enemyManager;
    private Vector2 selectorPosition;

    private Enemy selectedEnemy;
    private int selected;

    ShapeRenderer shapeRenderer;

    public PlayerController(PlayerManager manager, EnemyManager eManager) {
        this.playerManager = manager;
        this.enemyManager = eManager;
        this.selectorPosition = new Vector2();
        shapeRenderer =  new ShapeRenderer();
    }

    public boolean isCursor() {
        return (this.playerManager.getCurrent() == null);
    }

    public void setupCursor() {
        cursor = new Cursor(new AnimatedSprite("assets/tilesets/cursor.png", 0, 0, 16, 16, 1, 0.1f));
    }

    public void setupMenus(){
        actMenu = new ActionMenu(80,90);
        inventoryMenu = new InventoryMenu(100, 200);
    }

    public ActionMenu getActMenu() {return this.actMenu;}
    public InventoryMenu getInventoryMenu() { return this.inventoryMenu; }

    public Cursor getCursor() {
        return this.cursor;
    }

    public int controlAttack(Input input, ArrayList<Enemy> availableEnemies) {
        if (Controls.isKeyTapped(input, Controls.A_BUTTON)) {
            // Return the selected index.
            int ret = this.selectedAttack;
            resetAttack();
            endTurn(this.playerManager.getCurrent());
            return ret;
        }
        if (Controls.isKeyTapped(input, Controls.B_BUTTON)) {
            resetAttack();
            return -1;
        }

        int max = availableEnemies.size() - 1;
        if (Controls.isKeyTapped(input, Controls.RIGHT)) {
            this.selectedAttack = this.selectedAttack >= max ? 0 : ++this.selectedAttack;
        }
        if (Controls.isKeyTapped(input, Controls.LEFT)) {
            this.selectedAttack = this.selectedAttack <= 0 ? max : --this.selectedAttack;
        }

        return -1;
    }

    private void resetAttack() {
        this.selectedAttack = 0;
        this.attacking = false;
        this.attackRange = 0;
    }

    public void control(Input input, Level currentLevel) {
        Player p = this.playerManager.getCurrent();

        if (p != null) {
            if (this.statMenu.isVisible()) {

            }
            if (this.actMenu.isVisible()) {
                String action = actionMenuMode(input);
                if (action.equals("End Turn")) {
                    Logger.debug("Ending turn");

                    endTurn(p);

                    return;
                }
                if(action.equals("Attack")) {
                    Logger.debug("Attacking");
                    int range = 0;
                    if(playerManager.getCurrent().getPlayerInventory().getEquip(Inventory.RHAND_SLOT) == null){
                        range = 1;      //sets default to fists if no weapon is equipped
                        System.out.println("Defaulting to fists with 1 range.");
                    }
                    else
                        range = playerManager.getCurrent().getPlayerInventory().getEquip(Inventory.RHAND_SLOT).range;

                    this.attacking = true;
                    this.attackRange = range;
                    this.selectedAttack = 0;

                    return;
                }
                /*else if(action.equals("Equip")) {
                    Logger.debug("PlayerController::control - Equiping");
                    this.actMenu.toggleVisible();
                    this.inventoryMenu.setInventory(p.getPlayerInventory());
                    this.inventoryMenu.toggleVisible();
                }*/
                else if (action.equals("Inventory")) {
                    this.actMenu.toggleVisible();
                    this.inventoryMenu.setInventory(p.getPlayerInventory());
                    this.inventoryMenu.toggleVisible();

                    return;
                } else if (!action.equals("")) {
                    this.actMenu.toggleVisible();
                    releasePlayer();
                }
            } else if (this.inventoryMenu.isVisible()) {

            } else {
                movePlayer(input, p, currentLevel);
            }
        } else {
            moveCursor(input);
        }

        // Move the player or select an enemy.
        if (Controls.isKeyTapped(input, Controls.A_BUTTON)) {
            Logger.debug("'A' button pressed.");
            // Check to see if we're over a player with our cursor.
            if (p == null) {
                // No player is selected, so we should check if a player is under the cursor.
                Boolean sel = selectPlayerOnCursor();
                Boolean eSel = selectEnemyOnCursor();

                if(sel == true)
                    selected = 1;
                else if(eSel == true)
                    selected = 2;
                else
                    selected = 0;
            } else {
                actMenu.toggleVisible();
                Logger.debug("entering action menu");
            }
        }

        // Reset the selector position.
        if (Controls.isKeyTapped(input, Controls.B_BUTTON)) {
            Logger.debug("'B' button pressed.");
            if (p != null) {
                if (this.inventoryMenu.isVisible())
                    this.inventoryMenu.toggleVisible();
                else if (this.actMenu.isVisible())
                    this.actMenu.toggleVisible();
                else if (p.x == (int)this.selectorPosition.x && p.y == (int)this.selectorPosition.y)
                    releasePlayer();
                else
                    resetPlayerToCursor(p);
            }
        }
        //Display enemy stats on screen
        if(selected == 2){
            Logger.debug("MaxHealth = " + selectedEnemy.maxHealth);
        }
    }

    private void endTurn(Player p) {
        p.endTurn();
        releasePlayer();
        closeAllMenus();

        if (checkTurnOver()) {
            Logger.debug("Turn for player is over.");
            this.turnComplete = true;
        }
    }

    private boolean checkTurnOver() {
        for (Player p : this.playerManager.getPlayers()) {
            if (!p.turnOver) return false;
        }
        return true;
    }

    private void resetPlayerToCursor(Player p) {
        p.x = (int)this.selectorPosition.x;
        p.y = (int)this.selectorPosition.y;
        p.resetMove();
    }

    private boolean selectPlayerOnCursor() {
        for (Player player : this.playerManager.getPlayers()) {
            if (player.x == cursor.x && player.y == cursor.y && !player.turnOver) {
                this.playerManager.setCurrent(player);
                this.selectorPosition.x = player.x;
                this.selectorPosition.y = player.y;
                player.resetMove();
                return true;
            }
        }
        return false;
    }

    private boolean selectEnemyOnCursor() {
        for(Enemy enemy : this.enemyManager.getEnemies()){
            if(enemy.x == cursor.x && enemy.y == cursor.y){
                Logger.debug("Enemy selected");
                selectedEnemy = enemy;
                return true;
            }
        }
        return false;
    }

    private void releasePlayer() {
        this.cursor.x = this.playerManager.getCurrent().x;
        this.cursor.y = this.playerManager.getCurrent().y;
        this.playerManager.setCurrent(null); // Nullify the current player.
    }

    private String actionMenuMode(Input input){
        if (Controls.isKeyTapped(input, Controls.UP)) {
            this.actMenu.movePointer(1);
        } else if (Controls.isKeyTapped(input, Controls.DOWN)) {
            this.actMenu.movePointer(-1);
        }

        if (Controls.isKeyTapped(input, Controls.A_BUTTON)) {
            String action = this.actMenu.getAction();
            Logger.debug("Returning action: " + action);
            return action;
        }

        return "";
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

    private void movePlayer(Input input, Player p, Level l) {
        if (Controls.isKeyTapped(input, Controls.UP)) {
            p.move(0, -1, l);
        } else if (Controls.isKeyTapped(input, Controls.DOWN)) {
            p.move(0, 1, l);
        } else if (Controls.isKeyTapped(input, Controls.RIGHT)) {
            p.move(1, 0, l);
        } else if (Controls.isKeyTapped(input, Controls.LEFT)) {
            p.move(-1, 0, l);
        }
    }

    private void closeAllMenus() {
        this.inventoryMenu.setVisible(false);
        this.actMenu.setVisible(false);
    }

    public boolean isTurnComplete() {
        return turnComplete;
    }

    public void resetTurn() {
        for (Player p : this.playerManager.getPlayers()) {
            p.turnOver = false;
        }
        this.turnComplete = false;
    }

    public boolean isAttacking() {
        return attacking;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public int getSelectedAttack() { return this.selectedAttack; }
}
