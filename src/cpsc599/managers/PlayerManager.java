package cpsc599.managers;

import com.badlogic.gdx.math.Vector2;
import cpsc599.assets.Player;
import cpsc599.util.Logger;

import java.util.ArrayList;

/**
 * Manages the player controllable characters that are currently in the game.
 */
public class PlayerManager {
    private ArrayList<Player> playerList;
    private Player currentPlayer;

    public PlayerManager() {
        playerList = new ArrayList<Player>();
    }

    public void addPlayer(Player p) {
        Logger.debug("Adding player '" + p + "'.");
        playerList.add(p);
        //if (currentPlayer == null) setCurrent(0); // Commented to allow for cursor selection.
    }

    public void removePlayer(int i) {
        Logger.debug("Player " + i +" has died...");
        playerList.remove(i);
    }

    public Player[] getPlayers() {
        return playerList.toArray(new Player[playerList.size()]);
    }

    public Player getPlayer(int index) {
        if (playerList.size() < index) {
            Logger.error("index was out of range.");
            return null;
        }

        return playerList.get(index);
    }

    public Player setCurrent(int index) {
        currentPlayer = this.getPlayer(index);
        Logger.debug("Current player set to " + currentPlayer);
        return currentPlayer;
    }

    public Player getCurrent() {
        return currentPlayer;
    }

    public void setCurrent(Player p) {
        if (p != null && !this.playerList.contains(p)) {
            Logger.error("Attempted to set current player to player that did not exist.");
            return;
        }

        Logger.debug("Current player set to " + p);
        this.currentPlayer = p;
    }

    /**
     * Gets the player at the specified (x, y) coordinates.
     * @param position Coordinate value on the map.
     * @return The player at the specified position.
     *         <code>null</code> if no player was found.
     */
    public Player getPlayerAtPosition(Vector2 position) {
        for (Player p : playerList) {
            if (p.x == (int)position.x && p.y == (int)position.y) return p;
        }

        return null;
    }

    /**
     * Returns the nearest player to the specified position, based on vector distances (no pathfinding).
     * @param position The position from which to check the nearest player ((x, y))
     * @return The nearest player.
     *         <code>null</code> if no players exist.
     */
    public Player getNearest(Vector2 position) {
        float minDist = 10000f;
        Player minPlayer = null;
        for (Player p : playerList) {
            Vector2 pos = new Vector2(p.x, p.y);
            float dist = pos.dst(position);

            if (dist < minDist) {
                minDist = dist;
                minPlayer = p;
            }
        }

        return minPlayer;
    }

    public Player getPlayerAtPosition(int x, int y) {
        return getPlayerAtPosition(new Vector2(x, y));
    }

    public Player getPlayerByName(String name) {
        for (Player p : playerList) {
            if (p.getName().equals(name)) return p;
        }

        return null;
    }

    public void reset() {
        Logger.debug("Clearing PlayerController.");
        playerList.clear();
        currentPlayer = null;
    }

    public void removePlayer(Player obj) {
        Logger.debug("Player " + obj.getName() + " removed.");
        this.playerList.remove(obj);
    }
}
