package it.polimi.ingsw.model;

import java.util.ArrayList;

/**
 * This is a subclass of Card, which describes the Development Card.
 * Each Development Card has a color, a level, a cost and an input production;
 * while the output can be of resources and/or faith points
 *
 * @author Lorenzo Iovine.
 */

public class DevelopmentCard extends Card{
    private final CardColor color;
    private final int level;
    private final ArrayList<Goods> cost = new ArrayList<>();
    private final Production production;
    private final int faithSteps;



    public DevelopmentCard(int VictoryPoints, CardColor Color, int Level, ArrayList<Goods> Cost, Production production, int FaithSteps) {
        super(VictoryPoints);
        this.color=Color;
        this.level=Level;
        this.cost.addAll(Cost);
        this.production = production;
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

    /*public ArrayList<Goods> getInputProduction() {
        return inputProduction;
    }
    public ArrayList<Goods> getOutputProduction() {
        return outputProduction;
    }*/

    public int getFaithSteps() {
        return faithSteps;
    }

    public ArrayList<Goods> getInputProduction(){
        return production.getInputProduction();
    }
    public ArrayList<Goods> getOutputProduction(){
        return production.getOutputProduction();
    }

    public Production getProduction() {
        return production;
    }

    @Override
    public String toString() {
        return "DevelopmentCard{" +
                "color=" + color +
                ", level=" + level +
                ", cost=" + cost.toString() +
                ", production=(" + "input->" + production.getInputProduction().toString() +
                                    "output->" + production.getOutputProduction().toString() + ")"+
                ", faithSteps=" + faithSteps +
                '}';
    }
}
