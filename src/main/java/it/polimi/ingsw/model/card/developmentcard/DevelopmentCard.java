package it.polimi.ingsw.model.card.developmentcard;

import it.polimi.ingsw.model.enumeration.CardColor;
import it.polimi.ingsw.model.Goods;
import it.polimi.ingsw.model.Production;
import it.polimi.ingsw.model.card.Card;
import java.util.ArrayList;

/**
 * This is a subclass of Card, which describes the Development Card.
 * Each Development Card has a color, a level, a cost and an input production;
 * while the output can be of resources and/or faith points
 *
 * @author Lorenzo Iovine, Francesco Leone
 */

public class DevelopmentCard extends Card {
    private final CardColor color;
    private final int level;
    private final ArrayList<Goods> cost = new ArrayList<>();
    private final Production production;


    /**
     * Constructor DevelopmentCard creates a new DevelopmentCard instance
     * @param VictoryPoints is the number of the victory points of the card
     * @param CardID is the id of the card
     * @param Color is the color of the card
     * @param Level is the level of the card
     * @param Cost is the cost to activate the card
     * @param production is the production of the card
     */
    public DevelopmentCard(int VictoryPoints, int CardID, CardColor Color, int Level, ArrayList<Goods> Cost, Production production) {
        super(VictoryPoints, CardID);
        this.color=Color;
        this.level=Level;
        this.cost.addAll(Cost);
        this.production = production;
    }


    /**
     * Get the color of the card
     * @return the color of the card
     */
    public CardColor getColor() {
        return color;
    }


    /**
     * Get the level of the card
     * @return the level of the card
     */
    public int getLevel() {
        return level;
    }


    /**
     * Get the cost of the card
     * @return an ArrayList of Goods that represents the cost to activate the card
     */
    public ArrayList<Goods> getCost() {
        return cost;
    }


    /**
     * Get the faith steps of the card production
     * @return the faith steps
     */
    public int getFaithSteps() {
        return production.getFaithSteps();
    }


    /**
     * Get the input of the production of the card
     * @return the input of the production
     */
    public ArrayList<Goods> getInputProduction(){
        return production.getInputProduction();
    }


    /**
     * Get the output of the production of the card
     * @return the output of the production
     */
    public ArrayList<Goods> getOutputProduction(){
        return production.getOutputProduction();
    }


    /**
     * Get the production of the card
     * @return the production
     */
    public Production getProduction() {
        return production;
    }


    /**
     * This is the string representation of a Development Card
     * @return a string that represent the card
     */
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
