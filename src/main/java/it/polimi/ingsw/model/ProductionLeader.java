package it.polimi.ingsw.model;

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
    private final Requirements requirements;

    public ProductionLeader(int VictoryPoints, Resource Input, Requirements req) {
        super(VictoryPoints);
        this.inputProduction = Input;
        this.requirements=req;
    }

    public Resource getInputProduction(){
        return inputProduction;
    }

    public Requirements getRequirements(){
        return requirements;
    }
}
