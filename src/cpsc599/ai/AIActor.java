package cpsc599.ai;

public abstract class AIActor {
    public static float STEP_TIME = 500; // 500 ms per step.

    /**
     * The time at which the next step should take place.
     */
    private float nextStep;

    /**
     * Attempt to step the actor through their turn.
     * @param time
     * @return <code>true</code> if the turn is over;
     *         <code>false</code> if the turn still has steps left to take.
     */
    public abstract boolean step(float time);

    /**
     * Sets up a move to a specified location on the level.
     * @param x
     * @param y
     */
    public abstract void moveTo(int x, int y);

    /**
     * Sets up an attack-move to a specified location.
     * @param x
     * @param y
     */
    public abstract void attackTo(int x, int y);

    /**
     * Determines what actions to take this turn.
     * @return
     */
    public abstract boolean decideTurn();
}
