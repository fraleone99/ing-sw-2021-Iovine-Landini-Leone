package it.polimi.ingsw.model;

/**
 * This class is used to describe the requirements
 * for buying three types of Leader Cards.
 *
 * @author Lorenzo Iovine.
 */

public class Requirements {
    private CardColor color;
    private int level;
    private int amount;

    public Requirements(CardColor Color, int Level, int Amount){
        this.color=Color;
        this.level=Level;
        this.amount=Amount;
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
}
