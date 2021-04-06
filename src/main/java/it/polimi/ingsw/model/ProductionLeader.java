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
    private Production production;

    private final ArrayList<Requirements> requirements = new ArrayList<>();


    public ProductionLeader(int VictoryPoints, Production production, Requirements req) {
        super(VictoryPoints);
        requirements.add(req);
        this.production = production;
    }

    public ArrayList<Goods> getInputProduction(){
        return production.getInputProduction();
    }
    public ArrayList<Goods> getOutputProduction(){
        return production.getOutputProduction();
    }
    public Production getProduction(){  return production;}

    public ArrayList<Requirements> getRequirements(){ return requirements; }

    public boolean checkRequirements(PlayerDashboard playerDashboard){
        return playerDashboard.getDevCardsSpace().checkSpace(requirements.get(0).getColor(), requirements.get(0).getLevel()) >= 2;
    }
}
