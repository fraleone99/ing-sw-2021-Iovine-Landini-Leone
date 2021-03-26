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
    private Resource conversionType;
    private ArrayList<Requirements> requirements = new ArrayList<>();

    public WhiteBallLeader(int VictoryPoints, Resource type, Requirements req1, Requirements req2) {
        super(VictoryPoints);
        this.conversionType = type;
        requirements.add(req1);
        requirements.add(req2);
    }

    public Resource getConversionType(){
        return conversionType;
    }

    public ArrayList<Requirements> getRequirements() {
        return requirements;
    }
}