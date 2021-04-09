package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.Resource;

/**
 * This class is used to describe the cost, input
 * and output of development cards.
 *
 * @author Lorenzo Iovine.
 */

public class Goods{
    private final Resource type;
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

    public void setAmount(int amount){
        this.amount = amount;
    }

    public Goods(Goods g){
        this.type = g.getType();
        this.amount = g.getAmount();
    }

    @Override
    public String toString() {
        return "Goods{" +
                "type=" + type +
                ", amount=" + amount +
                '}';
    }
}
