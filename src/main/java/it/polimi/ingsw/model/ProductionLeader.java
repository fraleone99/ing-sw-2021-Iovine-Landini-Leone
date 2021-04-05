package it.polimi.ingsw.model;

import java.util.ArrayList;

/**
 * The production leader is a subclass of Leader Card.
 * This leader offers the player the chance to choose the output
 * of a production and to obtain a faith point.
 *
 * @author Lorenzo Iovine.
 *
 */

public class ProductionLeader extends LeaderCard{
    private final Resource inputProduction;
    private final Resource outputProduction;
    private final ArrayList<Requirements> requirements = new ArrayList<>();

    public ProductionLeader(int VictoryPoints, Resource Input, Resource Output, Requirements req) {
        super(VictoryPoints);
        this.inputProduction = Input;
        this.outputProduction = Output;
        requirements.add(req);
    }

    public Resource getInputProduction(){
        return inputProduction;
    }

    public ArrayList<Requirements> getRequirements(){
        return requirements;
    }

    public boolean checkRequirements(DevCardsSpace space){
        return space.checkSpace(requirements.get(0).getColor(), requirements.get(0).getLevel()) >= 2;
    }
}
