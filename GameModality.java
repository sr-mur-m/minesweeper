/**
 * Modalities supported in the game.
 * @author  Alberto-Mur (sr.mur.m@gmail.com)
 * @version 1-July-2015
 */
public interface GameModality {
    public static final byte LVL_BEGINNER     = 1; // square=4×4   &&  5 mines.
    public static final byte LVL_INTERMEDIATE = 2; // square=8×8   && 20 mines.
    public static final byte LVL_ADVANCE      = 3; // square=11×11 && 50 mines.
    public static final byte LVL_PROFICIENTLY = 4; // square=11×11 && 60 mines.
}
