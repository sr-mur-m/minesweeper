import java.util.Random;
/**
 * Class which offer matematics operations for the game.
 * @author  Alberto-Mur (sr.mur.m@gmail.com)
 * @version 1-July-2015
 */
public class Maths {
    public static byte max(int a, int b) {
        return (byte) ((a > b) ? a : b);
    }
    public static byte min(int a, int b) {
        return (byte) ((a < b) ? a : b);
    }
    public static byte random(Random rand, byte min, int max) {
        return (byte) (rand.nextInt(max)+min); // rg=[min,max]=[0,24].
    }
}
