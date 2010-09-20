import java.util.*;

/**
 * Interface Class for all things that can Act.
 */
public interface Actor {
    
    /**
     * Let the Actor Act
     * @param newAnimals Newborn animals
     */
    public abstract void act(List<Animal> newAnimals);
    
    /**
     * Reflects the alive state of the actor
     * @return True if the Actor is alive, False if the Actor is dead
     */
    public abstract boolean isAlive(); 
   
}
