import java.util.Random;

/**
 * Provide control over the randomization of the simulation.
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2008.03.30
 */
public class Randomizer {
    /**
     * Provide a random generator.
     * @return A Random object.
     */
    public static Random getRandom(){
        return new Random();
    }
}
