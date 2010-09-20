import java.awt.*;

/**
 * A road is a sprite, it's drawn on the field and can't be stepped on
 * by acting sprite. The road itself is not an actor, it remains stationary
 */
public class Road extends FieldSprite {

	/**
	 * Constructor.
	 * @param field the Field
	 * @param location the Location
	 */
    public Road(Field field, Location location) {
        super(field, location);
        markColor = Color.darkGray;
    }
}
