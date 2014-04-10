package cpsc599.managers;

import cpsc599.states.State;
import cpsc599.util.Logger;

import java.util.HashMap;

/**
 * Global state manager for all game-states.
 */
public class StateManager {
    private HashMap<String, State> stateList;
    public State current;

    public StateManager() {
        Logger.debug("Initializing StateManager.");
        stateList = new HashMap<String, State>();
    }

    /**
     * Adds a state to the state dictionary. Indexed based on stateName.
     * @param stateName Name of the state. Index by which the state is accessed.
     * @param state An instance of the state.
     * @return true on successful addition, false if the state name is already taken.
     */
    public boolean addState(String stateName, State state) {
        if (stateList.containsKey(stateName)) {
            Logger.warn("Unable to add state: " +
                    stateName + " as that state already exists.");
            return false;
        }

        Logger.debug("Adding state: " + stateName);
        stateList.put(stateName, state);
        return true;
    }

    /**
     * Sets the current state to the specified state index.
     * @param stateName The index of the state in the state dictionary.
     * @return The new current state. null if the state index does not exist in the dictionary.
     */
    public State setState(String stateName) {
        if (!stateList.containsKey(stateName)) {
            Logger.warn("Unable to get state as it does not exist: " + stateName);
            return null;
        }

        Logger.debug("Setting state to: " + stateName);
        this.current = stateList.get(stateName);
        return this.current;
    }
    public State setState(String stateName, String level) {
        if (!stateList.containsKey(stateName)) {
            Logger.warn("Unable to get state as it does not exist: " + stateName);
            return null;
        }

        Logger.debug("Setting state to: " + stateName);
        this.current = stateList.get(stateName);
        return this.current;
    }
}
