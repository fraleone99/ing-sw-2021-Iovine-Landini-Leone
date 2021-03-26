package it.polimi.ingsw.model;

/**
 * This class is used to describe the cost, input
 * and output of development cards.
 *
 * @author Lorenzo Iovine.
 */

public class Goods {
    private Resource type;
    private int amount;

    public Goods(Resource Type, int Amount){
        this.type = Type;
        this.amount = Amount;
    }

    public Resource getType(){
        return type;
    }

    public int getAmount(){
        return amount;
    }
}
