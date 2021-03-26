package it.polimi.ingsw.model;

import java.util.ArrayList;

/**
 * The economy leader is a subclass of Leader Card.
 * This leader helps the player by giving him a discount
 * for the purchase of development cards.
 *
 * @author Lorenzo Iovine.
 */

public class EconomyLeader extends LeaderCard{
    private Resource discountType;
    private ArrayList<Requirements> requirements = new ArrayList<>();

    public EconomyLeader(int VictoryPoints, Resource DiscountType, Requirements req1, Requirements req2) {
        super(VictoryPoints);
        this.discountType = DiscountType;
        requirements.add(req1);
        requirements.add(req2);
    }

    public Resource getDiscountType()
    {
        return discountType;
    }

    public ArrayList<Requirements> getRequirements(){
        return requirements;
    }
}
