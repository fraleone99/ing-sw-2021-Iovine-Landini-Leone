package it.polimi.ingsw.model.card.leadercard;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.model.gameboard.playerdashboard.PlayerDashboard;

import java.util.ArrayList;

/**
 * The production leader is a subclass of Leader Card.
 * This leader offers the player the chance to choose the output
 * of a production and to obtain a faith point.
 *
 * @author Lorenzo Iovine.
 *
 */

public class ProductionLeader extends LeaderCard {
    private final Production production;
    private final ArrayList<Requirements> requirements = new ArrayList<>();


    /**
     * Constructor ProductionLeader creates a new ProductionLeader instance
     * @param VictoryPoints is the number of the victory points of the card
     * @param CardID is the ID of the card
     * @param production is the production of the card
     * @param req is the requirement of the card
     */
    public ProductionLeader(int VictoryPoints, int CardID, Production production, Requirements req) {
        super(VictoryPoints, CardID);
        requirements.add(req);
        this.production = production;
    }


    /**
     * Get the input of the production of the card
     * @return the input of the production
     */
    public ArrayList<Goods> getInputProduction(){
        return production.getInputProduction();
    }


    /**
     * Get the output of the production of the card
     * @return the output of the production
     */
    public ArrayList<Goods> getOutputProduction(){
        return production.getOutputProduction();
    }


    /**
     * Get the production of the card
     * @return the production
     */
    public Production getProduction(){  return production;}


    /**
     * {@inheritDoc}
     */
    public ArrayList<Requirements> getRequirements(){ return requirements; }


    /**
     * {@inheritDoc}
     */
    public boolean checkRequirements(PlayerDashboard playerDashboard){
        return playerDashboard.getDevCardsSpace().checkSpace(requirements.get(0).getColor(), requirements.get(0).getLevel()) >= 1;
    }


    /**
     * Set the output of the production of the card
     * @param resource is the type of the output of the production
     */
    public void setOutputProduction(Resource resource){
        Goods good = new Goods(resource, 1);
        production.setOutputProduction(good);
    }


    /**
     * Get the faith steps of the production of the card
     * @return the faith steps
     */
    public int getFaithSteps() {
        return production.getFaithSteps();
    }
}
