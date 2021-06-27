package it.polimi.ingsw.model.card.leadercard;

import it.polimi.ingsw.model.gameboard.playerdashboard.PlayerDashboard;
import it.polimi.ingsw.model.enumeration.Resource;

import java.util.ArrayList;

/**
 * The white ball leader is a subclass of Leader Card.
 * This leader offers the possibility to exchange the white
 * balls taken from the market with a resource.
 *
 * @author Lorenzo Iovine.
 */

public class WhiteBallLeader extends LeaderCard {
    private final Resource conversionType;
    private final ArrayList<Requirements> requirements = new ArrayList<>();


    /**
     * Constructor WhiteBallLeader creates a new WhiteBallLeader instance
     * @param VictoryPoints is the number of the victory points of the card
     * @param CardID is the ID of the card
     * @param type is the type of the conversion that the card offers
     * @param req1 is the first requirement of the card
     * @param req2 is the second requirement of the card
     */
    public WhiteBallLeader(int VictoryPoints, int CardID, Resource type, Requirements req1, Requirements req2) {
        super(VictoryPoints, CardID);
        this.conversionType = type;
        requirements.add(req1);
        requirements.add(req2);
    }


    /**
     * Gets the type of the conversion that the card offers
     * @return the type of the conversion
     */
    public Resource getConversionType(){
        return conversionType;
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
    public boolean checkRequirements(PlayerDashboard playerDashboard){
        return playerDashboard.getDevCardsSpace().checkSpace(requirements.get(0).getColor(), requirements.get(0).getLevel()) >= 2 && playerDashboard.getDevCardsSpace().checkSpace(requirements.get(1).getColor(), requirements.get(1).getLevel()) >= 1;
    }
}
