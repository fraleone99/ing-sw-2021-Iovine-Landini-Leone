package it.polimi.ingsw.model;

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

    public Requirements(CardColor Color, int Level, int Amount, Goods cost){
        this.color=Color;
        this.level=Level;
        this.amount=Amount;
        activationCost.add(cost);
    }

    public CardColor getColor(){
        return color;
    }

    public int getLevel(){
        return level;
    }

    public int getAmount(){
        return amount;
    }

    public ArrayList<Goods> getCost() { return activationCost; }
}
