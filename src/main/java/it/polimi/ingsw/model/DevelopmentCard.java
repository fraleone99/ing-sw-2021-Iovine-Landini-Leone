package it.polimi.ingsw.model;

import java.util.ArrayList;

/**
 * This is a subclass of Card, which describes the Development Card.
 * Each Development Card has a color, a level, a cost and an input production;
 * while the output can be of resources or faith points.
 *
 * @author Lorenzo Iovine.
 */

public class DevelopmentCard extends Card{
    private CardColor color;
    private int level;
    private ArrayList<Goods> cost;
    private ArrayList<Goods> inputProduction;
    private ArrayList<Goods> outputProduction;
    private int faithSteps;


    public DevelopmentCard(int VictoryPoints, CardColor Color, int Level, ArrayList<Goods> Cost, ArrayList<Goods> input, ArrayList<Goods> output, int FaithSteps) {
        super(VictoryPoints);
        this.color=Color;
        this.level=Level;
        this.cost=Cost;
        this.inputProduction=input;
        this.outputProduction=output;
        this.faithSteps=FaithSteps;
    }

    public CardColor getColor() {
        return color;
    }

    public int getLevel() {
        return level;
    }

    public ArrayList<Goods> getCost() {
        return cost;
    }

    public ArrayList<Goods> getInputProduction() {
        return inputProduction;
    }

    public ArrayList<Goods> getOutputProduction() {
        return outputProduction;
    }

    public int getFaithSteps() {
        return faithSteps;
    }
}
