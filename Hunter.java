import java.util.List;
import java.awt.*;

/**
 * A simple model of a Hunter
 * Hunters move, shoot rabbits and foxes.
 * 
 * @author Jelko Jerbic, Martin Poelman, Eduard Hovinga
 *
 */
public class Hunter extends FieldSprite implements Actor {
    
    /**
     * Create a hunter.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Hunter(Field field, Location location)
    {
        super(field, location);      
        markColor = Color.green;
        field.setLocation(this.location, location, this);
    }
    /**
     * This is what the hunter does most of the time: it hunts for
     * rabbits and foxes.
     *
     */
    public void act(List<Animal> newHunter)
    {
        shootAnimal(location);

        // Always try to move to a free location.
        Location newLocation = field.freeAdjacentLocation(location,1);

        // See if it was possible to move.
        if(newLocation != null) {
            field.setLocation(location, newLocation, this);
            location = newLocation;
        }

    }
    


    

    
    /**
     * Tell the hunter to look for rabbits and foxes in a 5 square radius to its current location.
     * All animals in this rage are shot, except bears they are to big!
     * @param location Where in the field it is located.
     *
     */
    private void shootAnimal(Location location)
    {
        List<Location> adjacent = field.adjacentLocations(location,5);
        for (Location anAdjacent : adjacent) {
            FieldSprite sprite = field.getSpriteAt(anAdjacent);
            if (sprite instanceof Animal && !(sprite instanceof Bear)) {
                Animal animal = (Animal) sprite;
                if (animal.isAlive()) {
                    animal.setDead();
                }
            }
        }
    }
    
    /**
     * @return if the hunter is alive
     */
    public boolean isAlive(){
        return true;
    }
    

}

