import java.util.*;

/**
 * Simulation holds the data about the simulation. It records the current step, the total number of steps
 * And wether the simulation is paused or not.
 *
 * @author martin poelman
 * @version 0.1, 15-jun-2010 15:30:27
 */
public class Simulation extends Observable {
    private int numSteps;
    private int step;
    private boolean paused;

    /**
     * Default constructor
     */
    public Simulation(){
        super();
        numSteps = 0;
        step = 0;
    }

    /**
     * Pauses the simulation
     */
    public void pauseThread(){
        setPaused(true);
    }

    /**
     * Resumes the simulation
     */
    public void resumeThread() {
        setPaused(false);
    }

    /**
     * Check if the simulation is paused
     * @return
     */
    public boolean isPaused(){
        return paused;
    }

    /**
     * Pauses or unpauses the simulation according to the supplied boolean argument, then it notifies all
     * observers.
     * @param paused If true the thread will be paused, if false it wil be resumed
     */
    public void setPaused(boolean paused){
        this.paused = paused;
        notifyObservers();
    }

    /**
     * Retrieve the planned number of steps.
     *
     * @return The planned number of steps.
     */
    public int getNumSteps() {
        return numSteps;
    }

    /**
     * Sets the planned number of steps and notifies all the observers.
     *
     * @param numSteps Supply the number of steps.
     */
    public void setNumSteps(int numSteps) {
        this.numSteps = numSteps;
        notifyObservers();
    }

    /**
     * Increases the planned number of steps by the supplied amount.
     * @param amountOfSteps The amount with which the planned number of steps should be increased.
     */
    public void increaseNumSteps(int amountOfSteps){
        setNumSteps(amountOfSteps + numSteps);
    }

    /**
     * Returns the current step number.
     * @return The current step number.
     */
    public int getStep() {
        return step;
    }

    /**
     * Sets the current step
     * @param step The current step to be
     */
    public void setStep(int step) {
        this.step = step;
        notifyObservers();
    }

    /**
     * Overwriting the super class method to make notifying observers quicker.
     */
    public void notifyObservers() {
        setChanged();
        super.notifyObservers();
    }
}