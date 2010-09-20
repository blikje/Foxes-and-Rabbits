import java.util.List;
import java.util.Random;
import java.awt.*;

/**
 * A simple model of a rabbit.
 * Rabbits age, move, breed, and die.
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2008.03.30
 */
public class Rabbit extends Animal
{
    // Characteristics shared by all rabbits (static fields).

    // The age at which a rabbit can start to breed.
    public static int BREEDING_AGE = 5;
    // The age to which a rabbit can live.
    public static int MAX_AGE = 40;
    // The likelihood of a rabbit breeding.
    public static double BREEDING_PROBABILITY = 0.15;
    // The maximum number of births.
    public static int MAX_LITTER_SIZE = 4;
    // The time a rabbit with myxomatosis has left until it dies of the disease
    private static final int MYXOMATOSIS_MORTALITY_AGE = 5;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    public static int MYXO_GENE_PERCENTAGE = 90;
    // Individual characteristics (instance fields).
    // Indicates if the rabbit has myxomatosis
    private boolean myxomatosisGene;
    private boolean myxomatosisInfected;
    
    // The rabbit's age.
    private int age;

    /**
     * Create a new rabbit. A rabbit may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the rabbit will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Rabbit(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        markColor = Color.orange;
        myxomatosisGene = rand.nextInt(100) <= MYXO_GENE_PERCENTAGE;
        myxomatosisInfected = false;
        age = 0;
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
        }
    }
    
    /**
     * This is what the rabbit does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     * @param newRabbits A list to add newly born rabbits to.
     */
    public void act(List<Animal> newRabbits)
    {
        incrementAge();
        if(isAlive()) {
            giveBirth(newRabbits);            
            // Try to move into a free location.
            Location newLocation = getField().freeAdjacentLocation(getLocation(),1);
            if(newLocation != null) {
                setLocation(newLocation);
                if(myxomatosisInfected)
                    infectRabbits();
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }

    /**
     * Infects all adjacent rabbits if they are able to get the disease.
     */
    private void infectRabbits() {
        List<Location> locations = getField().adjacentLocations(getLocation(), 1);
        FieldSprite sprite;
        Rabbit rabbit;
        for(Location location : locations){
            sprite = getField().getSpriteAt(location);
            if(sprite instanceof Rabbit){
                rabbit = (Rabbit) sprite;
                rabbit.setMyxomatosisInfected(rabbit.hasMyxomatosisGene());
            }
        }
    }

    /**
     * Increase the age.
     * This could result in the rabbit's death.
     */
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }
    
    /**
     * Check whether or not this rabbit is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newRabbits A list to add newly born rabbits to.
     */
    private void giveBirth(List<Animal> newRabbits)
    {
        // New rabbits are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation(),1);
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Rabbit young = new Rabbit(false, field, loc);
            newRabbits.add(young);
        }
    }
        
    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    private int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }

    /**
     * A rabbit can breed if it has reached the breeding age.
     * @return true if the rabbit can breed, false otherwise.
     */
    private boolean canBreed()
    {
        return age >= BREEDING_AGE;
    }

    /**
     * If the rabbit has this gene it can get dissease
     * @return true if the rabbit has the gene
     */
    public boolean hasMyxomatosisGene() {
        return myxomatosisGene;
    }

    /**
     * Give this rabbit the dissease
     * @param myxomatosisInfected true to make this rabbit sick, false to cure it.
     */
    public void setMyxomatosisInfected(boolean myxomatosisInfected) {
        if(myxomatosisInfected && MAX_AGE - MYXOMATOSIS_MORTALITY_AGE > age){
            age = MAX_AGE - 5;
        }
        this.myxomatosisInfected = myxomatosisInfected;
    }

    /**
     * @return true if the rabbit is infected.
     */
    public boolean mark(){
        return myxomatosisInfected;
    }
}

