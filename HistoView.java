import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.util.Observable;
import java.util.Observer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

/**
 * A Histogram where the number of animals are presented as Bars
 */
public class HistoView extends ApplicationFrame implements Observer {

	public ChartPanel panel;
	private FieldStats stats;
	private Field field;
	private static DefaultCategoryDataset dataset;
	
  /**
   * Creates a new Histogram
   *
   * @param title of the Graph
   * @param stats FieldStats object
   * @param field The field where the animals are
   */
    public HistoView(String title,FieldStats stats,Field field) {

        super(title);
        this.stats=stats;
        this.field=field;
        
        CategoryDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart, false);
        chartPanel.setPreferredSize(new Dimension(200, 270));
        panel = chartPanel;
        setContentPane(chartPanel);

    }
    
    /**
     * Returns the dataset.
     * 
     * @return The dataset.
     */
    private static CategoryDataset createDataset() {
        
        // row keys...
        String series1 = "Rabbits";
        String series2 = "Foxes";
        String series3 = "Bears";
        
        
        

        // column keys...
        String category1 = "Field Statistics";
      
        

        // create the dataset...
        dataset = new DefaultCategoryDataset();

        dataset.addValue(0, series1, category1);
        
        dataset.addValue(0, series2, category1);

        dataset.addValue(0, series3, category1);

        
        return dataset;

    }
    /**
     * Creates a the chart.
     * 
     * @param dataset  the dataset.
     * 
     * @return The chart.
     */
    private static JFreeChart createChart(CategoryDataset dataset) {
        
        // create the chart...
        JFreeChart chart = ChartFactory.createBarChart(
            "Histogram",         // chart title
            "Units",               // domain axis label
            "Number",                  // range axis label
            dataset,                  // data
            PlotOrientation.VERTICAL, // orientation
            true,                     // include legend
            true,                     // tooltips?
            false                     // URLs?
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

        // set the background color for the chart...
        chart.setBackgroundPaint(Color.white);

        // get a reference to the plot for further customisation...
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setDomainGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.white);

        // set the range axis to display integers only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // disable bar outlines...
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        
        // set up gradient paints for series...
        GradientPaint gp0 = new GradientPaint(
            0.0f, 0.0f, Color.blue, 
            0.0f, 0.0f, new Color(0, 0, 64)
        );
        GradientPaint gp1 = new GradientPaint(
            0.0f, 0.0f, Color.green, 
            0.0f, 0.0f, new Color(0, 64, 0)
        );
        GradientPaint gp2 = new GradientPaint(
            0.0f, 0.0f, Color.red, 
            0.0f, 0.0f, new Color(64, 0, 0)
        );
        renderer.setSeriesPaint(0, gp0);
        renderer.setSeriesPaint(1, gp1);
        renderer.setSeriesPaint(2, gp2);

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
            CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
        );
        // OPTIONAL CUSTOMISATION COMPLETED.
        
        return chart;
        
    }

    /**
     * Updates the Bars if the Observable says so
     */
    public void update(Observable o, Object arg) {
    	 // row keys...
        String series1 = "Rabbits";
        String series2 = "Foxes";
        String series3 = "Bears";
       
        // column keys...
        String category1 = "Field Statistics";

        int rabbit = stats.getPopulationNumbers(field,"Rabbit");
        int fox = stats.getPopulationNumbers(field,"Fox");
        int bear = stats.getPopulationNumbers(field,"Bear");


    	dataset.setValue(rabbit,series1, category1);
    	dataset.setValue(fox,series2, category1);
    	dataset.setValue(bear,series3, category1);
    }
}
