package cpsc599.managers;

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
}
