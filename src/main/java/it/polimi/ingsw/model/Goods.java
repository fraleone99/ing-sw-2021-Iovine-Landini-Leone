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


    /**
     * Constructor Goods creates a new Goods instance
     * @param Type is the resource type of the goods
     * @param Amount is the amount of the goods
     */
    public Goods(Resource Type, int Amount){
        this.type = Type;
        this.amount = Amount;
    }


    /**
     * Gets the type of the goods
     * @return type variable
     */
    public Resource getType(){
        return type;
    }


    /**
     * Gets the amount of the goods
     * @return amount variable
     */
    public int getAmount(){
        return amount;
    }


    /**
     * Sets the amount of the goods
     * @param amount is the amount of the goods
     */
    public void setAmount(int amount){
        this.amount = amount;
    }


    /**
     * Constructor Goods create a new Goods instance
     * @param g is the goods we want to instantiate
     */
    public Goods(Goods g){
        this.type = g.getType();
        this.amount = g.getAmount();
    }


    /**
     * This is the string representation of a Goods
     * @return a string that represent the Goods
     */
    @Override
    public String toString() {
        return "Goods{" +
                "type=" + type +
                ", amount=" + amount +
                '}';
    }
}
