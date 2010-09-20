import java.awt.Color;
import java.util.Observable;
import java.util.Observer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

/**
 * Creates a graph representation of the population for each step
 */
public class GraphView extends ApplicationFrame implements Observer {
	
	public ChartPanel panel;
	private FieldStats stats;
	private Field field;
	private final XYSeries rabbitSeries = new XYSeries("Rabbits");
    private final XYSeries foxSeries = new XYSeries("Foxes");
    private final XYSeries bearSeries = new XYSeries("Bears");

    /**
     * Creates a graphView
     *
     * @param title The frame title.
     * @param stats The FieldStats object containing the population statistics
     */
    public GraphView(final String title,FieldStats stats,Field field) {

        super(title);
        this.stats=stats;
        this.field=field;
        
        final XYDataset dataset = createDataset();
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 250));
        panel = chartPanel;
        setContentPane(chartPanel);

    }
    
    /**
     * Creates the dataset.
     * 
     * @return The dataset.
     */
    private XYDataset createDataset() {
        //Define the values which hold the population numbers for each animal in the graph
    	int rabbit = stats.getPopulationNumbers(field,"Rabbit");
        int fox = stats.getPopulationNumbers(field,"Fox");
        int bear = stats.getPopulationNumbers(field,"Bear");

        //Add each value to the appropriate serie
        rabbitSeries.add(0, rabbit);
        foxSeries.add(0, fox);
        bearSeries.add(0, bear);

        //Add the series to the dataset
        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(rabbitSeries);
        dataset.addSeries(foxSeries);
        dataset.addSeries(bearSeries);

        //Return the dataset
        return dataset;
    }
    
    /**
     * Creates a chart.
     * 
     * @param dataset The data for the chart.
     * 
     * @return The created chart.
     */
    private JFreeChart createChart(final XYDataset dataset) {
        
        // create the chart...
        final JFreeChart chart = ChartFactory.createXYLineChart(
            "Line Chart",                 // chart title
            "Steps",                      // x axis label
            "Number",                     // y axis label
            dataset,                      // data
            PlotOrientation.VERTICAL,     // plot orientation
            true,                         // include legend
            true,                         // tooltips
            false                         // urls
        );

        // Char customisation
        chart.setBackgroundPaint(Color.white);

        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        //Define custom lines and shapes
        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(1, true);
        renderer.setSeriesShapesVisible(0, true);

        //Add the line and shape renderer to the plot
        plot.setRenderer(renderer);

        // Change the auto tick unit selection to integer units only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setAutoRange(true);
        
        // End of customisation
                
        return chart;
    }

    /**
     * Method is called by observable object, in this case the Simulation object when it steps
     * @param o
     * @param arg
     */
    public void update(Observable o, Object arg) {
        //We need the step in this method
        int step = ((Simulation)o).getStep();

        if(step == 0){ // If step is set to 0 then we want to reset the graph
            rabbitSeries.clear();
            foxSeries.clear();
            bearSeries.clear();
        } else { // If it's not 0 then continue the graph 
            int rabbit = stats.getPopulationNumbers(field,"Rabbit");
            int fox = stats.getPopulationNumbers(field,"Fox");
            int bear = stats.getPopulationNumbers(field,"Bear");
            rabbitSeries.add(step,rabbit);
            foxSeries.add(step,fox);
            bearSeries.add(step,bear);
        }
    }
}
