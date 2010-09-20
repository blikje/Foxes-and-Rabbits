import java.util.Random;
import java.util.List;
/**
 * Creates the Initial Population on the Field
 * 
 * @author Jelko Jerbic, Martin Poelman, Eduard Hovinga
 *
 */
public class Populator
{
    // instance variables - replace the example below with your own
    private SimulationControl simulator;
    private Field field;
    private List<Actor> actors;
    //The probability that a rabbit will be created in any given grid position.
    private static double RABBIT_CREATION_PROBABILITY = 0.08;
    //The probability that a fox will be created in any given grid position.
    private static double FOX_CREATION_PROBABILITY = 0.02;
    //The probability that a bear will be created in any given grid position
    private static double BEAR_CREATION_PROBABILITY = 0.0005;
    //The number of hunters to start with
    private static double NUMBER_OF_HUNTERS = 1;
    //The amount of rabbits that have myxomatosis
    private static double MYXOMATOSIS_INFECTION_PROBABILITY = 0.004;
    /**
     * Constructor for objects of class Populate
     * @param simulator The simulator control class
     * @param field The field which the populator is populating
     * @param actors The list with actors
     */
    public Populator(SimulationControl simulator,Field field,List<Actor> actors)
    {
        // initialise instance variables
        this.simulator=simulator;
        this.field=field;
        this.actors=actors;
    }

    /**
     * Set the Population variable and check if it's allowed
     * @param rabbit chance of rabbit creation
     * @param fox chance of fox creation
     * @param bear chance of bear creation
     * @param hunter number of hunters
     * @return true if successful
     */
    public boolean setPopulation(double rabbit,double fox, double bear, double hunter){
        if (rabbit<0 || fox<0 || bear<0 || hunter<0){
            return false;
        }

        RABBIT_CREATION_PROBABILITY = rabbit/100;
        FOX_CREATION_PROBABILITY = fox/100;
        BEAR_CREATION_PROBABILITY = bear/100;
        NUMBER_OF_HUNTERS = hunter;
        return true;
    }

    /**
     * Randomly populate the field with foxes and rabbits.
     * Using the according variable's
     */
    public void populate()
    {
        Random rand = Randomizer.getRandom();
        field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                if(rand.nextDouble() <= FOX_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Fox fox = new Fox(true, field, location);
                    actors.add(fox);
                }
                else if(rand.nextDouble() <= RABBIT_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Rabbit rabbit = new Rabbit(true, field, location);
                    rabbit.setMyxomatosisInfected(rand.nextDouble() <= MYXOMATOSIS_INFECTION_PROBABILITY);                    
                    actors.add(rabbit);
                }
                else if(rand.nextDouble() <= BEAR_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Bear bear = new Bear(true, field, location);
                    actors.add(bear);
                } // else leave the location empty.
            }
        }
    
        //Add the Hunters to the Field
        int hunterAmount = 0;
        while(hunterAmount < NUMBER_OF_HUNTERS){
            Hunter hunter = new Hunter(field,field.getFreeRandomLocation());
            actors.add(hunter);
            hunterAmount++;
        }
    }
}
