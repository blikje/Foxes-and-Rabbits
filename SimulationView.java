import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * A graphical graphical user interface for the simulator. Contains the grid which represents
 * the simulated forrest, control panels and statistic views.
 */
public class SimulationView extends JFrame {

    private FieldView fieldView;
    // A statistics object computing and storing simulation information
    private FieldStats stats;

    /**
     * Create a view of the given width and height.
     * @param height hight of the grid.
     * @param width width of the grid.
     * @param field the Field class.
     * @param simulation the Simulation class.
     */
    public SimulationView(int height, int width, Field field, Simulation simulation, SimulationControl simulationControl)
    {
        super("Fox and Rabbit Simulation");

        stats = new FieldStats();

        HistoView barThingy = new HistoView("Field Statistics", stats, field);
        barThingy.pack();
        GraphView lineThingy = new GraphView("Field Statistics", stats, field);
        lineThingy.pack();        
	
	    fieldView = new FieldView(height, width);
        ControlView controlButtons = new ControlView(stats, simulationControl);
        simulation.addObserver(controlButtons);
        simulation.addObserver(lineThingy);
        simulation.addObserver(barThingy);

        // Start of building the GUI layout
        Container contents = getContentPane();  //Main screen
        JPanel graphPanel = new JPanel(); // Line Graph Panel
        JPanel histoPanel = new JPanel(); // Histogram Graph Panel
        JPanel southPanel = new JPanel();// South panel, contains Line and Histogram graph

        //Layouts
        contents.setLayout(new BorderLayout()); //Main
        graphPanel.setLayout(new BorderLayout()); //Line Graph
        histoPanel.setLayout(new BorderLayout()); //Histogram Graph
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS )); //Panel containg graphs

        //Filling Line Graph Panel
        graphPanel.add(lineThingy.panel);
//        graphPanel.add(new JLabel());

        //Filling Histogram Graph Panel
        histoPanel.add(barThingy.panel);

        //Add both Graphs to the SouthPanel
        southPanel.add(graphPanel);
        southPanel.add(histoPanel);
        southPanel.setPreferredSize(new Dimension(getWidth(), 250));

        //Add all to the main Panel
        contents.add(fieldView, BorderLayout.CENTER);
        contents.add(controlButtons, BorderLayout.EAST);
        contents.add(southPanel, BorderLayout.SOUTH);

        //Create MenuBar
        setMenu(simulationControl);

        pack();

        //Set maximum size of frame so it won't ousize screen when it's no longer maximised
        Rectangle bounds = getGraphicsConfiguration().getBounds();
        setSize(new Dimension(bounds.width, bounds.height));

        //Set the frame to full screen
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Creates the MenuBar that is displayed on the GUI
     */
    public void setMenu(ActionListener listener) {
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem item;

        menuBar = new JMenuBar();

        //Simulation menu
        menu = new JMenu("Simulation");
        menuBar.add(menu);

        //Add Items to the Menu
        item = new JMenuItem("Increase steps with 1");
        item.addActionListener(listener);
        menu.add(item);

        item = new JMenuItem("Increase steps with 50");
        item.addActionListener(listener);
        menu.add(item);

        item = new JMenuItem("Increase steps with 100");
        item.addActionListener(listener);
        menu.add(item);

        item = new JMenuItem("Increase steps with 250");
        item.addActionListener(listener);
        menu.add(item);

        item = new JMenuItem("Increase steps with 500");
        item.addActionListener(listener);
        menu.add(item);

        item = new JMenuItem("Start simulation");
        item.addActionListener(listener);
        menu.add(item);

        item = new JMenuItem("Pause simulation");
        item.addActionListener(listener);
        menu.add(item);

        item = new JMenuItem("Reset simulation");
        item.addActionListener(listener);
        menu.add(item);

        setJMenuBar(menuBar);
    }

/**
     * Show the current status of the field.
     * @param field The field whose status is to be displayed.
     */
    public void showStatus(Field field){
        if(!isVisible()) {
            setVisible(true);
        }

        fieldView.preparePaint();
        fieldView.drawMarks(field, stats);

        repaint();
        fieldView.repaint();
    }

    /**
     * Determine whether the simulation should continue to run.
     * @param field the field
     * @return true If there is more than one species alive.
     */
    public boolean isViable(Field field)
    {
        return stats.isViable(field);
    }
}

