import java.util.HashMap;
import java.util.Observable;

/**
 * This class collects and provides some statistical data on the state 
 * of a field. It is flexible: it will create and maintain a counter 
 * for any class of object that is found within the field.
 */
public class FieldStats extends Observable {
    // Counters for each type of entity (fox, rabbit, etc.) in the simulation.
    private HashMap<Class, Counter> counters;
    // Whether the counters are currently up to date.
    private boolean countsValid;

    /**
     * Construct a FieldStats object.
     */
    public FieldStats()
    {
        // Set up a collection for counters for each type of animal that
        // we might find
        counters = new HashMap<Class, Counter>();
        countsValid = true;
    }

    /**
     * Returns the population counters
     * @return A map with a counter for each actor class
     */
    public HashMap<Class, Counter> getCurrentPopulationCounters(){
        return counters;
    }

     /**
     * Get details of what is in the field.
     * @return A string describing what is in the field.
     */
    public  int getPopulationNumbers(Field field,String name){
        
        if(!countsValid) {
            generateCounts(field);
        }
        for(Class key : counters.keySet()) {
            Counter info = counters.get(key);
            if (name.equals(info.getName()))
            {
            return info.getCount();
            }
            
        }
        return 0;
    }

    /**
     * Invalidate the current set of statistics; reset all 
     * counts to zero.
     */
    public void reset()
    {
        countsValid = false;
        for(Class key : counters.keySet()) {
            Counter count = counters.get(key);
            count.reset();
        }
        notifyObservers();
    }

    /**
     * Increment the count for one class of animal.
     * @param animalClass The class of animal to increment.
     */
    public void incrementCount(Class animalClass)
    {
        Counter count = counters.get(animalClass);
        if(count == null) {
            // We do not have a counter for this species yet.
            // Create one.
            count = new Counter(animalClass.getName());
            counters.put(animalClass, count);
        }
       
        count.increment();
        notifyObservers();
    }

    /**
     * Indicate that an animal count has been completed.
     */
    public void countFinished()
    {
        countsValid = true;
        notifyObservers();
    }

    /**
     * Determine whether the simulation is still viable.
     * I.e., should it continue to run.
     * @return true If there is more than one species alive.
     */
    public boolean isViable(Field field)
    {
        // How many counts are non-zero.
        int nonZero = 0;
        if(!countsValid) {
            generateCounts(field);
        }
        for(Class key : counters.keySet()) {
            if(Animal.class.isAssignableFrom(key)){
                Counter info = counters.get(key);
                if(info.getCount() > 0) {
                    nonZero++;
                }
            }
        }
        return nonZero > 1;
    }
    
    /**
     * Generate counts of the number of foxes and rabbits.
     * These are not kept up to date as foxes and rabbits
     * are placed in the field, but only when a request
     * is made for the information.
     * @param field The field to generate the stats for.
     */
    private void generateCounts(Field field)
    {
        reset();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                Object actor = field.getSpriteAt(row, col);
                if(actor != null) {
                    incrementCount(actor.getClass());
                    
                }
            }
        }
        countsValid = true;
    }

    /**
     * Overrides the notify to change all displayed data
     */
    public void notifyObservers() {
        setChanged();
        super.notifyObservers();
    }
}

