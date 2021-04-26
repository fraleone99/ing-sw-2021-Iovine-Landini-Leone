package it.polimi.ingsw.model.card.developmentcard;

import it.polimi.ingsw.model.enumeration.CardColor;
import it.polimi.ingsw.model.Goods;
import it.polimi.ingsw.model.Production;
import it.polimi.ingsw.model.card.Card;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This is a subclass of Card, which describes the Development Card.
 * Each Development Card has a color, a level, a cost and an input production;
 * while the output can be of resources and/or faith points
 *
 * @author Lorenzo Iovine.
 */

public class DevelopmentCard extends Card {
    private final CardColor color;
    private final int level;
    private final ArrayList<Goods> cost = new ArrayList<>();
    private final Production production;


    public DevelopmentCard(int VictoryPoints, CardColor Color, int Level, ArrayList<Goods> Cost, Production production) {
        super(VictoryPoints);
        this.color=Color;
        this.level=Level;
        this.cost.addAll(Cost);
        this.production = production;
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
        return production.getFaithSteps();
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
                ", victoryPoints=" + getVictoryPoints() +
                ", production=(" + "input->" + production.getInputProduction().toString() +
                                    "output->" + production.getOutputProduction().toString() + ")"+
                ", faithSteps=" + production.getFaithSteps() +
                '}';
    }
}
