import java.util.*;

/**
 * Represent a rectangular grid of field positions.
 * Each position is able to store a single animal.
 */
public class Field {

    // A random number generator for providing random locations.
    private static final Random rand = Randomizer.getRandom();
    // The depth and width of the field.
    private int depth, width;
    // Storage for the field sprites.
    private FieldSprite[][] field;

    /**
     * Represent a field of the given dimensions.
     * @param depth The depth of the field.
     * @param width The width of the field.
     */
    public Field(int depth, int width){
        this.depth = depth;
        this.width = width;
        field = new FieldSprite[depth][width];
    }
    
    /**
     * Empty the field.
     */
    public void clear(){
        for(int row = 0; row < depth; row++) {
            for(int col = 0; col < width; col++) {
                field[row][col] = null;
            }
        }
    }
    
    /**
     * Clear the given location.
     * @param location The location to clear.
     */
    public void clear(Location location){
        field[location.getRow()][location.getCol()] = null;
    }
    
    /**
     * Place an FieldSprite at the given location.
     * If there is already an FieldSprite at the location it will
     * be lost.
     * @param sprite The FieldSprite to be placed.
     * @param location The location to place the unit
     */
    public void place(FieldSprite sprite, Location location){
        field[location.getRow()][location.getCol()] = sprite;
    }
    
    /**
     * Return the FieldSprite at the given location, if any.
     * @param location Where in the field.
     * @return The FieldSprite at the given location, or null if there is none.
     */
    public FieldSprite getSpriteAt(Location location){
        return getSpriteAt(location.getRow(), location.getCol());
    }
    
    /**
     * Return the FieldSprite at the given location, if any.
     * @param row The desired row.
     * @param col The desired column.
     * @return The FieldSprite at the given location, or null if there is none.
     */
    public FieldSprite getSpriteAt(int row, int col){
        if(row < field.length && col < field[row].length){
            return field[row][col];
        }
        return null;
    }
    
    /**
     * Get a shuffled list of the free adjacent locations.
     * @param location Get locations adjacent to this.
     * @param depth How far around the current location should we look
     * @return A list of free adjacent locations.
     */
    public List<Location> getFreeAdjacentLocations(Location location,int depth){
        List<Location> free = new LinkedList<Location>();
        List<Location> adjacent = adjacentLocations(location,depth);
        for(Location next : adjacent) {
            if(getSpriteAt(next) == null) {
                free.add(next);
            }
        }
        return free;
    }
    
    /**
     * Try to find a free location that is adjacent to the
     * given location. If there is none, return null.
     * The returned location will be within the valid bounds
     * of the field.
     * @param location The location from which to generate an adjacency.
     * @return A valid location within the grid area.
     */
    public Location freeAdjacentLocation(Location location,int depth){
        // The available free ones.
        List<Location> free = getFreeAdjacentLocations(location,depth);
        if(free.size() > 0) {
            return free.get(0);
        }
        else {
            return null;
        }
    }

   
     /**
     * Return a shuffled list of locations adjacent to the given one.
     * The list will not include the location itself.
     * All locations will lie within the grid.
     * @param location The location from which to generate adjacencies.
     * @param range The range indicates how far away from the location we want to reach
     * @return A list of locations adjacent to that given.
     */
    public List<Location> adjacentLocations(Location location, int range){
        // The list of locations to be returned.
        List<Location> locations = new LinkedList<Location>();
        if(location != null) {
            int row = location.getRow();
            int col = location.getCol();
            for(int roffset = -range; roffset <= range; roffset++) {
                int nextRow = row + roffset;
                if(nextRow >= 0 && nextRow < depth) {
                    for(int coffset = -range; coffset <= range; coffset++) {
                        int nextCol = col + coffset;
                        // Exclude invalid locations and the original location.
                        if(nextCol >= 0 && nextCol < width && (roffset != 0 || coffset != 0)) {
                            locations.add(new Location(nextRow, nextCol));
                        }
                    }
                }
            }
            
            // Shuffle the list. Several other methods rely on the list
            // being in a random order.
            Collections.shuffle(locations, rand);
        }
        return locations;
    }

    /**
     * Return the depth of the field.
     * @return The depth of the field.
     */
    public int getDepth(){
        return depth;
    }
    
    /**
     * Return the width of the field.
     * @return The width of the field.
     */
    public int getWidth(){
        return width;
    }

    /**
     * Gets a random free locations in the field if there is any
     * @return A random free location
     */
    public Location getFreeRandomLocation(){
        List<Location> locations = new ArrayList<Location>();
        for(int r = 0; r < field.length; r++){
            for(int c = 0; c < field[r].length; c++){
                if(field[r][c] == null) locations.add(new Location(r, c));
            }
        }
        return locations.isEmpty() ? null : locations.get(rand.nextInt(locations.size()));
    }

    /**
     * Set a sprite on the given location
     *
     * @param location The destination location
     * @param sprite The sprite to be placed
     */
    public void setLocation(Location location, FieldSprite sprite){
        setLocation(null, location, sprite);
    }

    /**
     * Set a sprite on the given location
     *
     * @param oldLocation The sprite's old location
     * @param location The destination location
     * @param sprite The sprite to be moved
     */
    public void setLocation(Location oldLocation, Location location, FieldSprite sprite){
        if(location != null){
            if(oldLocation != null) {
                clear(oldLocation);
            }
            place(sprite, location);
        }
    }
}

