package cpsc599.controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import cpsc599.assets.*;
import cpsc599.items.Inventory;
import cpsc599.managers.EnemyManager;
import cpsc599.managers.GameEntityManager;
import cpsc599.managers.PlayerManager;
import cpsc599.menus.ActionMenu;
import cpsc599.menus.GlobalMenu;
import cpsc599.menus.InventoryMenu;
import cpsc599.menus.StatsMenu;
import cpsc599.util.Controls;
import cpsc599.util.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Acts as a controller for all players.
 */
public class PlayerController {
    private boolean turnComplete;
    private boolean attacking;
    private boolean inspecting;
    private boolean using;

    private int attackRange;

    private int selectedUnit;

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    private Cursor cursor;

    private ActionMenu actMenu;
    private StatsMenu statsMenu;
    private InventoryMenu inventoryMenu;
    private GlobalMenu globalMenu;
    private DynamicDialogue dynamicDialogue;

    private PlayerManager playerManager;
    private EnemyManager enemyManager;
    private GameEntityManager gameEntityManager;
    private Vector2 selectorPosition;

    private Enemy selectedEnemy;
    private int selected;

    ShapeRenderer shapeRenderer;

    public PlayerController(PlayerManager manager, EnemyManager eManager) {
        this.playerManager = manager;
        this.enemyManager = eManager;
        this.selectorPosition = new Vector2();
        shapeRenderer =  new ShapeRenderer();

        this.inspecting = false;
        this.using = false;
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
        statsMenu = new StatsMenu(80, 90, null);
        globalMenu = new GlobalMenu(80, 90);
        dynamicDialogue = new DynamicDialogue();
        dynamicDialogue.setPosition(new Vector2(390, 0));
    }

    public ActionMenu getActMenu() {return this.actMenu;}
    public StatsMenu getStatsMenu() {return  this.statsMenu;}
    public InventoryMenu getInventoryMenu() { return this.inventoryMenu; }
    public GlobalMenu getGlobalMenu() {return this.globalMenu;}

    public Cursor getCursor() {
        return this.cursor;
    }

    public int controlAttack(Input input, ArrayList<Enemy> availableEnemies) {
        if (Controls.isKeyTapped(input, Controls.A_BUTTON)) {
            // Return the selected index.
            int ret = this.selectedUnit;
            resetAttack();
            endTurn(this.playerManager.getCurrent());
            return ret;
        }
        if (Controls.isKeyTapped(input, Controls.B_BUTTON)) {
            resetAttack();
            //availableEnemies.clear(); might be neater to have this in but works otherwise
            return -1;
        }

        int max = availableEnemies.size() - 1;
        if (Controls.isKeyTapped(input, Controls.RIGHT)) {
            this.selectedUnit = this.selectedUnit >= max ? 0 : ++this.selectedUnit;
        }
        if (Controls.isKeyTapped(input, Controls.LEFT)) {
            this.selectedUnit = this.selectedUnit <= 0 ? max : --this.selectedUnit;
        }
        if (Controls.isKeyTapped(input, Controls.UP)) {

        }
        if (Controls.isKeyTapped(input, Controls.DOWN)) {

        }

        return -1;
    }

    public int controlSelect(Input input, List<GameEntity> entities) {
        if (Controls.isKeyTapped(input, Controls.A_BUTTON)) {
            // Return the selected index.
            int ret = this.selectedUnit;
            this.selectedUnit = 0;
            this.using = false; this.inspecting = false;
            endTurn(this.playerManager.getCurrent());
            return ret;
        }
        if (Controls.isKeyTapped(input, Controls.B_BUTTON)) {

            this.selectedUnit = 0;
            this.using = false; this.inspecting = false;
            return -1;
        }

        int max = entities.size() - 1;
        if (Controls.isKeyTapped(input, Controls.RIGHT)) {
            this.selectedUnit = this.selectedUnit >= max ? 0 : ++this.selectedUnit;
        }
        if (Controls.isKeyTapped(input, Controls.LEFT)) {
            this.selectedUnit = this.selectedUnit <= 0 ? max : --this.selectedUnit;
        }
        return -1;
    }

    private void resetAttack() {
        this.selectedUnit = 0;
        this.attacking = false;
        this.attackRange = 0;
    }

