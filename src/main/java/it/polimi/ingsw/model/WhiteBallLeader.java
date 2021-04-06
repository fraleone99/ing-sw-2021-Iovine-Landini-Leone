package it.polimi.ingsw.model;

import java.util.ArrayList;

/**
 * The white ball leader is a subclass of Leader Card.
 * This leader offers the possibility to exchange the white
 * balls taken from the market with a resource.
 *
 * @author Lorenzo Iovine.
 */

public class WhiteBallLeader extends LeaderCard{
    private final Resource conversionType;
    private final ArrayList<Requirements> requirements = new ArrayList<>();

    public WhiteBallLeader(int VictoryPoints, Resource type, Requirements req1, Requirements req2) {
        super(VictoryPoints);
        this.conversionType = type;
        requirements.add(req1);
        requirements.add(req2);
    }

    public Resource getConversionType(){
        return conversionType;
    }

    public ArrayList<Requirements> getRequirements(){
        return requirements;
    }

    public boolean checkRequirements(PlayerDashboard playerDashboard){
        return playerDashboard.getDevCardsSpace().checkSpace(requirements.get(0).getColor(), requirements.get(0).getLevel()) >= 2 && playerDashboard.getDevCardsSpace().checkSpace(requirements.get(1).getColor(), requirements.get(1).getLevel()) >= 1;
    }
}
