package it.polimi.ingsw.model.card.leadercard;

import it.polimi.ingsw.model.gameboard.playerdashboard.PlayerDashboard;
import it.polimi.ingsw.model.card.Card;

import java.util.ArrayList;

/**
 * This is a subclass of Card, which describes the Leader Card.
 * The things in common for each leader card are the fact that they can be activated or discarded;
 * so here we have two methods to activate or discard them,
 * and two methods that inform us if they are activated or discarded.
 *
 * @author Lorenzo Iovine.
 */

public abstract class LeaderCard extends Card {
    private boolean isDiscarded;
    private boolean isActive;


    /**
     * Constructor LeaderCard creates a new LeaderCard instance
     * @param VictoryPoints is the number of the victory points of the card
     * @param CardID is the ID of the card
     */
    public LeaderCard(int VictoryPoints, int CardID) {
        super(VictoryPoints, CardID);
    }


    /**
     * Set the isDiscarded parameter to true
     */
    public void setIsDiscarded(){
        isDiscarded=true;
    }


    /**
     * Set the isDiscarded parameter to false
     */
    public void setIsNotDiscarded() {isDiscarded=false;}


    /**
     * Set the isActive parameter to true
     */
    public void setIsActive(){
        isActive=true;
    }


    /**
     * Set the isActive parameter to false
     */
    public void setIsNotActive() {isActive=false;}


    /**
     * Get the value of the parameter isDiscarded
     * @return the value of isDiscarded
     */
    public boolean getIsDiscarded(){
        return isDiscarded;
    }


    /**
     * Get the value of the parameter isActive
     * @return the value of isActive
     */
    public boolean getIsActive(){
        return isActive;
    }


    /**
     * Get the requirements to activate the card
     * @return an ArrayList that contains the requirements
     */
    public abstract ArrayList<Requirements> getRequirements();


    /**
     * Check if the requirements to activate the card are met
     * @param playerDashboard represent where the requirements must be checked
     * @return true if the requirements are met, otherwise false
     */
    public abstract boolean checkRequirements(PlayerDashboard playerDashboard);

}