    public void control(Input input, Level currentLevel) {
        Player p = this.playerManager.getCurrent();
        this.dynamicDialogue.setVisibility(false);

        if (p != null) {
            if(this.statsMenu.isVisible()) {
                if (Controls.isKeyTapped(input, Controls.B_BUTTON)) {
                    statsMenu.setVisible(false);
                    Logger.debug("Closing stat menu");
                }
                return;
            }
            if (this.actMenu.isVisible()) {
                String action = actionMenuMode(input);
                if (action.equals("End Turn")) {
                    Logger.debug("Ending turn");

                    endTurn(p);

                    return;
                }
               else if(action.equals("Attack")) {
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
                    this.selectedUnit = 0;

                    return;
                }
                else if(action.equals("Stats")) {
                    statsMenu.setActor(p);
                    statsMenu.setVisible(true);
                }else if (action.equals("Inspect")) {
                    Logger.debug("Inspecting");
                    this.inspecting = true;
                    this.selectedUnit = 0;
                    return;

                } else if (action.equals("Use")) {
                    this.using = true;
                    this.selected = 0;
                    return;
                } else if (!action.equals("")) {
                    this.actMenu.toggleVisible();
                    releasePlayer();
                }
            } else if (this.inventoryMenu.isVisible()) {

            } else {
                movePlayer(input, p, currentLevel);
                dynamicDialogue.setText("Move " + p.curMove + "/" + p.maxMove);
                dynamicDialogue.setVisibility(true);
            }
        }
        else if(selectedEnemy != null){
            if (this.statsMenu.isVisible()) {
                controlStatsMenu(input);
                return;
            }
            dynamicDialogue.setVisibility(false);
        }
        else {
            dynamicDialogue.setVisibility(false);
            if(this.globalMenu.isVisible()) {
                String action = globalMenuMode(input);
                if(action.equals("End turn")) {
                    Logger.debug("Ending turn for all players");

                    for(int i = 0; i < playerManager.getPlayers().length; i++) {
                        endTurn(playerManager.getPlayer(i));
                    }
                    this.globalMenu.setVisible(false);
                }
                else if(Controls.isKeyTapped(input, Controls.B_BUTTON))
                    globalMenu.setVisible(false);
                    return;
            }
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

                if(sel)
                    selected = 1;
                else if(eSel)
                    selected = 2;
                else {
                    selected = 0;
                    globalMenu.setVisible(true);
                }
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
                else if(this.globalMenu.isVisible())
                       this.globalMenu.toggleVisible();
                else if (p.x == (int)this.selectorPosition.x && p.y == (int)this.selectorPosition.y)
                    releasePlayer();
                else
                    resetPlayerToCursor(p);
            }
        }
        //Display enemy stats on screen
        /*if(selected == 2){
            Logger.debug("MaxHealth = " + selectedEnemy.maxHealth);
        }*/
    }

    /** restore all players to maximum health **/
    public void healAll() {
        for (Player p : playerManager.getPlayers()) {
            p.heal(1000);
        }
    }

    public void endTurn(Player p) {
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
                statsMenu.setActor(enemy);
                statsMenu.setVisible(true);
                selectedEnemy = enemy;
                return true;
            }
        }
        return false;
    }

    private void releasePlayer() {
        if(this.playerManager.getCurrent() == null)
            return;
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

    private String globalMenuMode(Input input){
        if (Controls.isKeyTapped(input, Controls.UP)) {
            this.globalMenu.movePointer(1);
        } else if (Controls.isKeyTapped(input, Controls.DOWN)) {
            this.globalMenu.movePointer(-1);
        }

        if (Controls.isKeyTapped(input, Controls.A_BUTTON)) {
            String action = this.globalMenu.getAction();
            Logger.debug("Returning action: " + action);
            return action;
        }
        else
            return "";
    }

    private void controlStatsMenu(Input input){
        if(Controls.isKeyTapped(input, Controls.B_BUTTON)){
            statsMenu.toggleVisible();
            selectedEnemy = null;
        }
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
        int movex = 0;
        int movey = 0;
        if (Controls.isKeyTapped(input, Controls.UP)) {
            movex = 0; movey = -1;
        } else if (Controls.isKeyTapped(input, Controls.DOWN)) {
            movex = 0; movey = 1;
        } else if (Controls.isKeyTapped(input, Controls.RIGHT)) {
            movex = 1; movey = 0;
        } else if (Controls.isKeyTapped(input, Controls.LEFT)) {
            movex = -1; movey = 0;
        }

        // If we have a move and there's no enemy in our space, we can move.
        int newx = p.x + movex, newy = p.y + movey;
        if ((movex != 0 || movey != 0) && enemyManager.getEnemyAtPosition(newx, newy) == null &&
                !gameEntityManager.checkCollision(newx, newy)) {
            p.move(movex, movey, l);
        }
    }

    private void closeAllMenus() {
        this.inventoryMenu.setVisible(false);
        this.actMenu.setVisible(false);
        this.statsMenu.setVisible(false);
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

    public int getSelectedUnit() { return this.selectedUnit; }

    public void setGameEntityManager(GameEntityManager gameEntityManager) {
        this.gameEntityManager = gameEntityManager;
    }

    public boolean isInspecting() {
        return inspecting;
    }

    public boolean isUsing() {
        return using;
    }

    public DynamicDialogue getDynamicDialogue() {
        return dynamicDialogue;
    }

}
