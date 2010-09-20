import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;
import java.util.Observer;
import java.util.Observable;
import java.util.HashMap;

/**
 * Display for the Control Buttons on the GUI
 */
public class ControlView extends JPanel implements Observer {
    // Create instances of the Buttons and Labels
    private JButton playPauseButton = new JButton("Start");
    private JButton addStepsButton = new JButton("Add steps");
    private JButton resetButton = new JButton("Reset");
    private JButton optionButton = new JButton("Options");
     
    private JLabel playImage = new JLabel();
    private JLabel playLabel = new JLabel("Steps:");
    private NumericInput playInput = new NumericInput(NumericInput.INTEGER_INPUT);
    private JLabel playOutput = new JLabel();
    
    private JLabel infoLabel = new JLabel("");
    private JLabel info2Label = new JLabel("  Starting %");
    private JLabel info5Label = new JLabel("  Number");
    
    private JLabel rabbitImage = new JLabel ();
    private JLabel rabbitLabel = new JLabel("Rabbits:");
    private NumericInput rabbitInput = new NumericInput(NumericInput.PERCENTAGE_INPUT);
    private JLabel rabbitOutput = new JLabel();
    
    private JLabel foxImage = new JLabel();
    private JLabel foxLabel = new JLabel("Foxes:");
    private NumericInput foxInput = new NumericInput(NumericInput.PERCENTAGE_INPUT);
    private JLabel foxOutput = new JLabel();
    
    private JLabel bearImage = new JLabel();
    private JLabel bearLabel = new JLabel("Bears:");
    private NumericInput bearInput = new NumericInput(NumericInput.PERCENTAGE_INPUT);
    private JLabel bearOutput = new JLabel();
    
    private JLabel info3Label = new JLabel("  Hunters");
    private JLabel info4Label = new JLabel("  ");
    
    private JLabel hunterImage = new JLabel(); 
    private JLabel hunterLabel = new JLabel("Hunters:");
    private NumericInput hunterInput = new NumericInput(NumericInput.INTEGER_INPUT);
    private JLabel hunterOutput = new JLabel();

    private JLabel emptySpace1 = new JLabel();
    private JLabel emptySpace2 = new JLabel();
    private JLabel emptySpace3 = new JLabel();

    private SimulationControl simulationControl;
    private Populator populator;
    private FieldStats stats;
    private OptionPanel options;

    /**
     * Constructor for Controlview that creates the whole view
     * @param stats FieldStats
     */
    public ControlView(FieldStats stats, SimulationControl simulationControl){
        this.setLayout(new GridLayout(9,3, 6, 12));
        setBorder(new EmptyBorder(12,12,12,12));

        this.simulationControl = simulationControl;
        this.stats = stats;
        this.populator = simulationControl.getPopulator();
        options = new OptionPanel();

        setPopulationControls();
        setControlButtons();
    }

    /**
     * Adds the Images and Labels to the ControlPanel
     */
    private void setPopulationControls(){
        //Step controls
        playImage.setIcon(ImageClass.getImage("footsteps.JPG",50));
        add(playImage);
        add(playLabel);
        add(playInput);
        add(playOutput);

        add(emptySpace1); //Filler Label to get nice layout
        add(infoLabel);
        add(info2Label);
        add(info5Label);

        rabbitImage.setIcon(ImageClass.getImage("rabbit.JPG",50));
        add(rabbitImage);
        add(rabbitLabel);
        add(rabbitInput);
        add(rabbitOutput);

        foxImage.setIcon(ImageClass.getImage("fox.JPG",50));
        add(foxImage);
        add(foxLabel);
        add(foxInput);
        add(foxOutput);


         bearImage.setIcon(ImageClass.getImage("bear.JPG",50));
        add(bearImage);
        add(bearLabel);
        add(bearInput);
        add(bearOutput);

        add(emptySpace2);
        add(emptySpace3);
        add(info3Label);
        add(info4Label);


        hunterImage.setIcon(ImageClass.getImage("hunter.JPG",50));
        add(hunterImage);
        add(hunterLabel);
        add(hunterInput);
        add(hunterOutput);

        defaultValues();
    }

    /**
     * Sets the Listener Objects to the control buttons and adds them to the Panel
     */
    public void setControlButtons(){
        playPauseButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    playPauseButtonPressed();
                }});
        addStepsButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    addStepsButtonPressed();
                }});

        resetButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    resetButtonPressed();
                }});

        optionButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    options.setVisible(!options.isVisible());
                }});

        add(playPauseButton);
        add(addStepsButton);
        add(resetButton);
        add(optionButton);
    }

    /**
     * Sets the default Value's in the ControlPanel
     */
    public void defaultValues(){
        playInput.setText("0");
        playInput.setCaretPosition(playInput.getText().length()); //Makes the cursor go to the end of the line.
        rabbitInput.setText("8.0");
        foxInput.setText("2.0");
        bearInput.setText("0.05");
        hunterInput.setText("1");

        playOutput.setText("0/0");
        rabbitOutput.setText("8.0");
        foxOutput.setText("2.0");
        bearOutput.setText("0.05");
        hunterOutput.setText("1");
    }

    /**
     * Method to act when the Play button is pressed
     */
    public void playPauseButtonPressed(){
        Simulation simulation = simulationControl.getSimulation();
        boolean paused = simulation.isPaused();
        simulation.setPaused(!paused);
        setStartPauseButtonText();
    }

    /**
     * Method to act when the add steps button is pressed
     */
    private void addStepsButtonPressed() {
        Simulation sim = simulationControl.getSimulation();
        sim.increaseNumSteps(playInput.getIntValue());
        updateStepsOutput(sim);
    }

    /**
     * Method to act when the resetbutton is pressed
     */
    public void resetButtonPressed()
    {
        double rabbit = rabbitInput.getDoubleValue();
        double fox = foxInput.getDoubleValue();
        double bear = bearInput.getDoubleValue();
        int hunter = hunterInput.getIntValue();
        if (!populator.setPopulation(rabbit,fox,bear,hunter)){
            JOptionPane.showMessageDialog(null, "Waarden kunnen niet negatief zijn.");
            defaultValues();
        } else {
            playOutput.setText("0/0");
        }
        simulationControl.reset();
    }

    /**
     * Method that changes the text on the Play/Pause button accordingly
     */
    private void setStartPauseButtonText() {
        playPauseButton.setText(simulationControl.isRunning() ? "Pause" : "Start");
    }

/**
 * Updates the control panel when the simulation increases a step
 */
    public void update(Observable observable, Object o) {
        if(observable instanceof Simulation){
            Simulation sim = (Simulation)observable;
            updateStepsOutput(sim);
            setStartPauseButtonText();
            HashMap<Class,Counter> counters = stats.getCurrentPopulationCounters();
            for(Class key : counters.keySet()){
                if(key == Rabbit.class){
                    rabbitOutput.setText(String.valueOf(counters.get(key).getCount()));
                } else if(key == Fox.class){
                    foxOutput.setText(String.valueOf(counters.get(key).getCount()));
                } else if(key == Bear.class){
                    bearOutput.setText(String.valueOf(counters.get(key).getCount()));
                } else if(key == Hunter.class){
                    hunterOutput.setText(String.valueOf(counters.get(key).getCount()));
                }
            }
        }
    }

    /**
     * Updates the step output accordingly so it's displayed on the controlPanel
     * @param sim the Simulation class
     */
    private void updateStepsOutput(Simulation sim){
        playOutput.setText(sim.getStep() + "/" + sim.getNumSteps());
    }
}
