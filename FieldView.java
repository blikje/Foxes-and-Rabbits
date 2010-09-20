import javax.swing.*;
import java.awt.*;

/**
 * Provide a graphical view of a rectangular field. This is
 * a nested class (a class defined inside a class) which
 * defines a custom component for the user interface. This
 * component displays the field.
 * This is rather advanced GUI stuff - you can ignore this
 * for your project if you like.
 */
public class FieldView extends JPanel{

    // Colors used for empty locations.
    private static final Color EMPTY_COLOR = Color.white;

    // Sizes used to mark infected actors
    private static final int MIN_MARK_SIZE = 4;
    private static final int PREFERRED_MARK_SIZE = 8;
    private static final int MAX_MARK_SIZE = 14;

    // fields used to display the FieldView properly
    private int gridWidth, gridHeight;
    Dimension size;
    private Graphics g;
    private Image fieldImage;
    private int width, height;
    private int xOffset, yOffset;
    private int markSize;
    boolean resized;

    /**
     * Create a new FieldView component.
     * @param height the fieldviews height
     * @param width the fieldviews width
     */
    public FieldView(int height, int width)
    {
        gridHeight = height;
        gridWidth = width;
        size = new Dimension(0, 0);
        markSize = PREFERRED_MARK_SIZE;
        resized = true;
    }

    /**
     * Tell the GUI manager how big we would like it to be.(preferred)
     */
    public Dimension getPreferredSize()
    {
        return new Dimension(gridWidth * markSize,
                             gridHeight * markSize);
    }

    /**
     * Tell the GUI manager how big we would like it to be.(maximum)
     */
    public Dimension getMaximumSize()
    {
        return new Dimension(gridWidth * MAX_MARK_SIZE,
                             gridHeight * MAX_MARK_SIZE);
    }

    /**
     * Tell the GUI manager how big we would like it to be. (minimum)
     */
    public Dimension getMinimumSize()
    {
        return new Dimension(gridWidth * MIN_MARK_SIZE,
                             gridHeight * MIN_MARK_SIZE);
    }

    /**
     * Prepare for a new round of painting. Since the component
     * may be resized, compute the scaling factor again.
     */
    public void preparePaint(){
        Dimension currentSize = getSize();
        int containerWidth = currentSize.width;
        int containerHeight = currentSize.height;

        boolean widthOverflow = (containerHeight/gridHeight) * gridWidth > containerWidth;
        markSize = widthOverflow ? ((containerWidth)/gridWidth) : ((containerHeight)/gridHeight);
        markSize = markSize < MIN_MARK_SIZE ? MIN_MARK_SIZE : markSize > MAX_MARK_SIZE ? MAX_MARK_SIZE : markSize;

        width = (gridWidth * markSize);
        height = (gridHeight * markSize);

        xOffset = (currentSize.width-width)/2;
        yOffset = (currentSize.height-height)/2;

        size = getSize();
        fieldImage = createImage(width, height);
        g = fieldImage.getGraphics();
    }

    /**
     * Paint on grid location on this field in a given color.
     * @param x the x position
     * @param y the y position
     * @param color the color
     * @param mark Indicates if the square should be marked
     */
    public void drawMark(int x, int y, Color color, boolean mark)
    {
        g.setColor(color);
        g.fillRect((x * markSize), (y * markSize), markSize-1, markSize-1);
        if(mark){
            g.setColor(Color.red);
            g.fillRect((x * markSize) + (markSize/3), (y * markSize) + (markSize/3), markSize/3, markSize/3);
        }
    }

    /**
     * Draws a small mark inside an actor
     * @param field the field to draw the mark on
     * @param stats to recalculate the stats
     */
    public void drawMarks(Field field, FieldStats stats) {
        stats.reset();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                FieldSprite sprite = field.getSpriteAt(row, col);
                if(sprite != null) {
                    stats.incrementCount(sprite.getClass());
                    boolean drawMark = sprite instanceof Animal && sprite.mark();
                    drawMark(col, row, sprite.getMarkColor(), drawMark);
                } else {
                    drawMark(col, row, EMPTY_COLOR, false);
                }
            }
        }
        stats.countFinished();
    }

    /**
     * The field view component needs to be redisplayed. Copy the
     * internal image to screen.
     */
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if(fieldImage != null) {
            g.drawImage(fieldImage, xOffset, yOffset, width, height, null);
        }
    }

    /**
     * Gets the location in the grid with a given point inside te field image
     * @param p The point from which a location should be calculated
     * @return The resulting location, null if none was found at the point
     */
    public Location calculateLocation(Point p){
        return calculateLocation(p.x, p.y);
    }

    /**
     * Gets the location in the grid with a given x and y coordinate inside te field image
     * @param px Horizontal coord from which a location should be calculated
     * @param py Vertical coord from which a location should be calculated
     * @return The resulting location, null if none was found at the point
     */
    public Location calculateLocation(int px, int py){
        if(px < xOffset || px >= (xOffset + width) || py < yOffset || py >= yOffset + height)
            return null;
        return new Location((py-yOffset)/markSize, (px-xOffset)/markSize);
    }
}