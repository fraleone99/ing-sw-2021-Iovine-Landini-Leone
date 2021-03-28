package it.polimi.ingsw.model;

import java.util.ArrayList;

/**
 * In this class we insert all the productions requested by the
 * player before removing and adding the resources.
 *
 * @author Lorenzo Iovine.
 */

public class Production {
    private ArrayList<Goods> inputProduction = new ArrayList<>();
    private ArrayList<Goods> outputProduction = new ArrayList<>();

    public void setInputProduction(Goods input){
        inputProduction.add(input);
    }

    public void setOutputProduction(Goods output){
        outputProduction.add(output);
    }

    public ArrayList<Goods> getInputProduction() {
        return inputProduction;
    }

    public ArrayList<Goods> getOutputProduction() {
        return outputProduction;
    }
}
