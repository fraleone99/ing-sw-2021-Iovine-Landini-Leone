package it.polimi.ingsw.model.card.leadercard;

import it.polimi.ingsw.model.enumeration.CardColor;
import it.polimi.ingsw.model.Goods;

import java.util.ArrayList;

/**
 * This class is used to describe the requirements
 * for buying the Leader Cards.
 *
 * @author Lorenzo Iovine.
 */
public class Requirements {
    private final CardColor color;
    private final int level;
    private final int amount;
    private final ArrayList<Goods> activationCost = new ArrayList<>();


    /**
     * Constructor Requirements creates a new Requirements instance
     * @param Color is the color of the card required
     * @param Level is the level of the card required
     * @param Amount is the amount of cards required
     * @param cost is the cost to activate the card
     */
    public Requirements(CardColor Color, int Level, int Amount, Goods cost){
        this.color=Color;
        this.level=Level;
        this.amount=Amount;
        activationCost.add(cost);
    }


    /**
     * Gets the color of the card required
     * @return the parameter color
     */
    public CardColor getColor(){
        return color;
    }


    /**
     * Gets the level of the card required
     * @return the parameter level
     */
    public int getLevel(){
        return level;
    }


    /**
     * Gets the amount of cards required
     * @return the parameter amount
     */
    public int getAmount(){
        return amount;
    }


    /**
     * Gets the cost to activate the card
     * @return the parameter activationCost
     */
    public ArrayList<Goods> getCost() { return activationCost; }
}
