package it.polimi.ingsw.model.card.leadercard;

import it.polimi.ingsw.model.gameboard.playerdashboard.PlayerDashboard;
import it.polimi.ingsw.model.enumeration.Resource;

import java.util.ArrayList;

/**
 * The economy leader is a subclass of Leader Card.
 * This leader helps the player by giving him a discount
 * for the purchase of development cards.
 *
 * @author Lorenzo Iovine.
 */

public class EconomyLeader extends LeaderCard {
    private final Resource discountType;
    private final ArrayList<Requirements> requirements = new ArrayList<>();


    /**
     * Constructor EconomyLeader creates a new EconomyLeader instance
     * @param VictoryPoints is the number of the victory points of the card
     * @param CardID is the ID of the card
     * @param DiscountType is the discount type of the card
     * @param req1 is the first requirement of the card
     * @param req2 is the second requirement of the card
     */
    public EconomyLeader(int VictoryPoints, int CardID, Resource DiscountType, Requirements req1, Requirements req2) {
        super(VictoryPoints, CardID);
        this.discountType = DiscountType;
        requirements.add(req1);
        requirements.add(req2);
    }


    /**
     * Gets the discount type of the card
     * @return the discount type
     */
    public Resource getDiscountType()
    {
        return discountType;
    }


    /**
     * {@inheritDoc}
     */
    public ArrayList<Requirements> getRequirements(){
        return requirements;
    }


    /**
     * {@inheritDoc}
     */
    public boolean checkRequirements(PlayerDashboard playerDashboard) {
        return playerDashboard.getDevCardsSpace().checkSpace(requirements.get(0).getColor(), requirements.get(0).getLevel()) >= 1 && playerDashboard.getDevCardsSpace().checkSpace(requirements.get(1).getColor(), requirements.get(1).getLevel()) >= 1;
    }
}
