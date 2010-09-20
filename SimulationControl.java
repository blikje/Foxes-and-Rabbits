import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ConcurrentModificationException;

/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing rabbits and foxes.
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2008.03.30
 */
public class SimulationControl extends WindowAdapter implements ActionListener {
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 80;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 60;
    
    // List of actors in the field.
    private List<Actor> actors;
    // The current state of the field.
    private Field field;
    // A graphical view of the simulation.
    private SimulationView view;
    // Populator contains the tools needed to initialize the actor population
    private Populator populator;
    // Simulation contains data about the current state of the simulation
    private Simulation simulation;
    // SimutorThread makes sure the simulation kan run independently from the menu
    private SimulatorThread simulator;
    
    /**
     * Create a simulation field with the given size.
     */
    public SimulationControl(){
        actors = new ArrayList<Actor>();

        //Create te simulator, the thread that keeps our simulation running
        simulator = new SimulatorThread();

        //Create the required models
        field = new Field(DEFAULT_DEPTH, DEFAULT_WIDTH);
        populator = new Populator(this,field,actors);
        simulation = new Simulation();
        
        // Create a view of the state of each location in the field.
        view = new SimulationView(DEFAULT_DEPTH, DEFAULT_WIDTH, field, simulation, this);

        //Setup a valid starting point.
        reset();

        //Start the thread
        simulator.start();
    }

    /**
     * Gets the simulation object
     * @return The simulation object
     */
    public Simulation getSimulation() {
        return simulation;
    }

    /**
     * Gets the population object
     * @return The population object
     */
    public Populator getPopulator()
    {
        return populator;
    }
    
    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        simulation.pauseThread();
        simulation.setNumSteps(0);
        simulation.setStep(0);
        actors.clear();
        populator.populate();
    }

    /**
     * Check if the simulator thread is currently in running mode (see  SimulationThread.running)
     *
     * @return True if the simulation is running
     */
    public boolean isRunning(){
        return simulator.running;
    }

    /**
     * Capture main menu events and execute the correct code.
     *
     * @param event The event that was fired witht the press of a menuitem.
     */
    public void actionPerformed(ActionEvent event) {
        String actionCommand = event.getActionCommand();
        if ("Quit".equals(actionCommand)) {
            System.exit(0);
        } else if ("Increase steps with 1".equals(actionCommand)) {
            simulation.increaseNumSteps(1);
        } else if ("Increase steps with 50".equals(actionCommand)) {
            simulation.increaseNumSteps(50);
        } else if ("Increase steps with 100".equals(actionCommand)) {
            simulation.increaseNumSteps(100);
        } else if ("Increase steps with 250".equals(actionCommand)) {
            simulation.increaseNumSteps(250);
        } else if ("Increase steps with 500".equals(actionCommand)) {
            simulation.increaseNumSteps(500);
        } else if ("Start simulation".equals(actionCommand)) {
            simulation.resumeThread();
        } else if ("Pause simulation".equals(actionCommand)) {
            simulation.pauseThread();
        } else if ("Reset simulation".equals(actionCommand)) {
            reset();
        }
    }

    /**
     * When the window is closing we want to kill our thread, just to be sure
     *
     * @param event Any event responsible for closing the window
     */
    public void windowClosing(WindowEvent event) {
        simulator.killThread();
    }

    /**
     * The thread that keeps the simulation running
     */
    private class SimulatorThread extends Thread {
        //If die is true the thread will stop iterating and the simulation can't be continued
        private boolean die;
        //If running is true the simulation continues to step
        private boolean running;

        /**
         * Create a new simulator thread
         */
        public SimulatorThread(){
            die = false;
            running = true;
        }

        /**
         * Kill the thread
         */
        public void killThread(){
            die = true;
        }

        /**
         * Is called when the thread is started
         */
        public void run() {
            try{
                //Declare several variables outside the while loop for efficiency
                int step;
                int numSteps;
                boolean paused;
                boolean wasRunning;
                List<Animal> newAnimals;
                Actor actor;

                //Always keep looping unless the thread is killed
                while(!die){

                    //Set the variables
                    step = simulation.getStep();
                    numSteps = simulation.getNumSteps();
                    paused = simulation.isPaused();
                    wasRunning = running; //Remember if the simulation was running before we determine if we want to keep it running

                    //Determine if we should keep the simulation running. There can be 3 reasons it shouldn't be:
                    //  - The maximum amount of steps is reached
                    //  - The user pauses the simulation
                    //  - The simulation is no longer viable (only one animal type remains)
                    running = step < numSteps && !paused && view.isViable(field);
                    if(running){
                        step++;

                        // Provide space for newborn animals.
                        newAnimals = new ArrayList<Animal>();
                        // Let all actors act.
                        for(Iterator<Actor> it = actors.iterator(); it.hasNext(); ) {
                            actor = it.next();
                            actor.act(newAnimals);
                            if(!actor.isAlive()) { //If the actor is no longer alive remove it from the list
                                it.remove();
                            }
                        }

                        // Add the newly born animals to the main lists.
                        actors.addAll(newAnimals);
                    }

                    //If the simulator was running we want to set the step one more time for the perfect observer states
                    if(wasRunning){
                        simulation.setStep(step);
                    }

                    //Each iteration we refresh even if we're not running, we do this to keep the paused field in the right ratio when resizing
                    view.showStatus(field);

                    //Take a super short nap before we iterate again.
                    sleep(50);
                }
            } catch (InterruptedException e) {
                e.printStackTrace(System.out); //Should never be happening but if it does we sure want to know about it
            } catch (ConcurrentModificationException cme){
                cme.printStackTrace(System.out); //Should never be happening but if it does we sure want to know about it
            }
        }
    }
}