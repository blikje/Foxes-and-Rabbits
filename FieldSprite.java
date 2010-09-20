import java.awt.*;

public abstract class FieldSprite {
	// The Unit's Location
    protected Location location;
    // The Unit's field.
    protected Field field;
    // The unit's color
    protected Color markColor;

    /**
     * Create a FieldSprite on the given Field and Location
     * @param field The field to create
     * @param location The location to set the Field
     */
    public FieldSprite(Field field, Location location){
        this.field = field;
        this.location = location;
        setLocation(location);
        markColor = Color.gray; //Default color for items that are not colored
    }

    /**
     * Returns the location of this fieldunit
     * @return the location of this fieldunit
     */

    public Location getLocation(){
        return location;
    }

    /**
     * Sets the location of this fieldunit
     * @param location The location to set on this fieldunit
     */
    public void setLocation(Location location){
        field.setLocation(this.location, location, this);
        this.location = location;
    }

    /**
     * Reveals if the sprite should be marked while it's drawn. Default is false
     * @return True if the sprite should be marked
     */
    public boolean mark(){
        return false;
    }

    /**
     * Get the color this sprite should have
     * @return The Color for this sprite
     */
    public Color getMarkColor(){
        return markColor;
    }
}
