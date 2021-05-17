package it.polimi.ingsw.model;

import java.util.ArrayList;

/**
 * In this class we insert all the productions requested by the
 * player before removing and adding the resources.
 *
 * @author Lorenzo Iovine.
 */

public class Production {
    private final ArrayList<Goods> inputProduction;
    private final ArrayList<Goods> outputProduction;
    private final int faithSteps;


    /**
     * Constructor Production creates a new Production instance
     * @param inputProduction is the input of the production
     * @param outputProduction is the output of the production
     * @param faithSteps is the number of faith steps of the production
     */
    public Production(ArrayList<Goods> inputProduction, ArrayList<Goods> outputProduction, int faithSteps){
        this.inputProduction = inputProduction;
        this.outputProduction = outputProduction;
        this.faithSteps = faithSteps;
    }


    /**
     * Set the output of the production to the output parameter
     * @param output is the output of the production
     */
    public void setOutputProduction(Goods output){
        outputProduction.set(0, output);
    }


    /**
     * Gets the input of the production
     * @return inputProduction variable
     */
    public ArrayList<Goods> getInputProduction() {
        return inputProduction;
    }


    /**
     * Gets the output of the production
     * @return outputProduction variable
     */
    public ArrayList<Goods> getOutputProduction() {
        return outputProduction;
    }


    /**
     * Gets the faith steps of the production
     * @return faithSteps variable
     */
    public int getFaithSteps() {
        return faithSteps;
    }

}
