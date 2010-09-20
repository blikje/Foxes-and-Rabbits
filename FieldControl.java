import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

/**
 * FieldControl takes care of mouse events, it's mainly responsible for drawing the roads.
 */
public class FieldControl extends MouseAdapter {
    private FieldView fieldView;
    private Field field;

    /**
     * Constructor
     * @param fieldView The view that renders the field
     * @param field The model that contains field data
     */
    public FieldControl(FieldView fieldView, Field field) {
        this.fieldView = fieldView;
        this.field = field;

        this.fieldView.addMouseListener(this);
        this.fieldView.addMouseMotionListener(this);
    }

    /**
     * Puts a road on the given location, but only if there's not a hunter on it. Animals on the given location
     * are killed.
     * @param location The destination location
     */
    public void addRoad(Location location){
        if(location != null){
            FieldSprite sprite = field.getSpriteAt(location);
            if(!(sprite instanceof Hunter)) {
                if(sprite instanceof Animal){
                    ((Animal) sprite).setDead();
                }
                field.clear(location);
                field.setLocation(location, new Road(field, location));
            }
        }
    }

    /**
     * Drawing marks starts as soon as the mouse left mousebutton is pressed
     * @param event The fired event
     */
    public void mousePressed(MouseEvent event) {
        if(event.getButton() == MouseEvent.BUTTON1)
            addRoad(fieldView.calculateLocation(event.getPoint()));
    }

    /**
     * Dragging over locations keeps drawing them.
     * @param event The fired event
     */
    public void mouseDragged(MouseEvent event) {
        if(event.getButton() == MouseEvent.BUTTON1)
            addRoad(fieldView.calculateLocation(event.getPoint()));
    }
}
